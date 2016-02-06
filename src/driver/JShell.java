// **********************************************************
// Assignment2:
// Student1:
// UTOR user_name: wanghey2
// UT Student #: 1001910168
// Author:Heyang Wang
//
// Student2: Su Young Lee
// UTOR user_name: c4leesuy
// UT Student #: 999964882
// Author: Su Young Lee
//
// Student3:
// UTOR user_name: c5taseen
// UT Student #: 996652297
// Author: Jeffrey Taseen
//
// Student4: Anshay Ghosh
// UTOR user_name: ghoshans
// UT Student #: 1002019442
// Author: Anshay Ghosh
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package driver;

import java.util.Scanner;

public class JShell {

  /**
   * JShell through which user inputs commands to interact with a FileSystem
   */
  public static void main(String[] args) {
    String[] arg;
    FileSystem filesystem = FileSystem.createFileSystemInstance();

    Scanner in = new Scanner(System.in);

    while (true) {

      if (filesystem.root == filesystem.curDir) {
        System.out.print("/#: ");
      } else {
        System.out.print(filesystem.curDir.getName() + "#: ");
      }

      // arg = Parser.parse(in.nextLine());

      // initialize the map inside manual
      Manual.mapIni();

      String command = in.nextLine();
      //System.out.println(command.length());
      
     
      //command.replace("\\n", System.getProperty("line.separator"));
      //above try doesn't work,have no idea but try mannually replace the \n in
      //the string 
      int end=command.lastIndexOf("\\n", command.length()-1);
      // This finds the new line in command... WHY?  Maybe to use GREP
      //Trying to make echo able to write multiple lines sepearated by \n into 
      //a file, like what real echo command can do 
      
      if (end != -1)
        command=command.substring(0, end)
            +System.getProperty("line.separator")+command.substring(end+2,command.length() );
      
      arg = Parser.parse(command);
      if (Parser.execute(filesystem, arg, command) == 1) {
        break;
      }
    }

    in.close();

  }

}
