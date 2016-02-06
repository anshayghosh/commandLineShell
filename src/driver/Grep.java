package driver;



import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import driver.Echo.InvalidCommand;
import driver.PathFinder.InvalidPathError;

public class Grep {
  
  
  /**
   * Takes a String command in the form grep [-R] REGEX PATH.... If there is no
   * -R provided return a String which contains any lines that contain the 
   * provided REGEX. If -R is provided and PATH is a directory, recursively 
   * traverse the directory and return a String which contains all matches to 
   * REGEX in the form: PATH to matching file: line that contained REGEX
   * 
   * @param command String raw input from user console
   * @param fs FileSystem that is being worked on
   * @return String containing appropriate output
   * @throws InvalidPathError if provided PATH is invalid
   * @throws InvalidCommand if provided command is incorrect
   */
  public static String execute(String command, FileSystem fs)
      throws InvalidPathError, InvalidCommand {
    String[] str = command.trim().replaceAll("\\s+", " ").split(" ");
    Pattern p;
    String find = "";
    // for (String aa: str)
    // System.out.println(aa);
    // check the format of the command
    if (str.length > 4)
      throw new InvalidCommand("invalid command, too many arguments"
          + "can accept at most 3 argumrnts,you typed " + str.length
          + " arguments");
    // doing quotaticsaon check and get the pattern string
    try {// do quotation check
      // in order to reuse the code from echo,need to make sure before
      // " there are only 4 character
      String newContent;
      if ((str[1].equals("-R")) || (str[1].equals("-r")))
        // System.out.println(str[0]+str[2]);
        newContent = Echo.quotationCheck(str[0] + str[2]);
      else
        newContent = Echo.quotationCheck(str[0] + str[1]);
      // System.out.println("pattern is"+newContent);
      p = Pattern.compile(newContent);
    } catch (Exception a) {
      throw new InvalidCommand(a.getMessage());
    }
    // if argument R is given, call the method to recursive traverse
    // the directory
    if ((str[1].equals("-R")) || (str[1].equals("-r"))) {
      // r R both work
      // before call recGrep ,check whethe the command is valid first
      if (str.length != 4)
        throw new InvalidCommand("invalid command, needd exactly 3"
            + "arguments");
      try {
        if (PathFinder.FileDirCheck(str[3], fs))
          throw new InvalidCommand("invalid command, require a "
              + "directory path");
        Directory tarDir;
        tarDir = PathFinder.findDir(str[3], fs);

        find = recGrep(str, tarDir, p);

      } catch (Exception e) {// catch the invalid path error
        throw new InvalidPathError(e.getMessage());
      }
    } else // no argument R, just check one file
    {
      // before checking the file ,check whether it's a valid command
      if (str.length != 3)
        throw new InvalidCommand("invalid command, needd exactly 2"
            + "arguments");
      // check whether the file exist, FileDirCheck will return true
      // if the file exist
      if (!PathFinder.FileDirCheck(str[2], fs))
        throw new InvalidCommand("invalid command, file doesn't exist");
      try {

        Directory tarDir;
        try { // pathfinder may throw error
          tarDir = PathFinder.findParentDir(str[2], fs);

        } catch (Exception e) {
          throw new InvalidPathError(e.getMessage());
        }
        File dummy = PathFinder.findFile(fs, str[2]);
        find = findPattern(p, dummy, tarDir);



        // }

      } catch (Exception e) {// catch the invalid path error
        throw new InvalidPathError(e.getMessage());
      }
    }
    if (find.equals(""))
      return ("no match found on this file");
    else
      return find;
  }

  private static String recGrep(String[] str, Directory tarDir, Pattern p)
      throws InvalidPathError {

    // this method is used for searching all the file within a directory
    String find = "";
    String sum = "";

    for (File dummy : tarDir.getFiles()) {

      find = findPattern(p, dummy, tarDir);

      if (!find.equals("")) {

        if (sum.equals("")) {
          // System.out.println("find= "+find);
          sum = find;
        }

        else
          sum = sum + System.getProperty("line.separator") + find;

      } else
        return ("not find on this file");

    }

    if (sum.equals(""))
      return ("no match found on this file");
    else
      return sum;

  }

  private static String findPattern(Pattern p, File fil, Directory tarDir) {

    Matcher m;
    String find = "";
    String[] lines = fil.getContents().split("\n");
    for (String e : lines) {

      m = p.matcher(e);
      if (m.find()) {
        if (find.equals(""))
          find =
              find + (tarDir.getPathname() + "/" + fil.getFileName() + ":" + e);
        else
          find =
              find + System.getProperty("line.separator")
                  + (tarDir.getPathname() + "/" + fil.getFileName() + ":" + e);
      }

    }

    return find;
  }
}
