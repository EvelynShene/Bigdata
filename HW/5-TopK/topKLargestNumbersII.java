/** 545. Top k Largest Numbers II(lintcode)
 *     Implement a data structure, provide two interfaces:
 *        1) add(number). Add a new number in the data structure.
 *        2) topk(). Return the top k largest numbers in this data structure. 
 *            k is given when we create the data structure.
 *
 *     Example: s = new Solution(3);
 *              >> create a new data structure.
 *              s.add(3)
 *              s.add(10)
 *              s.topk()
 *              >> return [10, 3]
 *              s.add(1000)
 *              s.add(-99)
 *              s.topk()
 *              >> return [1000, 10, 3]
 *              s.add(4)
 *              s.topk()
 *              >> return [1000, 10, 4]
 *              s.add(100)
 *              s.topk()
 *              >> return [1000, 100, 10]
 */
 
 //My Method:
 public class Solution {
    PriorityQueue<Integer> topK;
    int k;
    public Solution(int k) {
        // do intialization if necessary
        topK = new PriorityQueue<Integer>();
        this.k = k;
    }
    
    public void add(int num) {
        if(topK.size() < k){
            topK.offer(num);
        }
        else{
            if(topK.peek() < num){
                topK.poll();
                topK.offer(num);
            }
        }
    }
    
    public List<Integer> topk() {
        List<Integer> res = new ArrayList<>();
        if(k == 0){
            return res;
        }
        Stack<Integer> stk = new Stack<>();
        while(!topK.isEmpty()){
            stk.push(topK.peek());
            res.add(0, topK.poll());
        }
        while(!stk.isEmpty()){
            topK.offer(stk.pop());
        }
        return res;
    }
}

//Another method for function topk()
public List<Integer> topk() {
    List<Integer> res = new ArrayList<>();
    if(k == 0){
        return res;
    }
    Iterator<Integer> itr = topK.iterator();
    while(itr.hasNext()){
        res.add(itr.next());
    }
    //now res is unsorted, need to sort in decending order
    Collections.sort(res, Collections.reverseOrder()); 
    
    return res;
}
