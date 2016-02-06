package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.FileSystem;
import driver.Parser;
import driver.Popd;
import driver.Pushd;
import driver.Echo.InvalidCommand;
import driver.PathFinder.InvalidPathError;

public class PopdTest {
  FileSystem filesystem;

  @Before
  public void setUp() throws InvalidPathError, InvalidCommand {
   
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
    //initial a file structure as : /2/5   
    // /3/4
  }
  @Test
  public void testExecute() {
    String[] str=Parser.parse("cd /3/4");
    //for (String a:str)
    //System.out.println(a);
    try{
      Pushd.execute(filesystem,str);   
      Popd.execute(filesystem);
    }catch (Exception e){
      System.out.println("invalid path"+e.getMessage());
    }
    System.out.println(filesystem.curDir.getName());
    System.out.println(filesystem.curDir.getPathname());
    assertEquals("",filesystem.curDir.getPathname());
  }

}
