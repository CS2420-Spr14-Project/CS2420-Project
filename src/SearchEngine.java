//Skyler is working on this right now 3/26/14-14.15


import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Skyler on 3/26/14.
 */
public class SearchEngine {

    //constructor
    public SearchEngine(){
        fileParser();

    }

    void fileParser(){

        Scanner inFile = new Scanner(new FileReader("./DataFiles./Class4./dataIn.txt"));
        PrintWriter outFile = new PrintWriter(".\\DataFiles.\\Class4.\\dataOut.txt");

        String fName = inFile.next();

    }

    //main function
    public static void main(String [] args){

        SearchEngine search = new SearchEngine();
    }
}

