package driver;

import java.util.ArrayList;
import driver.Echo.InvalidCommand;
public class History {

  /**
   * Prints out all the commands contained in historyList unless provided str
   * contains an argument, then print the last str[1] commands inputed.
   * 
   * @param historyList ArrayList<String[]> of inputed commands
   * @param str String[] of inputed arguments from user console
   * @throws InvalidCommand if value at str[1] is greater than number of 
   * elements contained in historyList
   */
  public static String execute(ArrayList<String[]> historyList, String[] str) 
      throws InvalidCommand{
    //Checks whether command has arguments, if not simple loop execution to
    //receive the history of vie the historyList.
    String output = "";
    if (str.length == 1){
      for(int i = 0;i<historyList.size();i++){
        String[] temp = historyList.get(i);
        output = output+(i+1);
        for(int j = 0;j<temp.length;j++){
          output = output + " "+temp[j];
        }
        output = output + "\n";
      }
    }
    else{
      //If contains an argument, checks he argument and returns the specific
      //portion of the history List. 
      int lim = Integer.parseInt(str[1]);
      if (lim>historyList.size()){
        throw new InvalidCommand(
            "An error occured: Value too great, not enough commands "
                + "in history.");
      }
      for(int i = historyList.size()-lim;i<historyList.size();i++){
        String[] temp = historyList.get(i);
        output = output+(i+1);
        for(int j = 0;j<temp.length;j++){
          output = output + " "+temp[j];
        }
        output = output + "\n";
      }
    }
    return output;
  }

}
