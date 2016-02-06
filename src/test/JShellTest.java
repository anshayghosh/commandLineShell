package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import driver.JShell;

public class JShellTest {

  @Test
  //this test manily test whether the echo string works correctly when newline()
  //is typed
  public void testMain() {
    String[] args = null;
    
    ByteArrayInputStream in = new 
        ByteArrayInputStream("echo \"asd\\nasd\"".getBytes());
    //if the above string is treated as 15 character long, it means that \n 
    //isn't been treat as a lineseperatror but a \ and a n seperately
    //\ is at the index of 
    System.setIn(in);
    JShell.main(args);
    // do your thing
    
    // optionally, reset System.in to its original
    System.setIn(System.in);
  }

}
