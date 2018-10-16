/** 503. Anagram (Map Reduce)
 *      Use Map Reduce to find anagrams in a given list of words.
 *
 *      Example: 1) Given ["lint", "intl", "inlt", "code"], return ["lint", "inlt", "intl"],["code"].
 *               2) Given ["ab", "ba", "cd", "dc", "e"], return ["ab", "ba"], ["cd", "dc"], ["e"].
 */
 
 /**
 * Definition of OutputCollector:
 * class OutputCollector<K, V> {
 *     public void collect(K key, V value);
 *         // Adds a key/value pair to the output buffer
 * }
 */
public class Anagram {

    public static class Map {
        public void map(String key, String value,
                        OutputCollector<String, String> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, String value);
            //Input_key = offset; Input_value = a string contains several words
            String[] words = value.trim().split("\\s+");
            for(String w: words){
                char[] c = w.toCharArray();
                Arrays.sort(c);
                String output_key = String.valueOf(c);
                output.collect(output_key, w);
            }
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<String> values,
                           OutputCollector<String, List<String>> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, List<String> value);
            List<String> res = new ArrayList<>();
            
            while(values.hasNext()){
                String v = values.next();
                res.add(v);
            }
            output.collect(key, res);
        }
    }
}
