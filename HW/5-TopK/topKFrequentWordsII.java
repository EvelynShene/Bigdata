/** 550. Top K Frequent Words II(lintcode)
 *      Find top k frequent words in realtime data stream.
 *      Implement three methods for Topk Class:
 *           1. TopK(k). The constructor.
 *           2. add(word). Add a new word.
 *           3. topk(). Get the current top k frequent words.
 *
 *      Note: If two words have the same frequency, rank them by alphabet.
 *
 *      Example: TopK(2)
 *               add("lint")
 *               add("code")
 *               add("code")
 *               topk()
 *               >> ["code", "lint"]
 */
 
 /* My Method: 每次获得一个新的词，词频更新时，就更新PriorityQueue里的topK的单词
  *   Note: 如果每次调用topk()函数时才遍历map计算topK的单词，会MTL.
  */
 public class TopK {
    /*
    * @param k: An integer
    */
    class Pair{
        String word;
        int count;
        
        public Pair(String word, int count){
            this.word = word;
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
    
    PriorityQueue<Pair> queue;
    HashMap<String, Pair> wordFreq;
    int topk;
    
    public TopK(int k) {
        // do intialization if necessary
        if(k != 0){
            queue = new PriorityQueue<Pair>(k, pairComparator);
            topk = k;
            wordFreq = new HashMap<String, Pair>();
        }
    }

    /*
     * @param word: A string
     * @return: nothing
     */
    public void add(String word) {
        // write your code here
        if(topk == 0){
            return;
        }
        
        if(!wordFreq.containsKey(word)){
            wordFreq.put(word, new Pair(word, 1));
        }
        else{
            wordFreq.get(word).count++;
        }
        
        Pair e = wordFreq.get(word);
        if(queue.contains(e)){
        	queue.remove(e);
        	queue.offer(e);
        }
        else{//queue not contains e
        	if(queue.size() < topk){
        		queue.offer(e);
        	}
        	else{
        		if(pairComparator.compare(e, queue.peek()) > 0){
        			queue.poll();
        			queue.offer(e);
        		}
        	}
        }
    }

    /*
     * @return: the current top k frequent words.
     */
    public List<String> topk() {
        // write your code here
        List<String> res = new ArrayList<>();
        
        if(topk == 0){
            return res;
        }
        
        Stack<Pair> stk = new Stack<>();
        while(!queue.isEmpty()){
            stk.push(queue.peek());
            res.add(0, queue.poll().word);
        }
        while(!stk.isEmpty()){
            queue.offer(stk.pop());
        }
        
        return res;
    }
}
