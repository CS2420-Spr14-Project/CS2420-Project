import javafx.scene.paint.Stop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created on 3/26/14.
 */
public class SearchEngine {

    final int SIZE = 1000;
    final int stopWordsSize = 54;
    String [] words = new String[SIZE];
    StopWordNode [] stopWords = new StopWordNode[stopWordsSize];

    //constructor
    public SearchEngine() throws FileNotFoundException {
        fileParser();

    }

    void fileParser() throws FileNotFoundException {
        String fileName, fileNum;
        int count = 0;

        stopWord();
        //we definately need to implement a hash table or
        //at least a linked list and deal with duplicate words
        //because there are a ton of words in these documents

        //scans through each document and stores the words in an array
        //for(int i = 1; i<51; i++){
        //for(int i = 1; i<8; i++){
        for(int i = 1; i<11; i++){


            //generating file name
            if(i<10)
                fileName = ".\\Project\\documents\\cranfield000";
            else
                fileName = ".\\Project\\documents\\cranfield00";

            fileNum = Integer.toString(i);

            fileName = fileName.concat(fileNum);

            //reading in from the files
            Scanner inFile = new Scanner(new FileReader(fileName));

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
                        words[count] = wordIn;
                        count++;
                    }
                }
            }
        }

        //prints out the array of stored words
        System.out.println("Array:");
        for(int i = 0; i < SIZE; i++){
            System.out.println(words[i]);
        }
        //prints out the array of stored words
        System.out.println("Array2:");
        for(int j = 0; j < stopWordsSize; j++){
            System.out.println(stopWords[j].word);
        }
    }

    void stopWord() throws FileNotFoundException {

        Scanner inFile = new Scanner(new FileReader(".\\Project\\stopwords.txt"));

        for(int i = 0; i < stopWordsSize; i++)
            stopWords[i] = new StopWordNode();

        while(inFile.hasNext()){
            String wordIn = inFile.next();

            //convert word to ASCII value
            int value = 0;
            for(int i =0; i < wordIn.length(); i++){
                value += wordIn.charAt(i);
            }

            System.out.println(wordIn + " "+(value%stopWordsSize));

            StopWordNode inNode = new StopWordNode(wordIn);

            if(stopWords[value%stopWordsSize].word == null)
                stopWords[value%stopWordsSize].word = inNode.word;
            else if(stopWords[value%stopWordsSize].next == null)
                stopWords[value%stopWordsSize].next = inNode;
            else
                stopWords[value%stopWordsSize].next.insert(inNode);
        }
    }

    boolean stopCheck(String inString){
        int value = 0;
        for(int i =0; i < inString.length(); i++){
            value += inString.charAt(i);
        }

        return stopCheck(inString, value, stopWords[value%stopWordsSize]);
    }
    boolean stopCheck(String inString, int inValue, StopWordNode inNode){

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

