/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cat;
import driver.FileSystem;
import driver.Get;
import driver.Mkdir;

/**
 * @author Jeffrey
 *
 */
public class GetTest {

  FileSystem filesystem = FileSystem.createFileSystemInstance();
  
  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    String[] path2 = {"mkdir", "1"};
    String[] path3 = {"mkdir", "a"};
    String[] path4 = {"mkdir", "b"};
    String[] path5 = {"mkdir", "1/2"};
    String[] path6 = {"mkdir", "1/3"};
    String[] path7 = {"mkdir", "a/c"};
    String[] path8 = {"mkdir", "b/d"};
    Mkdir.execute(path2, filesystem);
    Mkdir.execute(path3, filesystem);
    Mkdir.execute(path4, filesystem);
    Mkdir.execute(path5, filesystem);
    Mkdir.execute(path6, filesystem);
    Mkdir.execute(path7, filesystem);
    Mkdir.execute(path8, filesystem);
    
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    FileSystem.deleteAll(filesystem);
  }

  
  /**
   * Test method for {@link driver.Get#execute(driver.FileSystem, 
   * java.lang.String)}.
   * Test whether Get works on URL and actually creates a file and fills in 
   * content
   * @throws Exception 
   */
  @Test
  public void testExecute1() throws Exception {
    Get.execute(filesystem, "get http://www.cs.cmu.edu/~spok/grimmtmp/073.txt");
    String[] str = {"cat", "073.txt"};
    String completedFileContents = "";
    URL url = new URL("http://www.cs.cmu.edu/~spok/grimmtmp/073.txt");
    URLConnection urlConnection = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
        .getInputStream()));
    String input;
    while ((input = in.readLine()) != null){
      completedFileContents = completedFileContents + input + "\n";
    }
    in.close();
    assertEquals("073.txt", filesystem.curDir.getFiles().get(0).getFileName());
    assertEquals(completedFileContents, filesystem.curDir.getFiles().get(0)
        .getContents());
  }
  
  /**
   * Test method for {@link driver.Get#execute(driver.FileSystem, 
   * java.lang.String)}.
   * Test whether Get works on URL and actually creates a file and fills in 
   * content
   * @throws Exception 
   */
  @Test
  public void testExecute2() throws Exception {
    Get.execute(filesystem, 
        "get http://individual.utoronto.ca/mmina/a2b/test01.txt");
    String[] str = {"cat", "test01.txt"};
    String completedFileContents = "Hello World!\n";
    assertEquals("test01.txt", filesystem.curDir.getFiles().get(0)
        .getFileName());
    assertEquals(completedFileContents, filesystem.curDir.getFiles().get(0)
        .getContents());
  }
  
  /**
   * Test method for {@link driver.Get#execute(driver.FileSystem, 
   * java.lang.String)}.
   * Test whether Get works on URL and actually creates a file and fills in 
   * content
   * @throws Exception 
   */
  @Test
  public void testExecute3() throws Exception {
    Get.execute(filesystem, 
        "get http://individual.utoronto.ca/mmina/a2b/test02.html");
    String[] str = {"cat", "test02.html"};
    String completedFileContents = "<!DOCTYPE html><HTML><HEAD><TITLE>"
        + "HTML Hello</TITLE></HEAD><BODY><H1>Hello World</H1></BODY></HTML>\n";
    assertEquals("test02.html", filesystem.curDir.getFiles().get(0)
        .getFileName());
    assertEquals(completedFileContents, filesystem.curDir.getFiles().get(0)
        .getContents());
  }

}
