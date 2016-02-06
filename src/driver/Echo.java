/**
 * 
 */
package driver;

import driver.PathFinder.InvalidPathError;

public class Echo {

  /**
   * quotationCheck takes a String and checks to ensure there is an appropriate 
   * number of quotations and arguments.
   * @param command The inputed String command from the user console.
   * @return String of the content from command
   * @throws InvalidCommand If there is an incorrect number of quotes, 
   * or too many arguments provided within command.
   */
  public static String quotationCheck(String command) throws InvalidCommand {
    int firstQuotation = command.indexOf('\"');
    int lastQuotation = command.lastIndexOf('\"');
    if (firstQuotation == -1) {
      throw new InvalidCommand("Invalid command: content must be put within "
          + "quotation marks");
    }
    else if (lastQuotation == firstQuotation) {
      throw new InvalidCommand("Invalid command: content must be put within "
          + "quotation marks. Missing second quotation mark.");
    }
    String newContent = new String();
    if (lastQuotation -1 != firstQuotation) {
      newContent = command.substring(firstQuotation + 1, lastQuotation);
    }
    else {newContent = null;}
    String[] args = command.split("\"");
    args[0] = args[0].trim();
    if (args[0].length() > 4) {
      throw new InvalidCommand("Invalid command: too many arguments"
          + "the command"+args[0]+"is "+args[0].length()+"long");
    }
    return newContent;
  }

  /**
   * Takes an Echo command with a valid number of parameters.
   * Echo either replaces or appends content into an appropriate file based on
   * provided input. 
   * 
   * @param command Raw input command
   * @param fs The FileSystem on which it acts
   * 
   * @return empty String
   * @throws InvalidPathError If the PATH provided with command is invalid.
   * @throws InvalidCommand If provided command doesn't have ">" or ">>" 
   * correctly inputed
   */
  public static String execute(String command, FileSystem fs)
      throws InvalidCommand, InvalidPathError {
    // use Pathfinder2,
    // first parse the content between the two quotation, by searching the
    // quotation
    Directory tarDir;
    int first, last;
    String pathname;
    String[] args;

    String newContent = quotationCheck(command);

    args = command.split("\"");
    args[0] = args[0].trim();
    pathname = args[args.length - 1];
    // reuse "first" and "second" field
    if (args.length==2) //need to print to screen
      {//System.out.println(newContent);
      return newContent;
      
      }
    first = pathname.indexOf('>');
    last = pathname.lastIndexOf('>');
    if (first == -1) {
      throw new InvalidCommand("Invalid command: missing "
          + "> or >> before directory");
    }
    // pathname shouldn't contain the >
    pathname = pathname.replaceAll(">", " ").trim();
    try{
      tarDir = PathFinder.findParentDir(pathname, fs);  
    }catch (Exception e){
      throw new InvalidPathError(e.getMessage());
    }
    
    String[] name = pathname.split("/");
    String filename = name[name.length - 1];
    // looking for the file
    for (File dummy : tarDir.getFiles())
      if (dummy.getFileName().equals(filename)) {
        if (first == last) {
          dummy.replace(newContent);
          return "";
          }
        else {
          dummy.append(System.getProperty("line.separator")+newContent);
          return "";
      }
        }
    File.completeFileCreate(filename, newContent, tarDir);
    return "";
  }

  public static class InvalidCommand extends Exception {
    public InvalidCommand(String message) {
      super(message);
    }
  }

}
