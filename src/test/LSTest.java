/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.File;
import driver.FileSystem;
import driver.PathFinder;
import driver.PathFinder.InvalidPathError;
import driver.LS;
import driver.Mkdir;

public class LSTest {
  FileSystem filesystem;
  /**
   * @throws InvalidPathError 
   * 
   */
  @Before
  public void setUp() throws InvalidPathError {
    filesystem = FileSystem.createFileSystemInstance();
    String[] mkdirInput = {"mkdir", "1", "2", "3"};
    String[] mkdirInput2 = {"mkdir", "/3/4"};
    String[] mkdirInput3 = {"mkdir", "/3/4/5", "/3/4/6", "/3/4/7", "/3/4/8", "2/6"};
    Mkdir.execute(mkdirInput, filesystem);
    Mkdir.execute(mkdirInput2, filesystem);
    Mkdir.execute(mkdirInput3, filesystem);


    File.fileNameCreate("file1.txt", filesystem.root);
    File.fileNameCreate("file2.txt", filesystem.root);
    File.fileNameCreate("file1.txt", PathFinder.findDir("/3/4/8", filesystem));
    File.fileNameCreate("file3.txt", PathFinder.findDir("/3/4", filesystem));
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {FileSystem.deleteAll(filesystem);}
  
  @Test
  public void executeTest() throws InvalidPathError {
    //Run ls on root 
    String[] parameters = {"ls"};
    assertEquals("1\n2\n3\nfile1.txt\nfile2.txt\n", 
        LS.execute(filesystem, parameters));
    
  }
  
  @Test
  public void executeTest2() throws InvalidPathError {
    //Run ls on file file1.txt in root 
    String[] parameters = {"ls", "file1.txt"};
    assertEquals("file1.txt\n", 
        LS.execute(filesystem, parameters));
    
  }
  
  @Test
  public void executeTest3() throws InvalidPathError {
    //Run ls on directory /3/4 from root using full path
    String[] parameters = {"ls", "/3/4"};
    assertEquals("4:\n5\n6\n7\n8\nfile3.txt\n\n", 
        LS.execute(filesystem, parameters));
    
  }

  @Test
  public void executeTest4() throws InvalidPathError {
    //Run ls on file file1.txt in /3/4/8 from root using full path
    String[] parameters = {"ls", "/3/4/8/file1.txt"};
    assertEquals("file1.txt\n", 
        LS.execute(filesystem, parameters));
    
  }
  
  @Test
  public void executeTest5() throws InvalidPathError {
    //Run ls on directory /3/4 from /3/4
    filesystem.curDir = PathFinder.findDir("/3/4", filesystem);
    String[] parameters = {"ls"};
    assertEquals("5\n6\n7\n8\nfile3.txt\n", 
        LS.execute(filesystem, parameters));
    
  }
  
  @Test
  public void executeTest6() throws InvalidPathError {
    //Run ls on directory /3/4 from /3/4
    
    String[] parameters = {"ls", "-R"};
    assertEquals(    "/:\n1\n2\n3\nfile1.txt\nfile2.txt\n\n"
        + "1:\n\n"
        + "2:\n6\n\n"
        + "6:\n\n"
        + "3:\n4\n\n"
        + "4:\n5\n6\n7\n8\nfile3.txt\n\n"
        + "5:\n\n"
        + "6:\n\n"
        + "7:\n\n"
        + "8:\nfile1.txt\n\n", 
        LS.execute(filesystem, parameters));
    
  }
  
  @Test
  public void executeTest7() throws InvalidPathError {
    //Run ls on directory /3/4 from /3/4
    
    String[] parameters = {"ls", "-R", "/", "/1", "/3"};
    assertEquals(    "/:\n1\n2\n3\nfile1.txt\nfile2.txt\n\n"
        + "1:\n\n"
        + "2:\n6\n\n"
        + "6:\n\n"
        + "3:\n4\n\n"
        + "4:\n5\n6\n7\n8\nfile3.txt\n\n"
        + "5:\n\n"
        + "6:\n\n"
        + "7:\n\n"
        + "8:\nfile1.txt\n\n"
        + "1:\n\n"
        + "3:\n4\n\n"
        + "4:\n5\n6\n7\n8\nfile3.txt\n\n"
        + "5:\n\n"
        + "6:\n\n"
        + "7:\n\n"
        + "8:\nfile1.txt\n\n", 
        LS.execute(filesystem, parameters));
    
  }

  @Test
  public void executeTest8() throws InvalidPathError {
    //Run ls on directory /3/4 from /3/4
    
    String[] parameters = {"ls", "/", "/1", "/3"};
    assertEquals(    "/:\n1\n2\n3\nfile1.txt\nfile2.txt\n\n"
        + "1:\n\n"
        + "3:\n4\n\n", 
        LS.execute(filesystem, parameters));
    
  }
  
  
}
