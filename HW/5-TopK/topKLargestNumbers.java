/** 544. Top k Largest Numbers(lintcode)
 *      Given an integer array, find the top k largest numbers in it.
 *      
 *      Example: Given [3,10,1000,-99,4,100] and k = 3. Return [1000, 100, 10].
 */
 
//My Method:
public int[] topk(int[] nums, int k) {
    int[] res = new int[k];
    if(k == 0){
        return res;
    }

    PriorityQueue<Integer> queue = new PriorityQueue<Integer>(k);
    for(int i = 0; i < nums.length; i++){
        if(queue.size() < k){
            queue.offer(nums[i]);
        }
        else{
            if(nums[i] > queue.peek()){
                queue.poll();
                queue.offer(nums[i]);
            }
        }
    }

    for(int i = k - 1; i >= 0; i--){
        res[i] = queue.poll();
    }
    return res;
}
