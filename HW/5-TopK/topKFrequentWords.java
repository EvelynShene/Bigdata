/** 471. Top K Frequent Words(lintcode) / 692. Top K Frequent Words(leetcode)
 *      Given a non-empty list of words, return the k most frequent elements.
 *
 *      Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, 
 *  then the word with the lower alphabetical order comes first.
 *
 *      Example: Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2 ; Output: ["i", "love"]
 *            Explanation: "i" and "love" are the two most frequent words.
 *                 Note that "i" comes before "love" due to a lower alphabetical order.
 *
 *      Note:1) You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 *           2) Input words contain only lowercase letters.
 *
 *      Follow up: Try to solve it in O(n log k) time and O(n) extra space.
 */
 
 //My Method: HashMap + PriorityQueue - O(n log k) time and O(n) extra space
 public class Solution {
    /**
     * @param words: an array of string
     * @param k: An integer
     * @return: an array of string
     */
    class Pair{
        String word;
        int count;
        public Pair(String str, int count){
            this.word = str;
            this.count = count;
        }
    }
    
    private Comparator<Pair> pairComparator = new Comparator<Pair>(){
        public int compare(Pair a, Pair b){
            if(a.count == b.count){
                return b.word.compareTo(a.word);
            }
            return a.count - b.count;
        }
    };
    
    public String[] topKFrequentWords(String[] words, int k) {
        String[] res = new String[k];
        if(k == 0){
            return res;
        }
        Map<String, Integer> map = new HashMap<>();
        for(String word: words){
            if(!map.containsKey(word)){
                map.put(word, 1);
            }
            else{
                map.put(word, map.get(word) + 1);
            }
        }
        
        PriorityQueue<Pair> queue = new PriorityQueue<Pair>(k, pairComparator);
        for(Map.Entry<String, Integer> entry: map.entrySet()){
            Pair p = new Pair(entry.getKey(), entry.getValue());
            if(queue.size() < k){
                queue.offer(p);
            }
            else{// queue.size() == k
                Pair e = queue.peek();
                /*
                 * 1.if p.count > e.count, it will return result > 0, need to replace e to p
                 * 2.if p.count == e.count:
                 *   a) if p.word > e.word, it will return result < 0;
                 *   b) if p.word == e.word, it will return result = 0;
                 *   c) if p.word < e.word, it will return result > 0, need to replace e to p
                 *      [same frequency => lower alphabetical order come first]
                 */
                if(pairComparator.compare(p,e) > 0){
                    queue.poll();
                    queue.offer(p);
                }
            }
        }
        
        for(int i = k - 1; i >= 0; i--){
            res[i] = queue.poll().word;
        }
        return res;
    }
}
