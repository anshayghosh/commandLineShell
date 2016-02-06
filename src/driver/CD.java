/**
 * 
 */
package driver;

import driver.PathFinder.InvalidPathError;

public class CD {
  
  private static boolean cdErrorChecker(FileSystem fileSystem,
      String[] parameters) {
    boolean atRootDirectory = (fileSystem.root == fileSystem.curDir);
    if (atRootDirectory && (parameters[1].equals(".."))) {
      return true;
    }
    return false;
  }
  /**
   * Change to the Directory referenced by the path provided in parameters[1]
   *
   * @param filesystem is the shell's FileSystem instance and parameters is a 
   * String [] which contains the input from the command line split by 
   * whitespaces.
   * @return returns an empty String
   * @throws InvalidPathError if the path provided in parameters[1] is invalid
   */
  public static String execute(FileSystem fileSystem, String[] parameters) 
      throws InvalidPathError {
    //Checks if currently in the root directory and if the provided path is ..
    if (!cdErrorChecker(fileSystem, parameters)) {
      //If path is a single . do nothing
      if (parameters[1].equals(".")) {} 
      //If path is two dots .. change to parent
      else if (parameters[1].equals("..")) {
        fileSystem.curDir = PathFinder.findParentDir(
            fileSystem.curDir.getPathname(), fileSystem);
      } 
      //Otherwise set current Directory to the returned value of findDir.
      else {
          fileSystem.curDir = PathFinder.findDir(parameters[1], fileSystem);
      }
    }
    return ("");
  }
}

