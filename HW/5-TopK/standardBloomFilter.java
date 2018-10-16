/** 556. Standard Bloom Filter(lintcode) 
 *     Implement a standard bloom filter. Support the following method:
 *        1) StandardBloomFilter(k),The constructor and you need to create k hash functions.
 *        2) add(string). add a string into bloom filter.
 *        3) contains(string). Check a string whether exists in bloom filter.
 *
 *     Example: StandardBloomFilter(3)
 *              add("lint")
 *              add("code")
 *              contains("lint") // return true
 *              contains("world") // return false
 */
 
// idea from jiuzhang
class HashFunction{
    public int cap;
    public int seed;
    
    public HashFunction(int cap, int seed){
        this.cap = cap;
        this.seed = seed;
    }
    
    public int hash(String value){ // get the hash number of the string "value"
        int res = 0;
        for(int i = 0; i < value.length(); i++){
            res += seed * res + value.charAt(i);
            res %= cap;
        }
        return res;
    }
}


public class StandardBloomFilter {
    /*
    * @param k: An integer
    */
    
    public BitSet bits;
    public int k;
    public List<HashFunction> hashFunc;
    
    public StandardBloomFilter(int k) {
        this.k = k;
        hashFunc = new ArrayList<>();
        for(int i = 0; i < k; i++){
            hashFunc.add(new HashFunction(100000 + i, 2 * i + 3));
        }
        bits = new BitSet(100000 + k);
    }

    /*
     * @param word: A string
     * @return: nothing
     */
    public void add(String word) {
        for(int i = 0; i < hashFunc.size(); i++){
            int hash_value = hashFunc.get(i).hash(word);
            bits.set(hash_value);
        }
    }

    /*
     * @param word: A string
     * @return: True if contains word
     */
    public boolean contains(String word) {
        for(int i = 0; i < hashFunc.size(); i++){
            int hash_value = hashFunc.get(i).hash(word);
            if(!bits.get(hash_value)){
                return false;
            }
        }
        return true;
    }
}
