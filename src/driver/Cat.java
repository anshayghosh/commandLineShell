/**
 * 
 */
package driver;

import driver.PathFinder.InvalidPathError;

public class Cat {

  /**
   * Takes a cat command with a valid number of parameters. Reads designated
   * file, and returns the file's string. Else it returns a relevant error
   * message.
   * 
   * @param str The input command with no whitespace
   * @param fs The FileSystem on which it acts
   * 
   * @return file contents, or error message
   * @throws InvalidPathError Implementation prevents this from ever happening
   */
  public static String execute(String[] str, FileSystem fs)
      throws InvalidPathError {
    String pathname = str[1];
    if (str[1].equals("/"))
      throw new InvalidPathError ("Error: File name was not provided.");

    String targetDirName = pathname.substring(pathname.lastIndexOf("/") + 1);
    Directory parentDir = PathFinder.findParentDir(pathname, fs);
    for (File aFile : parentDir.getFiles()) {
      if (aFile.getName().equals(targetDirName))
        return aFile.getContents();
    }
    throw new InvalidPathError("An error occurred: Invalid Pathname");
  }
}
