package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.DirectoryStack;
import driver.FileSystem;
import driver.Echo.InvalidCommand;
import driver.PathFinder.InvalidPathError;

public class DirectoryStackTest {
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
    //initial a file structure as : /2/4    
    // /3/5
  }
  
  @Test
  public void testPush() {
    Directory test = new Directory("test");
    filesystem.DirStack.push(test);
    assertEquals(filesystem.DirStack.getList().getLast(),test);
    
  }
  
  @Test
  public void testPop() {
    Directory test = new Directory("test");
    filesystem.DirStack.push(test);
    try{
      filesystem.DirStack.Pop(); 
    }catch (Exception e){
      System.out.println("empty stack");
    }
   
    assertEquals(filesystem.DirStack.getList().getLast(),test);
  }

 

}
