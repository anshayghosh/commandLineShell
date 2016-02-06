package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.Echo;
import driver.File;
import driver.FileSystem;
import driver.LS;
import driver.Mkdir;
import driver.Echo.InvalidCommand;
import driver.Parser;
import driver.PathFinder.InvalidPathError;
import driver.Pushd;

public class PushdTest {
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
  public void testExecute1() {
    String[] str=Parser.parse("cd /3/4");
    //for (String a:str)
    //System.out.println(a);
    try{
      Pushd.execute(filesystem,str);   
    }catch (Exception e){
      System.out.println("invalid path"+e.getMessage());
    }
    System.out.println(filesystem.curDir.getName());
    System.out.println(filesystem.curDir.getPathname());
    assertEquals("/3/4",filesystem.curDir.getPathname());
  }
  @After
  public void tearDown() {
    FileSystem.deleteAll(filesystem);
  }

}
