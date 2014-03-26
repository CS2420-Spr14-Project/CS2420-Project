//Skyler is working on this right now 3/26/14-14.15


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Skyler on 3/26/14.
 */
public class SearchEngine {

    final int SIZE = 1000;
    String [] words = new String[SIZE];
    int count;

    //constructor
    public SearchEngine() throws FileNotFoundException {
        fileParser();

    }

    void fileParser() throws FileNotFoundException {
        String fileName, fileNum;

        //scans through each document and stores the words in an array
        for(int i = 1; i<51; i++){

            //generating file name
            if(i<10)
                fileName = "C:\\Users\\scowley\\IdeaProjects\\CS2420-Project3\\Project\\documents\\cranfield000";
            else
                fileName = "C:\\Users\\scowley\\IdeaProjects\\CS2420-Project3\\Project\\documents\\cranfield00";

            fileNum = Integer.toString(i);

            fileName = fileName.concat(fileNum);

            //reading in from the files
            Scanner inFile = new Scanner(new FileReader(fileName));

            while(inFile.hasNext()){
                String wordIn = inFile.next();

                if(!wordIn.startsWith("<")){
                    words[count] = wordIn;
                    count++;
                }
            }
        }

        //prints out the array of stored words
        System.out.println("Array:");
        for(int i = 0; i < SIZE; i++){
            System.out.println(words[i]);
        }
    }

    //main function
    public static void main(String [] args) throws FileNotFoundException {

        SearchEngine search = new SearchEngine();
    }
}

