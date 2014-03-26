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

        //for(int i = 1; i<51; i++){
        for(int i = 1; i<2; i++){

            if(i<10)
                fileName = "C:\\Users\\scowley\\IdeaProjects\\CS2420-Project3\\Project\\documents\\cranfield000";
            else
                fileName = "C:\\Users\\scowley\\IdeaProjects\\CS2420-Project3\\Project\\documents\\cranfield00";

            fileNum = Integer.toString(i);

            fileName = fileName.concat(fileNum);

            //System.out.println(fileName);

            Scanner inFile = new Scanner(new FileReader(fileName));

            while(inFile.hasNext()){
                String wordIn = new String(inFile.next());
                //System.out.println(wordIn);

                if(!wordIn.startsWith("<")){
                    //System.out.println(wordIn);
                    words[count] = wordIn;
                    count++;
                }
            }
        }

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

