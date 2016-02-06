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
import driver.CD;
import driver.Mkdir;

public class CDTest {
  FileSystem fileSystem;

  @Before
  public void setUp() throws InvalidPathError {
    fileSystem = FileSystem.createFileSystemInstance();
    String[] mkdirInput = {"mkdir", "1", "2", "3"};
    String[] mkdirInput2 = {"mkdir", "/3/4"};
    String[] mkdirInput3 = {"/3/4/5", "/3/4/6", "2/7"};
    Mkdir.execute(mkdirInput, fileSystem);
    Mkdir.execute(mkdirInput2, fileSystem);
    Mkdir.execute(mkdirInput3, fileSystem);

    File.fileNameCreate("file1.txt", fileSystem.root);
    File.fileNameCreate("file2.txt", fileSystem.root);
  }
  


  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    
    FileSystem.deleteAll(fileSystem);
  }
  
  @Test
  public void executeTest1() throws InvalidPathError {
    //Change to Directory 1 from root using a full path with a trailing /
    String[] parameters = {"cd", "/1/"};
    String directoryName = "1";
    CD.execute(fileSystem, parameters);
    assertEquals(fileSystem.curDir.getName(), directoryName);
    assertNotEquals(fileSystem.curDir, fileSystem.root);
    
  }

  @Test
  public void executeTest2() throws InvalidPathError {
    //Change to Directory 1 from root using a full path
    String[] parameters = {"cd", "/1"};
    String directoryName = "1";
    CD.execute(fileSystem, parameters);
    assertEquals(fileSystem.curDir.getName(), directoryName);
    assertNotEquals(fileSystem.curDir, fileSystem.root);
    
  }
  
  @Test(expected = PathFinder.InvalidPathError.class)
  public void executeTest3() throws InvalidPathError {
    //Change to a directory that doesn't exist
    String[] parameters = {"cd", "/a"};
    CD.execute(fileSystem, parameters);
    assertEquals("/", fileSystem.curDir.getName());
    assertEquals(fileSystem.root, fileSystem.curDir);
  }
  
  @Test
  public void executeTest4() throws InvalidPathError {
    //Change to /3/4/5 from root
    String[] parameters = {"cd", "/3/4/5"};
    CD.execute(fileSystem, parameters);
    assertEquals("5", fileSystem.curDir.getName());
    assertEquals("/3/4/5", fileSystem.curDir.getPathname());
  }
  
  @Test
  public void executeTest5() throws InvalidPathError {
    //Change to /3/4/5 from root and then back to root
    String[] parameters = {"cd", "/3/4/5"};
    CD.execute(fileSystem, parameters);
    assertEquals("5", fileSystem.curDir.getName());
    assertEquals("/3/4/5", fileSystem.curDir.getPathname());
    String[] parameters2 = {"cd", "/"};
    CD.execute(fileSystem, parameters2);
    assertEquals(fileSystem.root, fileSystem.curDir);
  }
  
  @Test
  public void executeTest6() throws InvalidPathError {
    //Change to root from /3/4/5
    try {
      fileSystem.curDir = PathFinder.findDir("/3/4/5", fileSystem);
    } catch (InvalidPathError e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals("5", fileSystem.curDir.getName());
    assertEquals("/3/4/5", fileSystem.curDir.getPathname());

    String[] parameters = {"cd", "/"};
    CD.execute(fileSystem, parameters);
    assertEquals("/", fileSystem.curDir.getName());
    assertEquals(fileSystem.root, fileSystem.curDir);
  
  }
  
  @Test
  public void executeTest7() throws InvalidPathError {
    //Change to parent from /3/4/5
    try {
      fileSystem.curDir = PathFinder.findDir("/3/4/5", fileSystem);
    } catch (InvalidPathError e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals("5", fileSystem.curDir.getName());
    assertEquals("/3/4/5", fileSystem.curDir.getPathname());

    String[] parameters = {"cd", ".."};
    CD.execute(fileSystem, parameters);
    assertEquals("4", fileSystem.curDir.getName());
    assertEquals("/3/4", fileSystem.curDir.getPathname());
    assertEquals(PathFinder.findDir("/3/4", fileSystem), fileSystem.curDir);
    
  }
  
  @Test
  public void executeTest8() throws InvalidPathError {
    //Change to self from /3/4/5 using .
    try {
      fileSystem.curDir = PathFinder.findDir("/3/4/5", fileSystem);
    } catch (InvalidPathError e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals("5", fileSystem.curDir.getName());
    assertEquals("/3/4/5", fileSystem.curDir.getPathname());

    String[] parameters = {"cd", "."};
    CD.execute(fileSystem, parameters);
    assertEquals("5", fileSystem.curDir.getName());
    assertEquals("/3/4/5", fileSystem.curDir.getPathname());
    assertEquals(PathFinder.findDir("/3/4/5", fileSystem), fileSystem.curDir);
    
  }
  
}
