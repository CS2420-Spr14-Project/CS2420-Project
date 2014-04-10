import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Created by Skyler on 4/10/14.
 */
public class SearchEngineTest extends SearchEngine{

    private Node list1, list2;

    public SearchEngineTest() throws FileNotFoundException {
    }

    @Before
    public void setUp() throws Exception {

        String tString = "string";
        for(int i = 0; i < 10; i++){
            list1.insert(new Node(tString.concat(Integer.toString(i))));
            //int
            list2.insert(new Node(tString.concat(Integer.toString((i+2)))));
        }

    }

    @Test
    public void testAndCompare() throws Exception {

        Assert.assertNotSame(list1, andCompare(list1, list2));
        Assert.assertNotSame(list1, orCompare(list1, list2));
        Assert.assertNotSame(list2, andCompare(list1, list2));
        Assert.assertNotSame(list2, orCompare(list1, list2));
    }

    @Test
    public void testOrCompare() throws Exception {

    }
}
