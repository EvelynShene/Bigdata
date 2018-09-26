/** 504. Inverted Index (Map Reduce)
 *    Use map reduce to build inverted index for given documents.
 */

/**
 * Definition of OutputCollector:
 * class OutputCollector<K, V> {
 *     public void collect(K key, V value);
 *         // Adds a key/value pair to the output buffer
 * }
 * Definition of Document:
 * class Document {
 *     public int id;
 *     public String content;
 * }
 */

public class InvertedIndex {

    public static class Map {
        public void map(String key, Document value,
                        OutputCollector<String, Integer> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, int value);
            int id = value.id;
            String[] words = value.content.trim().split("\\s+");
            for(String w: words){
                output.collect(w, id);
            }
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<Integer> values,
                           OutputCollector<String, List<Integer>> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, List<Integer> value);
            List<Integer> list = new ArrayList<>();
            Set<Integer> set = new HashSet<Integer>();
            while(values.hasNext()){
                Integer v = values.next();
                if(!set.contains(v)){
                    list.add(v);
                    set.add(v);
                }
            }
            output.collect(key, list);
        }
    }
}
