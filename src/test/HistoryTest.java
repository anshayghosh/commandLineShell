package test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Echo.InvalidCommand;
import driver.History;


public class HistoryTest {
  ArrayList<String[]> historyList;
  ArrayList<String[]> historyList1;
  ArrayList<String[]> emptyHistoryList;
  
  @Before
  public void setUp(){
    historyList=new ArrayList<String[]>();
    historyList1 = new ArrayList<String[]>();
    emptyHistoryList =  new ArrayList<String[]>();
    String[] input1 = {"mkdir","1"};
    String[] input2 = {"ls"};
    String[] input3 = {"pwd"};
    String[] input4 = {"cd","1"};
    String[] input5 = {"mkdir","2"};
    historyList.add(input1);
    historyList.add(input2);
    historyList.add(input3);
    historyList.add(input4);
    historyList.add(input5);
    historyList1.add(input1);
     
  }
  
  @Test
  public void executeTest() throws InvalidCommand{
    String[] parameters = {"history"};
    assertEquals("1 mkdir 1\n2 ls\n3 pwd\n4 cd 1\n5 mkdir 2\n",
        History.execute(historyList, parameters));  
  }
  
  @Test
  public void executeTest2() throws InvalidCommand{
    String[] parameters = {"history"};
    assertEquals("1 mkdir 1\n",
        History.execute(historyList1, parameters));  
  }
  @Test
  public void executeTest3() throws InvalidCommand{
    String[] parameters = {"history"};
    assertEquals("",
        History.execute(emptyHistoryList, parameters));  
  }
  
  @Test
  public void executeTest4() throws InvalidCommand{
    String[] parameters = {"history","2"};
    assertEquals("4 cd 1\n5 mkdir 2\n",
        History.execute(historyList, parameters));  
  }
  
  @Test
  public void executeTest5() throws InvalidCommand{
    String[] parameters = {"history","1"};
    assertEquals("5 mkdir 2\n",
        History.execute(historyList, parameters));  
  }


}
