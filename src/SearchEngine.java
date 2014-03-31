import javafx.scene.paint.Stop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created on 3/26/14.
 */
public class SearchEngine {

    //final int SIZE = 5420;
    final int SIZE = 128;
    final int stopWordsSize = 54;
    //String [] words = new String[SIZE];
    Node [] words = new Node[SIZE];
    Node [] stopWords = new Node[stopWordsSize];

    //constructor
    public SearchEngine() throws FileNotFoundException {
        fileParser();

    }

    void fileParser() throws FileNotFoundException {
        String fileName, fileNum, filePath;
        int count = 0;

        stopWord();
        //we definitely need to implement a hash table or
        //at least a linked list and deal with duplicate words
        //because there are a ton of words in these documents

        //creating array to hash words into
        for(int i = 0; i < SIZE; i++)
            words[i] = new Node();

        //scans through each document and stores the words in an array
        for(int i = 1; i<51; i++){

            //generating file name
            if(i<10)
                fileName = "cranfield000";
            else
                fileName = "cranfield00";

            fileNum = Integer.toString(i);
            filePath = ".\\Project\\documents\\";

            fileName = fileName.concat(fileNum);
            filePath = filePath.concat(fileName);

            //reading in from the files
            Scanner inFile = new Scanner(new FileReader(filePath));

            while(inFile.hasNext()){
                String wordIn = inFile.next();

                //skip <> tags and .
                if(!wordIn.startsWith("<") && !wordIn.startsWith(".")){
                    //remove commas
                    if(wordIn.endsWith(","))
                        wordIn = wordIn.replace(",", "");
                    //check to see if it is stop word
                    if(!stopCheck(wordIn)){
                        //stores the word in an array
                        //**need to turn into storing into a hash table

                        ////insertinto hash table
                        Node toInsert = new Node(wordIn, fileName);
                        System.out.println(wordIn +", "+ fileName);
                        int value = 0;
                        for(int j =0; j < wordIn.length(); j++){
                            value += wordIn.charAt(j);
                        }

                        if(words[value%SIZE].word == null){
                            words[value%SIZE].word = toInsert.word;
                            words[value%SIZE].docs = toInsert.docs;
                        }
                        else if(words[value%SIZE].word.compareTo(toInsert.word)==0){
                            Node docIn = new Node(toInsert.docs.word);
                            if(words[value%SIZE].docs == null)
                                words[value%SIZE].docs = toInsert;
                            else
                                words[value%SIZE].docs.insert(docIn);
                        }
                        else if(words[value%SIZE].next == null)
                            words[value%SIZE].next = toInsert;
                        else
                            words[value%SIZE].next.insert(toInsert);
                        //////////////////////////
                    }
                }
            }
        }

        //prints out the array of stored words
        System.out.println("Array of stored words:");
        for(int i = 0; i < SIZE; i++){
            System.out.println(i + " "+words[i].word);
        }
        //prints out the array of stored words
        System.out.println("Array of stop words(withought linked lists):");
        for(int j = 0; j < stopWordsSize; j++){
            System.out.println(stopWords[j].word);
        }
    }

    //creates the stopWords hash table
    void stopWord() throws FileNotFoundException {

        Scanner inFile = new Scanner(new FileReader(".\\Project\\stopwords.txt"));

        for(int i = 0; i < stopWordsSize; i++)
            stopWords[i] = new Node();

        while(inFile.hasNext()){
            String wordIn = inFile.next();

            //convert word to ASCII value
            int value = 0;
            for(int i =0; i < wordIn.length(); i++){
                value += wordIn.charAt(i);
            }

            Node inNode = new Node(wordIn);

            if(stopWords[value%stopWordsSize].word == null)
                stopWords[value%stopWordsSize].word = inNode.word;
            else if(stopWords[value%stopWordsSize].next == null)
                stopWords[value%stopWordsSize].next = inNode;
            else
                stopWords[value%stopWordsSize].next.insert(inNode);
        }
    }

    //recursively checks a given string against the stopWords hash table
    //if the word is in the stopWords array it returns true
    boolean stopCheck(String inString){
        int value = 0;
        for(int i =0; i < inString.length(); i++){
            value += inString.charAt(i);
        }

        return stopCheck(inString, value, stopWords[value%stopWordsSize]);
    }
    boolean stopCheck(String inString, int inValue, Node inNode){

        if(inNode.word == null)
            return false;
        else if(inString.compareTo(inNode.word) == 0)
            return true;
        else if(inNode.next == null)
            return false;
        else
            return stopCheck(inString, inValue, inNode.next);
    }



    //main function
    public static void main(String [] args) throws FileNotFoundException {

        SearchEngine search = new SearchEngine();
    }
}

