## Auto-Complete - Google Suggestion
   > 基于单词级别联想：说一个单词词组，机器自动帮你联想接下来可能出现的单词或词组。
## Algorithm - N-Gram Model
   > N-Gram: 在一段话中连续出现的N个单词[“连续出现”是因为联想要符合语义，一句话中连续出现的N个单词符合语义的可能性更高]
      > An n-gram is a contiguous sequence of n items from a given sequence of text or speech.
   > Model: Use Probability to predict next word/phrase
   
## Design - Predict N-Gram based on N-Gram
   > Two steps: 
   > 1)MapReduce实现 N-Gram -> 1-Gram ; 
   > 2) Database实现N-Gram -> N-Gram

## Implement (MapReduce)
 > (1) Read a large-scale document collection
 > (2) Build n-gram library - 得到哪些单词／短语是可能出现在哪些单词／短语后的
 > (3) Calculate probability - 得到概率，哪些following出现的概率大
 > (4) Run the project on MapReduce
