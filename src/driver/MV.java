/**
 * 
 */
package driver;

import driver.PathFinder.InvalidPathError;

// Takes the string parameters of the initial valid input
public class MV {
  /**
   * Takes an MV command with a valid number of parameters. Checks the following
   * and returns an error message if condition is met: - moving root directory -
   * moving directory into subdirectory - origin and destination directory are
   * the same Returns an empty string if no error message.
   * 
   * @param parameters The input command with no whitespace
   * @param fs The FileSystem on which it acts
   * 
   * @return Error message, or empty String
   * @throws InvalidPathError
   */
  private static String mvValidChecker(String[] parameters, FileSystem fs)
      throws InvalidPathError {
    Directory[] possible_dirs = new Directory[2];
    if (parameters[1].equals("/")) {
      throw new InvalidPathError("Error. "
          + "Can not move root directory elsewhere");
    }

    if (PathFinder.FileDirCheck(parameters[1], fs))
      // If indeed a valid File, then run MV for files
      return FileMV(parameters, fs);

    // else
    possible_dirs[0] = PathFinder.findDir(parameters[1], fs);
    possible_dirs[1] = PathFinder.findDir(parameters[2], fs);

    // Check if user is trying to move a dir into a dir with same name
    for (Directory subDir : possible_dirs[1].getDirectories()) {
      if (possible_dirs[0].getName().equals(subDir.getName()))
        throw new InvalidPathError(
            "Error. There is already a directory of that name.");
    }

    // Check if user is trying to move dir into a subdir
    boolean subDirVerify =
        possible_dirs[1].getPathname().toLowerCase()
            .contains(possible_dirs[0].getPathname().toLowerCase());
    if (subDirVerify) {
      throw new InvalidPathError("Error. "
          + "Can not move a directory into one of it's subdirectories.");
    }
    return ("");

  }

  /**
   * Given a valid File path, it will create a duplicated in target directory
   * 
   * returns "File" to signify success, else returns an Error
   * 
   * @param parameters Command input without whitespace
   * @param fs Target FileSystem on which this acts
   * @throws InvalidPathError
   */
  private static String FileMV(String[] parameters, FileSystem fs)
      throws InvalidPathError {
    // File has already been confirmed to exist
    Directory targetDirectory = PathFinder.findDir(parameters[2], fs);
    String fileName =
        parameters[1].substring(parameters[1].lastIndexOf("/") + 1);
    File targetFile = null;

    Directory fileContainingDirectory =
        PathFinder.findParentDir(parameters[1], fs);
    for (File searchFile : fileContainingDirectory.getFiles()) {
      if (searchFile.getName().equals(fileName))
        targetFile = searchFile;
    }

    // Check there are no files of same name already in target Directory
    for (File aFile : targetDirectory.getFiles()) {
      if (aFile.getName().equals(targetFile.getName()))
        throw new InvalidPathError(
            "Error: There is already a file of that name.");
    }

    // Else if all conditions are met
    targetDirectory.getFiles().add(targetFile);
    for (int i = 0; i < fileContainingDirectory.getFiles().size(); i++) {
      if (fileContainingDirectory.getFiles().get(i).getName()
          .equals(targetFile.getName()))
        fileContainingDirectory.getFiles().remove(i);
    }

    return ("File");
  }

  /**
   * Takes a MV command with a valid number of parameters. Takes input directory
   * and makes a duplicate move at target destination. Returns a relevant error
   * message or blank string.
   * 
   * @param parameters The input command with no whitespace
   * @param fs The FileSystem on which it acts
   * 
   * @return Error/Empty String
   * @throws InvalidPathError
   */
  public static String execute(String[] parameters, FileSystem fs)
      throws InvalidPathError {
    String error = mvValidChecker(parameters, fs);
    if (error == "File")
      return ("");
    else if (error != "")
      return (error);
    else {
      // if no errors then
      Directory[] possible_dirs = new Directory[2];
      possible_dirs[0] = PathFinder.findDir(parameters[1], fs);
      possible_dirs[1] = PathFinder.findDir(parameters[2], fs);

      String newpathname =
          possible_dirs[1].getPathname() + "/" + possible_dirs[0].getName();

      Directory targetParentDir = PathFinder.findParentDir(newpathname, fs);
      Directory oldParentDir =
          PathFinder.findParentDir(possible_dirs[0].getPathname(), fs);

      targetParentDir.addDirectory(possible_dirs[0]);
      possible_dirs[0].setPathname(newpathname); // updating moved file path

      // then need to delete reference in parent directory
      for (int i = 0; i < oldParentDir.getDirectories().size(); i++) {
        if (oldParentDir.getDirectories().get(i).getName()
            .equals(possible_dirs[0].getName()))
          oldParentDir.getDirectories().remove(i);
      }
    }
    return ("");
  }
}
