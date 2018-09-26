/** 500. Inverted Index
 *    Create an inverted index with given documents.
 *    
 *    Note: Ensure that data does not include punctuation.
 *
 *    Example:
 *         Given a list of documents with id and content. (class Document)
 *            [
 *              {
 *                 "id": 1,
 *                "content": "This is the content of document 1 it is very short"
 *              },
 *              {
 *                 "id": 2,
 *                "content": "This is the content of document 2 it is very long bilabial bilabial heheh hahaha ..."
 *              },
 *            ]
 *         Return an inverted index (HashMap with key is the word and value is a list of document ids).
 *            {
 *                "This": [1, 2],
 *                "is": [1, 2],
 *                ...
 *            }
 */
 
/**
 * Definition of Document:
 * class Document {
 *     public int id;
 *     public String content;
 * }
 */
public class Solution {
    /**
     * @param docs a list of documents
     * @return an inverted index
     */
    public Map<String, List<Integer>> invertedIndex(List<Document> docs) {
        // Write your code here
        Map<String, List<Integer>> map = new HashMap<>();
        if(docs == null || docs.size() == 0){
            return map;
        }
        
        for(int i = 0; i < docs.size(); i++){
            int id = docs.get(i).id;
            String context = docs.get(i).content.trim();
            String[] words = context.split("\\s+");
            for(String w: words){
                if(!map.containsKey(w)){
                    map.put(w, new ArrayList<Integer>());
                    map.get(w).add(id);
                }
                else{
                    List<Integer> l = map.get(w);
                    if(l.get(l.size() - 1) != id){
                        map.get(w).add(id);
                    }
                }
            }
        }       
        return map;
    }
}
