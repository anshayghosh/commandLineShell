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
import driver.Echo.InvalidCommand;
import driver.LS;
import driver.PathFinder.InvalidPathError;


public class EchoTest {
  FileSystem filesystem;



  @Before
  public void setUp() throws InvalidPathError, InvalidCommand {
    // code borrowed form jshell revision76
    filesystem = FileSystem.createFileSystemInstance();
    filesystem.root.addDirectory(new Directory("1"));

    Directory d = new Directory("2");
    Directory e = new Directory("3");
    Directory f = new Directory("4");
    Directory g = new Directory("5");

    filesystem.root.addDirectory(d);
    filesystem.root.addDirectory(e);
    e.addDirectory(f);
    f.addDirectory(g);

    File.fileNameCreate("aasd", filesystem.root);
    File.fileNameCreate("second", filesystem.root);
    File.fileNameCreate("existing", g);
      Echo.execute("echo \"existing content\" > 3/4/5/existing", filesystem);

      Echo.execute("echo \"existing content\" > second", filesystem);

  }

  @Test
  public void executeTest1() throws InvalidPathError {
    // whether content write to a already existing empty file
    // is correctly recorded
    // first test case is to write to the empty file aasd in root

    try {
      Echo.execute("echo \"first_test\" >> aasd", filesystem);
    } catch (InvalidCommand e) {
      System.out.println(e.getMessage());
    }
    assertEquals("first_test", filesystem.root.getFiles().get(0).getContents());

  }

  @Test
  public void executeTest2() throws InvalidPathError {
    // whether content write to a existing file is dealt with correctly

    try {
      Echo.execute("echo \"second_test\" >> second", filesystem);
    } catch (InvalidCommand e) {
      System.out.println(e.getMessage());
    }
    assertEquals("existing content" + "second_test", filesystem.root.getFiles()
        .get(1).getContents());
  }

  @Test
  public void executeTest3() throws InvalidPathError {
    // whether content write to a existing file is dealt with correctly

    try {
      Echo.execute("echo \"second_test\" > second", filesystem);
    } catch (InvalidCommand e) {
      System.out.println(e.getMessage());
    }

    assertEquals("second_" + "test", filesystem.root.getFiles().get(1)
        .getContents());
  }

  @Test
  public void executeTest4() throws InvalidPathError {
    // whether content write to a missingfile is dealt with correctly

    try {
      Echo.execute("echo \"second_test\" > missing", filesystem);

    } catch (InvalidCommand e) {
      System.out.println(e.getMessage());
    }
    assertEquals("second_" + "test", filesystem.root.getFiles().get(2)
        .getContents());
  }

  @Test
  public void executeTest5() throws InvalidPathError {
    // start from test 5
    // test whether content write to a file within layers of directory
    // is dealt with correctly, first write conetnts to missing file
    
    try {
      Echo.execute("echo \"second_test\" > 3/4/5/ss", filesystem);
    } catch (InvalidCommand e) {
      System.out.println(e.getMessage());
    }
    try {
      Directory tar = PathFinder.findParentDir("/3/4/5/ss", filesystem);
      assertEquals("second_" + "test", tar.getFiles().get(1).getContents());
    } catch (driver.PathFinder.InvalidPathError patherror) {
      System.err.println("InvalidPathError: " + "Please input a valid path.");
    }

  }

  @Test
  public void executeTest6() throws InvalidPathError {
    // whether content write to a file within layers of directory
    // is dealt with correctly, this test write to a file with existing content

    try {
      Echo.execute("echo \"second_test\" > 3/4/5/existing", filesystem);
    } catch (InvalidCommand e) {
      System.out.println(e.getMessage());
    }
    try {
      Directory tar = PathFinder.findParentDir("/3/4/5/existing", filesystem);
      assertEquals("second_" + "test", tar.getFiles().get(0).getContents());
    } catch (driver.PathFinder.InvalidPathError patherror) {
      System.err.println("InvalidPathError: " + "Please input a valid path.");
    }
  }

  @Test
  public void executeTest7() throws InvalidPathError {
    // whether content write to a file within layers of directory
    // is dealt with correctly,
    // this test append to a file with existing content

    try {
      Echo.execute("echo \"second_test\" >> 3/4/5/existing", filesystem);
    } catch (InvalidCommand e) {
      System.out.println(e.getMessage());
    }
    try {
      Directory tar = PathFinder.findParentDir("/3/4/5/existing", filesystem);
      assertEquals("existing content" + "second_" + "test",
          tar.getFiles().get(0).getContents());
    } catch (driver.PathFinder.InvalidPathError patherror) {
      System.err.println("InvalidPathError: " + "Please input a valid path.");
    }
  }

  @After
  public void tearDown() {
    FileSystem.deleteAll(filesystem);
  }
}
