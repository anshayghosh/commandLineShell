/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CP;
import driver.Directory;
import driver.File;
import driver.FileSystem;
import driver.PathFinder;
import driver.Echo;
import driver.PathFinder.InvalidPathError;
import driver.Cat;
import driver.Echo.InvalidCommand;
import driver.Mkdir;
import driver.MV;

public class CPTest {
  FileSystem fileSystem;

  @Before
  public void setUp() throws InvalidPathError {
    fileSystem = FileSystem.createFileSystemInstance();
    fileSystem = FileSystem.createFileSystemInstance();
    String[] mkdirInput = {"mkdir", "1", "2", "3"};
    String[] mkdirInput2 = {"mkdir", "/3/4"};
    String[] mkdirInput3 = {"/3/4/5", "/3/4/6", "2/7"};
    Mkdir.execute(mkdirInput, fileSystem);
    Mkdir.execute(mkdirInput2, fileSystem);
    Mkdir.execute(mkdirInput3, fileSystem);
  }

  @After
  public void tearDown() throws Exception {
    FileSystem.deleteAll(fileSystem);
  }

  @Test
  public void executeTest1() throws InvalidPathError {
    // Copy a single Directory
    Directory movedDir = null;
    String[] parameters = {"mv", "/2/7", "/1"};
    CP.execute(parameters, fileSystem);
    movedDir = PathFinder.findDir("/1/7", fileSystem);
    // Assert move file is dir 7
    assertEquals(movedDir.getName(), "7");
    Directory oldParentDir = PathFinder.findDir("/2", fileSystem);
    // Assert old parent directory still has it
    assertEquals(oldParentDir.getDirectories().size(), 1);
  }

  @Test
  public void executeTest2() throws InvalidPathError {
    // Make sure all contents of Directory have been moved
    Directory movedDir = null;
    String[] parameters = {"cp", "/2", "/1"};
    CP.execute(parameters, fileSystem);
    movedDir = PathFinder.findDir("/1/2/7", fileSystem);
    // Assert nested Directory is in correct location
    assertEquals(movedDir.getName(), "7");
    // Assert old directory location has stayed same
    assertEquals(fileSystem.root.getDirectories().size(), 3);
  }
  
  @Test
  public void executeTest3() throws InvalidPathError {
    // Moving to/from an invalid/non-existent path
    String[] parameters = {"cp", "/cat/dog/ChanceOfGetting100", "/1"};
    String output = CP.execute(parameters, fileSystem);
    assertEquals(output, "InvalidPathError: Please input a valid path.");
  }
  
  @Test
  public void executeTest4() throws InvalidPathError {
    // Trying to move a directory into a subdirectory.
    String[] parameters = {"cp", "/2", "/2/7"};
    String output = CP.execute(parameters, fileSystem);
    assertEquals(output, "Error. Can not copy a directory "
        + "into one of it's subdirectories.");
  }
}
