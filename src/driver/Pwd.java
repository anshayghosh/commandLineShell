/**
 * 
 */
package driver;

import java.util.*;

import driver.PathFinder.InvalidPathError;

public class Pwd {
  /**
   * Print the current working Directory of given FileSystem
   * 
   * @param str the input command without whitespace
   * @param fs FileSystem being accessed
   * @return String containing current Directory's pathname
   */
  public static String execute(String[] str, FileSystem fs) {
    return (fs.curDir.getPathname());
  }
}
