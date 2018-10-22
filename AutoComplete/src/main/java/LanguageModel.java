import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class LanguageModel {
	public static class Map extends Mapper<LongWritable, Text, Text, Text> {

		int threashold;

		@Override
		public void setup(Context context) {
			// how to get the threashold parameter from the configuration?
			Configuration conf = context.getConfiguration();
			threashold = conf.getInt("threashold", 10);
		}

		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			//input_value -> this is cool\t20
			//separate the value to starting_phrase and following_word and its count
			//output_key: starting_phrase
			//output_value: following_word=its count

			if((value == null) || (value.toString().trim()).length() == 0) {
				return;
			}

			//this is cool\t20
			String line = value.toString().trim();
			
			String[] wordsPlusCount = line.split("\t");
			if(wordsPlusCount.length < 2) {
				return;
			}
			
			String[] words = wordsPlusCount[0].split("\\s+");
			int count = Integer.valueOf(wordsPlusCount[1]);

			//how to filter the n-gram lower than threashold
			if(count < threashold){
				return;
			}
			
			//this is --> cool = 20
			StringBuilder starting_phrase = new StringBuilder();
			for(int i = 0; i < words.length - 1; i++){
				starting_phrase.append(" " + words[i]);
			}
			String following_word = words[words.length - 1];

			//what is the outputkey? - output_key: starting_phrase
			//what is the outputvalue? - output_value: following_word=its count
			String output_key = starting_phrase.toString().trim();
			String output_value = following_word + "=" + count;

			//write key-value to reducer?
			if((output_key == null) || output_key.length() < 1 ){
				return;
			}

			context.write(new Text(output_key), new Text(output_value));
		}
	}

	public static class Reduce extends Reducer<Text, Text, DBOutputWritable, NullWritable> {
		int n; // store only the top n words with the highest probabilities
		// get the n parameter from the configuration
		@Override
		public void setup(Context context) {
			Configuration conf = context.getConfiguration();
			n = conf.getInt("n", 5);
		}

		private Comparator<String> strComparator = new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		};

		@Override
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			//input_key -> key = starting_phrase;
			//input_value -> values = <following_word1=count1, following_word2=count2, ...>
			//find the topN n-gram and write it to database
			//output <starting_phrase, following_word, count> -> DB

			//can you use priorityQueue to rank topN n-gram, then write out to hdfs?
			//use count to sort all following_words, get list like:
			//   {{60, <word1, word2,...>}, {50, <word3, word4>}, ...}
			//  the words in one list is also sorted in lexicographically order
			TreeMap<Integer, PriorityQueue<String>> tm = new TreeMap<Integer, PriorityQueue<String>>(Collections.<Integer>reverseOrder());
			for(Text value: values){
				String curValue = value.toString().trim();
				String word = curValue.split("=")[0].trim();
				int count = Integer.parseInt(curValue.split("=")[1].trim());
				if(tm.containsKey(count)){
					tm.get(count).offer(word);
				}
				else{
					PriorityQueue<String> q = new PriorityQueue<String>(n, strComparator);
					q.offer(word);
					tm.put(count, q);
				}
			}

			Iterator<Integer> itr = tm.keySet().iterator();
			for(int i = 0; itr.hasNext() && i < n; i++){
				int key_count = itr.next();
				PriorityQueue<String> q = tm.get(key_count);
				while(!q.isEmpty()) {
					String following_word = q.poll();
					context.write(new DBOutputWritable(key.toString(), following_word, key_count), NullWritable.get());
					i++;
				}
				i--;
			}
		}
	}
}
