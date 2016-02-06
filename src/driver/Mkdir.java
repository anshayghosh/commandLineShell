/**
 * 
 */
package driver;

import java.util.Arrays;

import driver.PathFinder.InvalidPathError;

/**
 * 
 *
 */
public class Mkdir {

  /**
   * Creates a Directory. Will verify that there is no duplicate Directory of
   * the same name in the current Directory.
   * 
   * @param pathArray pathnames of Directories to be made
   * @param fs FileSystem to be targeted
   * @return Error message/Empty String
   * @throws InvalidPathError
   */
  public static String execute(String[] pathArray, FileSystem fs)
      throws InvalidPathError {
    
    pathArray = Arrays.copyOfRange(pathArray, 1, pathArray.length);
    // First check it is possible to make mkdirs
    for (String path : pathArray) {
      String errorMessage = mkDirCheck(path, fs);
      if (!errorMessage.equals(""))
        return errorMessage;
    }
    
    for (String path : pathArray) {
        Directory parentDir = PathFinder.findParentDir(path, fs);
        String newDirName = path.substring(path.lastIndexOf("/") + 1);
        Directory newDir = new Directory(newDirName);
  
        for (Directory subDirectory : parentDir.getDirectories()) {
          if (subDirectory.getName().equals(newDirName)) {
            throw new InvalidPathError ("Error, can not add file of same name");
          }
        }
        parentDir.addDirectory(newDir);
        newDir.setPathname(parentDir.getPathname() + "/" + newDirName);
    }
    return ("");
  }
  private static String mkDirCheck(String path, FileSystem fs) throws InvalidPathError {
      Directory parentDir = PathFinder.findParentDir(path, fs);
      String newDirName = path.substring(path.lastIndexOf("/") + 1);

      for (Directory subDirectory : parentDir.getDirectories()) {
        if (subDirectory.getName().equals(newDirName)) {
          throw new InvalidPathError ("Error, can not add file of same name");
        }
      }
      return ("");
  }
}
