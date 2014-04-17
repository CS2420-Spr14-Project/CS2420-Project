import javafx.scene.paint.Stop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created on 3/26/14.
 */
public class SearchEngine extends JFrame implements ActionListener{

    final int SIZE = 256;
    final int stopWordsSize = 28;
    Node[] words = new Node[SIZE];
    Node[] stopWords = new Node[stopWordsSize];
    JTextField inputTF;
    private JTextArea resultsTA = new JTextArea("");
    JButton search;
    String searchTerm;
    SearchBH searchHandler;

    //constructor
    public SearchEngine() throws FileNotFoundException {
        fileParser();

        JLabel title = new JLabel("DocHunt", SwingConstants.CENTER);
        inputTF = new JTextField();

        Container pane = getContentPane();
        pane.setLayout(null);

        title.setSize(500, 100);
        title.setLocation(0, 0);
        title.setFont(new Font(title.getName(), Font.PLAIN, 50));

        inputTF.setSize(300, 30);
        inputTF.setLocation(50, 100);

        search = new JButton("Search");
        search.setSize(80, 29);
        search.setLocation(350, 100);
        searchHandler = new SearchBH();
        search.addActionListener(searchHandler);

        //resultsTA.setSize(100, 100);
        //resultsTA.setLocation(50, 150);
        resultsTA.setBounds(60, 160, 100, 100);
        resultsTA.setEditable(false);
        resultsTA.setBorder(null);
        resultsTA.setBackground(null);

        //resultsTA.setText("World");

        JScrollPane temp = new JScrollPane(resultsTA);
        temp.setBounds(50, 150, 445, 322);
        temp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        temp.setPreferredSize(new Dimension(100, 100));
        temp.setBorder(null);

        pane.add(title);
        pane.add(inputTF);
        pane.add(search);
        //pane.add(resultsTA);
        pane.add(temp);
        resultsTA.setLineWrap(true);


        //getInput();

        setSize(500, 500);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

                        if (wordIn.contains("-")) {
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

    void getInput() {

        //String input = JOptionPane.showInputDialog(null, "What would you like to search for?", "Search");

        //JOptionPane.showMessageDialog(null, input + " was found in the following documents:\n" + unknown(input).printDocs(), "Results", JOptionPane.PLAIN_MESSAGE);

        //resultsTA.setText(unknown(input).printDocs());

    }

    Node search(String input) {

        System.out.println(input);
        int value = mainHash(input);
        Node output = search(words[value % SIZE], input);

        return output; // placed temporarily
    }

    Node search(Node inNode, String input) {
        if (inNode.word == null){
            if (stopCheck(input, stopWords[stopHash(input) % stopWordsSize])){
                return new Node("Please refine your search");
            }
            else return new Node(input + " is not found in any document");
        }

        else if (inNode.word.compareTo(input) == 0) {
            return inNode.docs;
        } else if (inNode.next == null) {
            if (stopCheck(input, stopWords[stopHash(input) % stopWordsSize])){
                return new Node("Please refine your search");
            }
            else return new Node(input + " is not found in any document");
        } else
            return search(inNode.next, input);
    }

    Node unknown(String inString) {
        boolean andTrue = false, orTrue = false;
        Node results = new Node();
        int[] termIndex = new int[4];
        String[] term = new String[4];
        int[] andOrIndex = new int[4];

        termIndex = initialized(termIndex);
        andOrIndex = initialized(andOrIndex);



        if (inString.contains("AND")) andTrue = true;
        if (inString.contains("OR")) orTrue = true;

        //check if it contains and or or
        if (andTrue) {

            int j = 0;
            for (int i = -1; (i = inString.indexOf("AND", i + 1)) != -1; ) {

                if (!stopCheck(inString.substring(j, inString.indexOf("AND", j) - 1), stopWords[stopHash(inString.substring(j, inString.indexOf("AND", j) - 1)) % stopWordsSize])) {

                    andOrIndex[nextElement(andOrIndex)] = i;

                    term[nextElement(termIndex)] = inString.substring(j, inString.indexOf("AND", j) - 1);

                    termIndex[nextElement(termIndex)] = j;
                }

                j = i + 4;

                if (inString.indexOf("AND", i + 1) == -1)
                    term[nextElement(termIndex)] = inString.substring(j, (inString.length()));
            }
        }

        if (orTrue) {

            int j = 0;
            for (int i = -1; (i = inString.indexOf("OR", i + 1)) != -1; ) {

                if (!stopCheck(inString.substring(j, inString.indexOf("OR", j) - 1), stopWords[stopHash(inString.substring(j, inString.indexOf("OR", j) - 1)) % stopWordsSize])) {

                    andOrIndex[nextElement(andOrIndex)] = i;

                    term[nextElement(termIndex)] = inString.substring(j, inString.indexOf("OR", j) - 1);

                    termIndex[nextElement(termIndex)] = j;

                }

                j = i + 3;

                if (inString.indexOf("OR", i + 1) == -1)
                    term[nextElement(termIndex)] = inString.substring(j, (inString.length()));
            }
        }

        if (andTrue && orTrue) {
            int[] andOrList = new int[4];
            andOrList = initialized(andOrList);

            for (int i = 0; i < 3; i++){

                if (inString.charAt(andOrIndex[i]) == 'A') andOrList[i] = 1;
                else if (inString.charAt(andOrIndex[i]) == 'O') andOrList[i] = 0;

            }

            if (andOrList[0] > andOrList[2] && andOrList[1] > andOrList[2]){
                Node temp1 = unknown(term[2]);
                Node temp2 = unknown(term[3]);
                results = orCompare(temp1, temp2);
            }
        }

        if (!andTrue && !orTrue) {
            results = search(inString);
        } else if (nextElement(andOrIndex) > 0) {
            if (andTrue && !orTrue) {
                results = andCompare(search(term[0]), search(term[1]));

                int numAnd = nextElement(andOrIndex);

                for (int i = 1; numAnd > 1 && i < numAnd; i++)
                    results = andCompare(results, search(term[i + 1]));

            } else if (orTrue && !andTrue) {
                results = orCompare(search(term[0]), search(term[1]));

                int numOr = nextElement(andOrIndex);

                for (int i = 1; numOr > 1 && i < numOr; i++)
                    results = orCompare(results, search(term[i + 1]));
            }
        } else
            results = search(term[0]);

        return results;
    }

    int nextElement(int[] arr) {
        int var = -1;

        for (int i = 0; i < 5; ) {

            if (arr[i] != -1) i += 1;
            else {
                var = i;
                return var;
            }
        }
        return var;
    }

    int[] initialized(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -1;
        }
        return arr;
    }

    Node andCompare(Node inNode1, Node inNode2) {
        //if equal
        if (inNode1.word.compareTo(inNode2.word) == 0) {
            //if at the end of a list
            if (inNode1.next == null)
                return inNode1;
            else if (inNode2.next == null)
                return inNode2;
            else {
                Node result = new Node(inNode1.word);
                result.next = andCompare(inNode1.next, inNode2.next);
                return result;
            }
        }
        //if n1 is greater
        else if (inNode1.word.compareTo(inNode2.word) > 0) {
            if (inNode2.next == null)
                return null;
            return andCompare(inNode1, inNode2.next);
        }
        //if n2 is greater
        else {
            if (inNode1.next == null)
                return null;
            return andCompare(inNode1.next, inNode2);
        }
    }

    Node orCompare(Node inNode1, Node inNode2) {
        //if equal
        if (inNode1.word.compareTo(inNode2.word) == 0) {
            //if at the end of a list
            if (inNode1.next == null)
                return inNode2;
            else if (inNode2.next == null)
                return inNode1;
            else {
                Node result = new Node(inNode1.word);
                result.next = orCompare(inNode1.next, inNode2.next);
                return result;
            }
        }
        //if n1 is greater
        else if (inNode1.word.compareTo(inNode2.word) > 0) {
            Node result = new Node(inNode2.word);
            if (inNode2.next == null)
                result.next = inNode1;
            else
                result.next = orCompare(inNode1, inNode2.next);
            return result;
        }
        //if n2 is greater
        else {
            Node result = new Node(inNode1.word);
            if (inNode1.next == null)
                result.next = inNode2;
            else
                result.next = orCompare(inNode1.next, inNode2);
            return result;
        }
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class SearchBH implements ActionListener{

        public void actionPerformed(ActionEvent e){

            resultsTA.setText(unknown(inputTF.getText()).printDocs());
        }

    }
}
