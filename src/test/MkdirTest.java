/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.File;
import driver.FileSystem;
import driver.PathFinder;
import driver.Echo;
import driver.PathFinder.InvalidPathError;
import driver.Cat;
import driver.Echo.InvalidCommand;
import driver.Mkdir;

public class MkdirTest {
  FileSystem fileSystem;

  @Before
  public void setUp() {
    fileSystem = FileSystem.createFileSystemInstance();
  }

  @Test
  public void executeTest1() throws InvalidPathError {
    // Attempt to make two files in root
    String[] mkdirInput1 = {"mkdir", "1", "2"};
    Mkdir.execute(mkdirInput1, fileSystem);
    assertTrue(fileSystem.root.getDirectories().size() == 2);
  }
  
  @Test
  public void executeTest2 () throws InvalidPathError {
    // Attempt to make two files of same name
    String[] mkdirInput2 = {"mkdir", "1", "1"};
    String errorMessage = Mkdir.execute(mkdirInput2, fileSystem);
    assertEquals(errorMessage, "Error, can not add file of same name");
  }
  
  @Test
  public void executeTest3() throws InvalidPathError {
    Directory newDirectory = null;
    // Create file with complete path
    String[] mkdirInput1 = {"mkdir", "1"};
    String[] mkdirInput2 = {"mkdir", "/1/2"};
    Mkdir.execute(mkdirInput1, fileSystem);
    Mkdir.execute(mkdirInput2, fileSystem);
    try {
      newDirectory = PathFinder.findDir("/1/2", fileSystem);
    } catch (InvalidPathError e) { }
    assertEquals(newDirectory.getName(), "2");
  }
  
  @Test
  public void executeTest4() throws InvalidPathError {
    // Create file with invalid path
    String[] mkdirInput1 = {"mkdir", "/1/2/3/stop/what/no"};
    String output = Mkdir.execute(mkdirInput1, fileSystem);
    assertEquals(output, "InvalidPathError: Please input a valid path.");
  }
}
