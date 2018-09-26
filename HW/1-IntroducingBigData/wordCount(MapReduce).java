/** 499. Word Count (Map Reduce)
 *    Using map reduce to count word frequency.
 *
 *    Example: chunk1: "Google Bye GoodBye Hadoop code"
 *             chunk2: "lintcode code Bye"
 *             Get MapReduce result:
 *                    Bye: 2
 *                    GoodBye: 1
 *                    Google: 1
 *                    Hadoop: 1
 *                    code: 2
 *                    lintcode: 1
 */
 
/**
 * Definition of OutputCollector:
 * class OutputCollector<K, V> {
 *     public void collect(K key, V value);
 *         // Adds a key/value pair to the output buffer
 * }
 */
public class WordCount {

    public static class Map {
        public void map(String key, String value, OutputCollector<String, Integer> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, int value);
            String line = value.trim();
            String[] words = line.split("\\s+");
            for(String w: words){
                output.collect(w.trim(), 1);
            }
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<Integer> values,
                           OutputCollector<String, Integer> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, int value);
            int sum = 0;
            // Iterator itr = values.Iterator();
            while(values.hasNext()){
                Integer v = values.next();
                sum += v;
            }

            output.collect(key, sum);
        }
    }
}
