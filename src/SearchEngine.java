import javafx.scene.paint.Stop;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created on 3/26/14.
 */
public class SearchEngine {

    //final int SIZE = 5420;
    //final int SIZE = 128;
    final int SIZE = 256;
    //final int SIZE = 512;
    //final int stopWordsSize = 54;
    final int stopWordsSize = 28;
    //String [] words = new String[SIZE];
    Node[] words = new Node[SIZE];
    Node[] stopWords = new Node[stopWordsSize];

    //constructor
    public SearchEngine() throws FileNotFoundException {
        fileParser();
        search();

    }

    void fileParser() throws FileNotFoundException {
        String fileName, fileNum, filePath;
        int count = 0;

        stopWord();

        //creating array to hash words into
        for (int i = 0; i < SIZE; i++)
            words[i] = new Node();

        //scans through each document and stores the words in an array
        for (int i = 1; i < 51; i++) {

            //generating file name
            fileName = i < 10 ? "cranfield000" : "cranfield00";

            fileNum = Integer.toString(i);
            filePath = ".\\Project\\documents\\";

            fileName = fileName.concat(fileNum);
            filePath = filePath.concat(fileName);

            //reading in from the files
            Scanner inFile = new Scanner(new FileReader(filePath));

            while (inFile.hasNext()) {
                String wordIn = inFile.next();

                //skip <> tags and .
                if (!wordIn.startsWith("<") && !wordIn.startsWith(".")) {
                    //remove commas
                    if (wordIn.endsWith(","))
                        wordIn = wordIn.replace(",", "");
                    //check to see if it is stop word


                    //if(!stopCheck(wordIn)){
                    if (!stopCheck(wordIn, stopWords[stopHash(wordIn) % stopWordsSize])) {

                        if(wordIn.contains("-")){
                            //System.out.println(wordIn);
                        }

                        //insert into hash table
                        Node toInsert = new Node(wordIn, fileName);
                        //System.out.println(wordIn + ", " + fileName);
                        int value = mainHash(wordIn);

                        if (words[value % SIZE].word == null) {
                            words[value % SIZE].word = toInsert.word;
                            words[value % SIZE].docs = toInsert.docs;
                        } else if (words[value % SIZE].word.compareTo(toInsert.word) == 0) {
                            Node docIn = new Node(toInsert.docs.word);
                            if (words[value % SIZE].docs == null)
                                words[value % SIZE].docs = toInsert;
                            else
                                words[value % SIZE].docs.insert(docIn);
                        } else if (words[value % SIZE].next == null)
                            words[value % SIZE].next = toInsert;
                        else
                            words[value % SIZE].next.insert(toInsert);
                    }
                }
            }
        }

        //prints out the array of stored words
        System.out.println("Array of stored words:");
        for (int i = 0; i < SIZE; i++) {
            System.out.println(i + " " + words[i].word);
        }
        //prints out the array of stored words
        System.out.println("Array of stop words(withought linked lists):");
        for (int j = 0; j < stopWordsSize; j++) {
            System.out.println(stopWords[j].word);
        }
    }

    //creates the stopWords hash table
    void stopWord() throws FileNotFoundException {

        Scanner inFile = new Scanner(new FileReader(".\\Project\\stopwords.txt"));

        for (int i = 0; i < stopWordsSize; i++)
            stopWords[i] = new Node();

        while (inFile.hasNext()) {
            String wordIn = inFile.next();

            //convert word to ASCII value
            int value = stopHash(wordIn);

            Node inNode = new Node(wordIn);

            if (stopWords[value % stopWordsSize].word == null)
                stopWords[value % stopWordsSize].word = inNode.word;
            else if (stopWords[value % stopWordsSize].next == null)
                stopWords[value % stopWordsSize].next = inNode;
            else
                stopWords[value % stopWordsSize].next.insert(inNode);
        }
    }

