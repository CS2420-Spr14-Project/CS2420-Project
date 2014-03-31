/**
 * Created on 3/26/14.
 */
public class Node{
    String word;
    Node next;
    Node docs;

    Node(){
        word = null;
        next = null;
        docs = null;
    }

    Node(String wordIn){
        word = wordIn;
        next = null;
        docs = null;
    }

    Node(String wordIn, String docIn){
        word = wordIn;
        next = null;
        docs = new Node(docIn);
    }

    void insert(Node inNode){

        if(this.word.compareTo(inNode.word)==0 && inNode.docs != null){
            Node docIn = new Node(inNode.docs.word);
            this.docs.insert(docIn);
        }
        else if(this.word.compareTo(inNode.word)==0 && inNode.docs == null){

        }
        else{
                if(this.next == null){
               this.next = inNode;
            }
            else {
               this.next.insert(inNode);
            }
        }
    }

    String printDocs(){
        if(this.next == null)
            return this.word;
        else
            return this.word.concat("\n"+printDocs(this.next));
    }
    String printDocs(Node currResults){
        if(currResults.next == null)
            return currResults.word;
        else
            return currResults.word.concat("\n"+printDocs(currResults.next));
    }
}