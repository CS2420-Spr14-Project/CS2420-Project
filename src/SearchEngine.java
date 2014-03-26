//Skyler is working on this right now 3/26/14-14.15


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Skyler on 3/26/14.
 */
public class SearchEngine {

    //constructor
    public SearchEngine() throws FileNotFoundException {
        fileParser();

    }

    void fileParser() throws FileNotFoundException {

        for(int i = 1; i<51; i++){
            String fileName = "cranfield00", num = null;
            num = Integer.toString(i);

            fileName = fileName.concat(num);
            System.out.println(fileName);




            //Scanner inFile = new Scanner(new FileReader("./DataFiles./Class4./dataIn.txt"));

            //String fName = inFile.next();


        }
    }

    //main function
    public static void main(String [] args) throws FileNotFoundException {

        SearchEngine search = new SearchEngine();
    }
}

