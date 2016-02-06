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

public class CatTest {
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

    try {
      Echo.execute("echo \"Im file in 1\" > /1/Filein1", fileSystem);
      Echo.execute("echo \"hi root folks\" > /hello", fileSystem);
    } catch (InvalidCommand e) {
    }
  }

  @After
  public void tearDown() throws Exception {FileSystem.deleteAll(fileSystem);}
  
  @Test
  public void executeTest1() throws InvalidPathError {
    // Attempt to read file in root
    String[] parameters = {"cat", "hello"};
    String output = Cat.execute(parameters, fileSystem);
    assertEquals("hi root folks", output);
  }

  @Test(expected = PathFinder.InvalidPathError.class)
  public void executeTest2() throws InvalidPathError {
    // Attempt to read file that doesn't exist
    String[] parameters = {"cat", "BruceWillInSixthSense"};
    String output = Cat.execute(parameters, fileSystem);
    assertEquals("", output);
  }

  @Test
  public void executeTest3() throws InvalidPathError {
    // Attempt to read in different curDir
    Directory newCurDir = null;
    try {
      newCurDir = PathFinder.findDir("/1", fileSystem);
    } catch (InvalidPathError e) {
    }
    fileSystem.curDir = newCurDir;
    String[] parameters = {"cat", "Filein1"};
    String output = Cat.execute(parameters, fileSystem);
    assertEquals(output, "Im file in 1");
  }
}
