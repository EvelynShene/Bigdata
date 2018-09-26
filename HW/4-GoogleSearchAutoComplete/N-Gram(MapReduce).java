/** 537. N-Gram (Map Reduce)
 *    给出若干字符串和数字 N。用 Map Reduce 的方法统计所有 N-Grams 及其出现次数 。以字母为粒度。
 *
 *    Example: Given N = 3
 *                doc_1: "abcabc"
 *                doc_2: "abcabc"
 *                doc_3: "bbcabc"
 *             The final result should be:
 *                 [
 *                   "abc": ５,
 *                   "bbc": 1, 
 *                   "bca": 3,
 *                   "cba": 3
 *                 ]
 */

/**
 * Definition of OutputCollector:
 * class OutputCollector<K, V> {
 *     public void collect(K key, V value);
 *         // Adds a key/value pair to the output buffer
 * }
 */
 public class NGram {

    public static class Map {
        public void map(String key, int n, String str,
                        OutputCollector<String, Integer> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, Integer value);
            str = str.trim();
            for(int i = 0; i + n <= str.length(); i++){
                String s = str.substring(i, i + n);
                output.collect(s, 1);
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
            while(values.hasNext()){
                Integer v = values.next();
                sum += v;
            }
            output.collect(key, sum);
        }
    }
}
