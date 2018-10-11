/** 549. Top K Frequent Words (Map Reduce)
 *      Find top k frequent words with map reduce framework.
 *      The mapper's key is the document id, value is the content of the document, words in a document are split 
 *  by spaces.
 *      For reducer, the output should be at most k key-value pairs, which are the top k words and their 
 *  frequencies in this reducer. The judge will take care about how to merge different reducers' results to get 
 *  the global top k frequent words, so you don't need to care about that part.
 *      The k is given in the constructor of TopK class.
 *
 *      Note: For the words with same frequency, rank them with alphabet.
 *      [只取前K个高频词，有相同的词但是超过K了，直接舍去; 相同频率时，取字母序小的那个词频]
 */
 
 //My Method:
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
public class TopKFrequentWords {

    public static class Map {
        public void map(String key, Document value,
                        OutputCollector<String, Integer> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, int value);
            String[] words = value.content.trim().split("\\s+");
            for(String word: words){
                output.collect(word, 1);
            }
        }
    }
    
    static class Pair{
        public String word;
        public int count;
        public Pair(String word){
            this.word = word;
            this.count = 0;
        }
    }

    public static class Reduce {
        
        PriorityQueue<Pair> q;
        int topk;
        
        private Comparator<Pair> pairComparator = new Comparator<Pair>() {
            public int compare(Pair a, Pair b) {
                if (a.count == b.count) { //相等的时候根据字母序取小的那个
                    return b.word.compareTo(a.word); // 1 means b's word > a's word
                }
                return a.count - b.count; // return positive if a - b > 0 / (a > b)
            }
        };
        
        public void setup(int k) {
            // initialize your data structure here
            q = new PriorityQueue<Pair>(k, pairComparator);
            topk = k;
        }   

        public void reduce(String key, Iterator<Integer> values) {
            // Write your code here
            Pair word = new Pair(key);
            while(values.hasNext()){
                word.count += values.next();
            }
            if(q.size() < topk){
                q.offer(word);
            }
            else{//q.size() == topk
                Pair q_min = q.peek();
                // if word.count > q_min.count, it will return res > 0
                /* if word.count == q_min.count：
                 *   1) if word.word > q_min.word, return res < 0
                 *   2) if word.word == q_min.word, return res = 0
                 *   3) if word.word < q_min.word, return res > 0, we get this one
                 */
                if(pairComparator.compare(word, q_min) > 0){
                    q.poll();
                    q.offer(word);
                }
            }
        }

        public void cleanup(OutputCollector<String, Integer> output) {
            // Output the top k pairs <word, times> into output buffer.
            // Ps. output.collect(String key, Integer value);
            List<Pair> list = new ArrayList<>();
            while(!q.isEmpty()){
                Pair w = q.poll();
                list.add(0, w);
            }
            for(int i = 0; i < list.size(); i++){
                output.collect(list.get(i).word, list.get(i).count);
            }
        }
    }
}
