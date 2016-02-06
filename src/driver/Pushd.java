package driver;

import driver.PathFinder.InvalidPathError;

public class Pushd {


  /**
   * Pushes current directory onto the DirectoryStack in fs
   * @param fs FileSystem currently being accessed
   * @param str String[] of inputed command
   * @throws InvalidPathError if provided path in str is invalid
   */
  public static void execute(FileSystem fs, String[] str)
      throws InvalidPathError {
    fs.DirStack.push(fs.curDir);
    try {
      CD.execute(fs, str);
    } catch (Exception e) {
      throw new InvalidPathError(e.getMessage());
    }
  }
}
