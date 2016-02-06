package driver;


import java.util.Arrays;
import java.util.ArrayList;

import com.sun.xml.internal.ws.util.StringUtils;

import driver.PathFinder.InvalidPathError;
import driver.Echo.InvalidCommand;

public class Parser {

  private static String targetFileName;
  public static ArrayList<String[]> historyList=new ArrayList<String[]>();

  /**
   * Replaces one or more whitespaces with a single whitespace. Then splits the
   * String str by whitespace.
   *
   * @param String str which represents the input on the command line
   * @return String[] which is str split by whitespaces.
   */
  public static String[] parse(String str) {
    String[] temp = str.trim().replaceAll("\\s+", " ").split(" ");
    historyList.add(temp);
    return temp;

  }

  private static void consolePrint(String message) {
    if (!message.isEmpty())
      System.out.println(message);
    
  }

  public static boolean redirectionTest(String[] str) {
    if (str[0].equals("echo"))
      return false; // temporary measure to pre-emptively avoid problems
    if (str.length < 3)
      return false;
    // else check second last element in array
    String lessThanSign = str[(str.length - 2)];
    if (lessThanSign.equals(">") || lessThanSign.equals(">>"))
      return true;
    // if lessThanSign wasn't actually a less than sign,
    return false;
  }

  /**
   * Execute the appropriate command located in the first position of String[]
   * str. Execute checks that the String[] str contains the correct number of
   * arguments for the individual command.
   *
   * @param str is a String [] which contains the input from the command line
   *        split by whitespaces.
   * @return returns 0 unless the exit command is run then the value is 1.
   */
  public static int execute(FileSystem filesystem, String[] str, String command)
  {try {
      if (str[0].equals("exit") && str.length == 1) {
        return 1;
      }
      // At the moment, redirection is going to be funky
      // in regards to things that take command as input, aka. echo() and get()
      // so far.
      boolean invalidCommandFlag = true;
      String output = "";

      boolean redirection = redirectionTest(str);
      String[] lastTwoParams = new String[2];

      if (redirection) { // aka. there are >= 3 params, and with >or>> fileName
        lastTwoParams[0] = str[str.length - 2];
        lastTwoParams[1] = str[str.length - 1];
        str = Arrays.copyOfRange(str, 0, str.length - 2);
      }

      if (str[0].equals("mkdir") && str.length >= 2) {
        output = Mkdir.execute(str, filesystem);
        invalidCommandFlag = false;
      }
      
      if (str[0].substring(0, 1).equals("!") && str.length == 1) {
        return NumberCommand.execute(filesystem, str, historyList);
      }
      
      if (str[0].equals("cd") && str.length == 2) {
        output = CD.execute(filesystem, str);
        invalidCommandFlag = false;
      }

      if (str[0].equals("ls")) {
        // need to review LS output afterwards
        output = LS.execute(filesystem, str);
        if (output.equals(""))
          System.out.println(output);
        invalidCommandFlag = false;
      }

      if (str[0].equals("pwd") && str.length == 1) {
        output = Pwd.execute(str, filesystem);
        invalidCommandFlag = false;
      }

      if (str[0].equals("mv") && str.length == 3) {
        output = MV.execute(str, filesystem);
        invalidCommandFlag = false;
      }

      if (str[0].equals("cp") && str.length == 3) {
        output = CP.execute(str, filesystem);
        invalidCommandFlag = false;
      }
      if (str[0].equals("cat") && str.length == 2) {
        output = Cat.execute(str, filesystem);
        invalidCommandFlag = false;
      }
      // if (str[0].equals("echo") && str.length >= 2){
      if (str[0].equals("echo")) {
        // this implementation let echo do the error checking
        consolePrint(Echo.execute(command, filesystem));
        invalidCommandFlag = false;
      }
      if (str[0].equals("get")) {
        Get.execute(filesystem, command);
        invalidCommandFlag = false;
      }
      if (str[0].equals("man")) {
        Manual.execute(str);
        invalidCommandFlag = false;
      }
      if (str[0].equals("popd")) {
        Popd.execute(filesystem);
        invalidCommandFlag = false;
      }
      if (str[0].equals("pushd")) {
        Pushd.execute(filesystem, str);
        invalidCommandFlag = false;
      }
      if (str[0].equals("grep")) {
        output=Grep.execute(command,filesystem);
        invalidCommandFlag = false;
      }
      if (str[0].equals("history")&&((str.length==1)||(str.length==2))) {
        if(str.length ==2){
          //checks whether the inputed argument is an integer, if not, returns
          //an error.
          boolean checker = true;
          for(int i = 0;i<str[1].length();i++){
            if(!Character.isDigit(str[1].charAt(i)))
              checker = false;
          }
          if (checker==true){
            
            output = History.execute(historyList,str);
            invalidCommandFlag = false;
          }
        }
        else{
          output = History.execute(historyList,str);
          invalidCommandFlag = false;
        }
        
      }
      if (invalidCommandFlag) {
        throw new InvalidCommand(
            "An error occured: incorrect number of arguments "
                + "or incorrect command name");
      }
      
      // if there was a valid command run
      if (redirection) {
        String echoCommand =
         // echoCommand = echo "output" > fileName
            "echo \"" + output + "\" " + lastTwoParams[0] + lastTwoParams[1];
        System.out.println(echoCommand);
        consolePrint(Echo.execute(echoCommand, filesystem));
        return 0;
      }
      
      else {
        consolePrint(output);
        return 0;
      }
      
    } catch (Exception e) {
      consolePrint(e.getMessage());
      return 0;
    }
  }
}
