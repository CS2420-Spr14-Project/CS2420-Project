/**
 * Created on 3/26/14.
 */
public class StopWordNode{
    String word;
    StopWordNode next;

    StopWordNode(){
        word = null;
        next = null;
    }

    StopWordNode(String wordIn){
        word = wordIn;
        next = null;
    }

    void insert(StopWordNode inNode){
        if(this.next == null){
           this.next = inNode;
        }
        else {
           this.next.insert(inNode);
        }
    }
}