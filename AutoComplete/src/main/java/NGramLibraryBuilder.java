import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class NGramLibraryBuilder {
	public static class NGramMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

		int noGram;
		@Override
		public void setup(Context context) {
			//how to get n-gram from command line?
			Configuration conf = context.getConfiguration();
			noGram = conf.getInt("noGram", 5);
		}

		// map method
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			// input_value = a sentence; eg. I love big data
			// split sentence and get 2-gram, 3-gram, ... ,n-gram(n = noGram)
			// ouput_key = n-gram, like: I love, love big, bit data
			// output_value = 1

			String line = value.toString();
			
			line = line.trim().toLowerCase();

			//how to remove useless elements?
			line = line.replaceAll("[^a-z]", " ");
			
			//how to separate word by space?
			String[] words = line.split("\\s+");

			if(words.length < 2){
				return;
			}

			//how to build n-gram based on array of words?
			//Use two pointer left, right
			for(int left = 0; left < words.length; left++) {
				StringBuilder substr = new StringBuilder(words[left]);
				for(int right = 1; (left + right) < words.length && right < noGram; right++) {
					substr.append(" " + words[left + right]);
					context.write(new Text(substr.toString().trim()), new IntWritable(1));
				}
			}

		}
	}

	public static class NGramReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		// reduce method
		@Override
		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			// input_key = n-gram; like: I love
			// input_value -> <1, 1, 1,...>
			// output_key = input_key
			// output_value = sum of the values

			//how to sum up the total count for each n-gram?
			int sum = 0;
			for(IntWritable v : values) {
				sum += v.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}

}