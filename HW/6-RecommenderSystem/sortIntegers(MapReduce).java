/** 554. Sort Integers (Map Reduce)
 *      Sort integers by Map Reduce framework.
 *
 *      Example: In the mapper, key is the document id which can be ignored, value is the integers.
 *               In the reducer, your can specify what the key / value is (this depends on how you implement your 
 *        mapper class). For the output of the reducer class, the key can be anything you want, the value should 
 *        be ordered. (the order is depending on when you output it)
 */
 
 /**
 * Definition of OutputCollector:
 * class OutputCollector<K, V> {
 *     public void collect(K key, V value);
 *         // Adds a key/value pair to the output buffer
 * }
 */
class Node{
    int value;
    int list_index;
    int ele_index;
    
    public Node(int value, int l_index, int e_index){
        this.value = value;
        this.list_index = l_index;
        this.ele_index = e_index;
    }
}
public class SortIntegers {

    public static class Map {
        public void map(int key, List<Integer> value,
                        OutputCollector<String, List<Integer>> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, List<Integer> value);
            Collections.sort(value);
            output.collect("no_key", value);
        }
    }
        
    public static class Reduce {
        public void reduce(String key, List<List<Integer>> values,
                           OutputCollector<String, List<Integer>> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, List<Integer> value);
            PriorityQueue<Node> q = new PriorityQueue<Node>(new Comparator<Node>(){
                public int compare(Node a, Node b){
                    return a.value - b.value;
                }
            });
            for(int i = 0; i < values.size(); i++){
                if(values.get(i).size() > 0){
                    q.offer(new Node(values.get(i).get(0), i, 1));
                }
            }
            List<Integer> res = new ArrayList<>();
            while(!q.isEmpty()){
                Node node = q.poll();
                if(node.ele_index < values.get(node.list_index).size()){
                    q.offer(new Node(values.get(node.list_index).get(node.ele_index), node.list_index, node.ele_index + 1));
                }
                res.add(node.value);
            }
            
            output.collect(key, res);
        }
    }
}
