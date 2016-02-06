package driver;

import java.util.Arrays;
import java.util.Iterator;

public class PathFinder {

  /**
   * pathfinder receive a string as the path for the file/directory,and the
   * current directory reference it will do the following thing: 1. find the
   * directory based on the given pathname 2. Return reference to directory if
   * exists or throw an error 3. if there are any mistake inside the path , path
   * finder will throw error
   * 
   *
   * @param 1. string contain the path 2. reference to the current directory
   * @return reference to the targeted directory
   */
  public static Directory findParentDir(String path, FileSystem fs)
      throws InvalidPathError {
    // ONLY TAKES FULL PATH NAME, MOSTLY BECAUSE I'M LAZY!
    Directory dir = null;
    if (path.equals(""))
      throw new InvalidPathError("Error: No parent directory of root");

    if (path.equals("/"))
      throw new InvalidPathError("An error occurred: Invalid Pathname");
    // no parent directory for
    // root.

    String[] arg = path.split("/");
    if (path.substring(0, 1).equals("/")) {
      if (arg.length == 2)
        dir = fs.root;
      else { // arg.length > 2
        path = path.substring(0, path.lastIndexOf("/"));
        dir = findDir(path, fs);
      }
    }

    else { // relative pathname
      dir = fs.curDir;

      String[] lengthTest = path.split("/");
      if (lengthTest.length == 1)
        return fs.curDir;
      else {
        path = path.substring(0, path.lastIndexOf("/"));
        dir = findDir(path, fs);
      }
    }
    return dir;


  }

  public static Directory findDir(String path, FileSystem fs)
      throws InvalidPathError {
    Directory dir;
    String[] arg;

    if (path.equals("/"))
      return fs.root;

    arg = path.split("/");
    if (path.substring(0, 1).equals("/")) {
      // going back to the root dir
      dir = fs.root;
      arg = Arrays.copyOfRange(arg, 1, arg.length);
    }

    else {
      dir = fs.curDir;
    }

    Directory curDir = dir;
    for (String directoryName : arg) {
      if (directoryName.equals(".."))
        curDir = PathFinder.findParentDir(curDir.getPathname(), fs);
      else {
        for (int i = 0; i <= curDir.getDirectories().size(); i++) {
          if (curDir.getDirectories().size() == 0) // if there aren't any
                                                   // subdirs
            throw new InvalidPathError("An error occurred: Invalid Pathname");

          if (directoryName.equals(curDir.getDirectories().get(i).getName())) {
            curDir = curDir.getDirectories().get(i);
            break;
          }

          // If end of the search and no subdirectory has the desired name,
          if ((i + 1) >= curDir.getDirectories().size())
            throw new InvalidPathError("An error occurred: Invalid Pathname");
        }
      }
    }
    return curDir;


  }

  public static Directory findDirwithFile(String path, FileSystem filesystem)
      throws InvalidPathError {
    Directory curDir;
    String[] arg;
    // if path is "/" return the root
    if (path.equals("/"))
      return filesystem.root;
    arg = path.split("/");
    // if the first character of path is "/" make dir root;
    if (path.substring(0, 1).equals("/")) {
      // going back to the root dir
      curDir = filesystem.root;
      arg = Arrays.copyOfRange(arg, 1, arg.length);
    }

    else {
      curDir = filesystem.curDir;
    }

    for (String directoryName : arg) {
      Iterator<Directory> itr = curDir.getDirectories().iterator();
      while (itr.hasNext()){
        Directory dir = (Directory)itr.next();
        Iterator<File> itrFiles = curDir.getFiles().iterator();
        while (itrFiles.hasNext()){
          File file = (File)itrFiles.next();
          if (directoryName.equalsIgnoreCase(file.getFileName())){
            return curDir;
          }
        }
        if (directoryName.equals(dir.getName())){
          curDir = dir;
          break;
        }
      }
      
//      for (int i = 0; i <= curDir.getDirectories().size(); i++) {
//        // Search for File
//        if ((i + 1) >= curDir.getDirectories().size()) {
//          // if at end of loop (curDir is file/directory name)
//          for (int j = 0; j < curDir.getFiles().size(); j++) {
//            if (directoryName.equals(curDir.getFiles().get(j).getFileName())){
//              return curDir;
//            }
//          }
//        }
//
//        if ((curDir.getDirectories().size() == 0)
//            && (curDir.getFiles().size() == 0))
//          // if there aren't any subdirs and no files throw an error
//          throw new InvalidPathError("An error occurred: Invalid Pathname");
//
//        if (directoryName.equals(curDir.getDirectories().get(i).getName())) {
//          curDir = curDir.getDirectories().get(i);
//          break;
//        }
//
//
//      }
    }
    return curDir;
  }

  /**
   * Returns true or false whether given path is a Directory or a File Returns
   * false if invalid path
   * 
   * @param pathname Path of object to be tested
   * @param fs FileSystem on which it asks
   * @return boolean true if File, false if Directory
   */
  public static boolean FileDirCheck(String pathname, FileSystem fs) {
    // First check whether it is or isn't a File
    try {
      Directory unsureDirFile = PathFinder.findDir(pathname, fs);
      return false;
    } catch (InvalidPathError e) {
    } // Error has to be raised for program to continue

    // Checked that there is no directory with that path. Now check for file
    try {
      Directory parentDir = PathFinder.findParentDir(pathname, fs);
      String posFileName = pathname.substring(pathname.lastIndexOf("/") + 1);
      for (File aFile : parentDir.getFiles()) {
        // Search for file name
        if (aFile.getName().equals(posFileName))
          return true;
      }
    } catch (InvalidPathError e2) {
      return false;
    }
    return false;
  }
  
  public static File findFile(FileSystem fileSystem, String path) 
      throws InvalidPathError {

      Directory dir = PathFinder.findDirwithFile(path, fileSystem);
      for (File f : dir.getFiles()) {
        if (f.getFileName().equals(path.substring(path.lastIndexOf("/") + 1))){
          return f;
        }
      }
      throw new InvalidPathError("file doesn't exist");
  }


  public static class InvalidPathError extends Exception {
    public InvalidPathError(String message) {
      super(message);
    }
  }
}
