package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.Echo;
import driver.FileSystem;
import driver.Grep;
import driver.Mkdir;
import driver.Echo.InvalidCommand;
import driver.PathFinder.InvalidPathError;

public class GrepTest {
  FileSystem filesystem;

  @Before
  public void setUp() throws InvalidPathError, InvalidCommand {

    filesystem = FileSystem.createFileSystemInstance();
    filesystem.root.addDirectory(new Directory("1"));
    // code borrowed from get test
    String[] path2 = {"mkdir", "2"};
    String[] path3 = {"mkdir", "3"};
    String[] path4 = {"mkdir", "2/5"};
    String[] path5 = {"mkdir", "3/4"};
    Mkdir.execute(path2, filesystem);
    Mkdir.execute(path3, filesystem);
    Mkdir.execute(path4, filesystem);
    Mkdir.execute(path5, filesystem);

    // initial a file structure as : /2/5
    // /3/4
    Echo.execute("echo \"existing content\" > existing", filesystem);

    Echo.execute("echo \"existing content\" > second", filesystem);
  }

  @Test
  public void testExecute1() {
    try {

      String output = Grep.execute("grep -R \"ex\" /", filesystem);
      assertEquals(output,
          "/existing:existing content\n/second:existing content");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testExecute2() {
    try {

      Echo.execute("echo \"existing content\" > /2/5/existing", filesystem);

      Echo.execute("echo \"existing content\" > /2/5/second", filesystem);
      String output = Grep.execute("grep -R \"ex\" /2/5/", filesystem);

      assertEquals(output, "/2/5/existing:existing content\n"
          + "/2/5/second:existing content");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testExecute3() {
    try {

      Echo.execute("echo \"existing content\" > /2/5/existing", filesystem);

      Echo.execute("echo \"existing content2\" >> /2/5/existing", filesystem);
      String output = Grep.execute("grep  \"ex\" /2/5/existing", filesystem);

      assertEquals(output, "/2/5/existing:existing content\n/"
          + "2/5/existing:existing content2");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @After
  public void tearDown() {
    FileSystem.deleteAll(filesystem);
  }

}
