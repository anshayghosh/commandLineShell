package driver;

import java.util.HashMap;

import driver.Echo.InvalidCommand;


public class Manual {

  // private map to map the commands with manual
  private static HashMap<String, String> m = new HashMap<String, String>();

  // manual into map
  public static String putManualintoMap(String command, String manual) {
    return m.put((String) command, (String) manual);
  }

  // record all the command we have
  private static String[] AllCommand = {"exit", "mkdir", "cd", "ls", "pwd",
      "mv", "cp", "cat", "echo", "man", "grep", "pushd", "popd", "!", "get",
      "history"};
  // please put your manual description here
  // if it's a real shell, better let each command hold their own manual or let
  // each command put their manual into the map
  private static String[] Manual = {
      // "exit"
      " NAME \n   exit \nSYNOPSIS \n   exit \n DESCRIPTION \n   "
          + "this command takes no argument,can be used to quit the jshell",
      // "mkdir"
      "manual for mkdir",
      // "cd"
      "NAME \n   cd \nSYNOPSIS \n   cd DIR \nDESCRIPTION \n   "
          + "Change to directory DIR. "
          + "DIR provided may be a partial path relative to the "
          + "\ncurrent directory or it may be a full path. "
          + "A full path begins with a single "
          + "\nslash: /. Two periods: .. means the parent directory "
          + "and a single period: . "
          + "\nmeans the current directory. The seperator for each "
          + "directory " + "in a path must be the single slash: /. "
          + "\nTo change to the root directory simply input a single "
          + "slash: /." + "\nPaths which don't exist or are incorrectly "
          + "inputted will result in an error message. ",
      // "ls",
      "NAME \n   ls \nSYNOPSIS \n   ls [-R] [PATH]... \nDESCRIPTION \n   "
      + "If -R is provided without a PATH recursively print contents of the "
      + "current directory."
      + "\nIf -R is provided with PATH(s) recursively print contents of PATH."
      + "\nor filename if PATH if a file.\n"
          
          + "If no PATH is provided, print the contents of "
          + "the current directory. "
          + "\nIf a PATH is provided and it is a file "
          + "then the file's name will be printed. "
          + "\nIf PATH is a directory"
          + "the directory name will be printed followed by the contents"
          + " of " + "\nthe directory. If PATH doesn't exist or is inputted "
          + "incorrectly" + " it will result in an " + "\nerror message.",
      // "pwd",
      "manual for pwd",
      // "mv",
      "NAME \n   mv \nSYNOPSIS \n   mv [PATH1] [PATH2] \n DESCRIPTION \n   "
          + "If PATH1 and PATH2 are the same, it will result in an error "
          + "message."
          + "\nIf a PATH1 is a subdirectory of PATH2, it will reult in an"
          + " error."
          + "\n Else, the directory in PATH1 will create a copy of itself "
          + "in PATH2"
          + "\n Then it will delete the reference of itself in the parent "
          + "dir of PATH1",
      // "cp",
      "NAME \n   cp \nSYNOPSIS \n   cp [PATH1] [PATH2] \n DESCRIPTION \n   "
          + "If PATH1 and PATH2 are the same, it will result in an error "
          + "message."
          + "\nIf a PATH1 is a subdirectory of PATH2, it will reult in an "
          + "error."
          + "\n Else, the directory in PATH1 will create a copy of itself "
          + "in PATH2",
      // "cat",
          "NAME \n   cat \nSYNOPSIS \n   cat FILE  \n DESCRIPTION \n   "
          + "returns the contents of the file",
      // "echo",
      "NAME \n   echo \nSYNOPSIS \n   echo string [>|>>] [path] \n "
          + "DESCRIPTION \n   "
          + "this command takes 2-4 commands, can be used to print out "
          + "content on shell" + "or to write/append string to file",
      // "man"
      "NAME \n   man \nSYNOPSIS \n   man argument \n DESCRIPTION \n   "
          + "this command only take one argument, if the argument is valid,"
          + "this command will return the manual for that command",
      // grep
      "NAME \n   grep \nSYNOPSIS \n   grep [-r] REGEX PATH \n DESCRIPTION "
          + "\n   "
          + "this command takes 3 or 4arguments, if -r is supplied, print "
          + "any " + "lines containing regex in path. which must be a file\n"
          + "\n if -r is supplied, and PATH is a directory, resursively "
          + "traverse"
          + " the directory and print all lines in all files that contain"
          + "REGEX",


      // pushd
      "NAME \n   pushd \nSYNOPSIS \n   pushd DIR \n DESCRIPTION \n   "
          + "save current directory to directory stack and changes the new "
          + "current directory to DIR",
      // popd

      "NAME \n   popd \nSYNOPSIS \n   popd  \n DESCRIPTION \n   "
          + "remove the top entry on directory stack and cd to it",
     // !(history)
          "NAME \n   !(history) \nSYNOPSIS \n   ![int]  \n DESCRIPTION \n   "
          + "Re executes the command at the respective integer on the "
          + "command line.",
      // get
          "NAME \n   get \nSYNOPSIS \n   get URL \nDESCRIPTION \n   "
          + "Retrieve the file located at URL and place it in the current "
          + "directory.",
       //history
          "NAME \n   history \nSYNOPSIS \n   history [int]  \n DESCRIPTION \n  "
          + " "
          + "If command is inputted just as history, return a list of all the"
          + "commands inputted previously, if inputted with an integer, return"
          + " those specific number of commands from the history of commands."
           
      };


  // initialize the map, record all the manual we have
  public static void mapIni() {
    int i = 0;
    for (String e : AllCommand) {
      putManualintoMap(e, Manual[i]);
      i++;
    }
  }


  public static void execute(String[] str) throws InvalidCommand {
    // more than one argument in man, invalid command,shall it throws an
    // exception and let the caller handle the error?
    if (str.length > 2)
      throw new InvalidCommand("invalid command,only "
          + "accepdt one valid argument");
    else {
      // find the corresponding command, and output its manual
      if (m.get(str[1]) == null)
        throw new InvalidCommand("invalid command " + str[1]);
      else
        System.out.println(m.get(str[1]));

    }
  }

}