    //recursively checks a given string against the stopWords hash table
    //if the word is in the stopWords array it returns true
    boolean stopCheck(String inString, Node inNode) {

        if (inNode.word == null)
            return false;
        else if (inString.compareTo(inNode.word) == 0)
            return true;
        else if (inNode.next == null)
            return false;
        else
            return stopCheck(inString, inNode.next);
    }

    void getInput(){

        String input = JOptionPane.showInputDialog(null, "What would you like to search for?", "Search");

        JOptionPane.showMessageDialog(null, unknown(input).printDocs(), "Results", JOptionPane.PLAIN_MESSAGE);
    }

    String search(String input) {

        //String input = JOptionPane.showInputDialog(null, "What would you like to search for?", "Search");
        int value = mainHash(input);
        String output = search(words[value % SIZE], input);

        JOptionPane.showMessageDialog(null, input + " was found in the following documents:\n" + output, "Results", JOptionPane.PLAIN_MESSAGE);

        return output; // placed temporarily
    }

    String search(Node inNode, String input) {
        if (inNode.word == null)
            return "Word not found.";
        else if (inNode.word.compareTo(input) == 0) {
            return inNode.docs.printDocs();
        } else if (inNode.next == null) {
            return "Word not found.";
        } else
            return search(inNode.next, input);
    }

    Node unknown(String inString){
        boolean andTrue = false, orTrue = false;

        int[] termIndex = new int[4];
        String[] term = new String[4];
        int[] andIndex = new int[3];
        int[] orIndex = new int [3];

        //check if it contains and or or
        if (inString.contains("AND")){
            andTrue = true;

            int j = 0;
            for (int i = -1; (i = inString.indexOf("AND", i + 1)) != -1;){
                int element = nextElement(termIndex);
                term[element] = inString.substring(j, inString.indexOf("AND") - 2);
                j = i + 4;
            }
        }

        if (inString.contains("OR")){
            orTrue = true;

            for (int i = -1; (i = inString.indexOf("OR", i + 1)) != -1;){

            }
        }

        if (andTrue && orTrue){

        }

        //check for only
        //parse through term1 term2
        //determine if there are ands and ors
        //if and
        //Node results = AndCompare(search(term1), search(term2));
        //else or
        //Node results = OrCompare(search(term1), search(term2));

        return results;

    }

    int nextElement(int[] arr){
        int var = -1;

        for (int i = 0; i < 5; i++){

            if (arr[i] != -1) i++;
            else var = i;
        }
        return var;
    }

    String findTerm(String inString){
        String term1, term2;

        //check if it contains and or or
        if (inString.contains("AND")){
            term1 = inString.substring(0, inString.indexOf("AND"));
            term2 = findTerm(inString.substring(inString.indexOf("AND") + 4, inString.length()));
        }

        if (inString.contains("OR")){
            term1 = inString.substring(0, inString.indexOf("OR"));
            term2 = findTerm(inString.substring(inString.indexOf("OR") + 3, inString.length()));
        }

        //check for only
        //parse through term1 term2
        //determine if there are ands and ors
        //if and
        //Node results = AndCompare(search(term1), search(term2));
        //else or
        //Node results = OrCompare(search(term1), search(term2));

        //return results;

    }

    //true = and, false = or
    Node compare(Node inNode1, Node inNode2){
        //temp node

        //compare each doc in each list
        //if anding
        //store matches in temp
        //if oring
        //store all in temp discard duplicate docs
        //return temp


    }

    int mainHash(String wordIn) {
        int value = 0;
        for (int j = 0; j < wordIn.length(); j++) {
            value += wordIn.charAt(j);
        }
        //value = wordIn.charAt(0)*wordIn.charAt(wordIn.length()-1);
        return value;
    }

    int stopHash(String wordIn) {
        /*int value = 0;
            for(int i =0; i < wordIn.length(); i++){
                value += wordIn.charAt(i);
            }*/
        return wordIn.charAt(0) + wordIn.charAt(wordIn.length() - 1);
    }

    //main function
    public static void main(String[] args) throws FileNotFoundException {

        SearchEngine search = new SearchEngine();
    }
}

