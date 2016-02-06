/**
 * 
 */
package driver;

import java.util.Arrays;

import driver.PathFinder.InvalidPathError;


public class LS {
  /**
   * Return a String of the contents of the current directory, a provided 
   * directory, or a filename
   *
   * @param filesystem is the shell's FileSystem instance
   * @param parameters is a String [] which contains the input from the command 
   * line split by whitespaces.
   * @return returns a String that is the appropriate output based on parameters
   * If parameters contains just ls then a String representing the contents of
   * the current directory. If parameters[1] is -R or -r then the PATHs provided
   * would be recursively traversed and have their contents printed or their 
   * filename if the PATH point to a File. If no PATH is provided but the -R
   * flag if provided then the current directory is recursively traversed and 
   * its contents returns.
   * @throws InvalidPathError if the PATH(s) provided in parameters is invalid
   */
  public static String execute(FileSystem fileSystem, String[] parameters)
      throws InvalidPathError {
    // if the inputed command is just ls, then print the contents of the
    // current directory
    boolean recursiveFlag = false;
    String completedOutput = "";
    if (parameters.length > 1){
      if (parameters[1].equals("-R") || parameters[1].equals("-r")){
        recursiveFlag = true;
      }
    }
    if (parameters.length == 1) {
      for (Directory dir : fileSystem.curDir.getDirectories()) {
        completedOutput = completedOutput + dir.getName() + "\n";
      }
      for (File file : fileSystem.curDir.getFiles()) {
        completedOutput = completedOutput + file.getFileName() + "\n";      }
      return completedOutput;
    }
    if (!recursiveFlag){
      String[] paths = Arrays.copyOfRange(parameters, 1, parameters.length);
      Directory dir;
      for (String path : paths){
        dir = PathFinder.findDirwithFile(path, fileSystem);
        completedOutput = completedOutput + printPath(fileSystem, path);
      }
      return completedOutput;
    }

    if (recursiveFlag) {
      if (parameters.length == 2) {
        if (fileSystem.root == fileSystem.curDir) {
          completedOutput = recursivePrintDirectory(fileSystem, "/");
        }
        else {
          completedOutput = recursivePrintDirectory(fileSystem, fileSystem.curDir.getPathname());
        }
        return completedOutput;
      }
      String[] paths = Arrays.copyOfRange(parameters, 2, parameters.length);
      for (String path : paths) {
        completedOutput = completedOutput + recursivePrintDirectory(fileSystem, path);
      }
      return completedOutput;
    }
    return completedOutput;

  }




  private static String recursivePrintDirectory(FileSystem fileSystem, String path) throws InvalidPathError{
    if (PathFinder.FileDirCheck(path, fileSystem)){
      Directory dir = PathFinder.findDirwithFile(path, fileSystem);
      File file = PathFinder.findFile(fileSystem, path);
      return file.getFileName()+ "\n";
    }
    String completedOutput = "";
    completedOutput = completedOutput + printPath(fileSystem, path);
    Directory thisDir = PathFinder.findDir(path, fileSystem);
    if (!thisDir.getDirectories().isEmpty()) {
      for (Directory d : thisDir.getDirectories() ){
        completedOutput = completedOutput + recursivePrintDirectory(fileSystem, d.getPathname());
      }
    }
    return completedOutput;

  }

  private static String printPath(FileSystem fileSystem, String path) 
      throws InvalidPathError {
    if (PathFinder.FileDirCheck(path, fileSystem)) {
      Directory dir = PathFinder.findDirwithFile(path, fileSystem);
      File file = PathFinder.findFile(fileSystem, path);
      return file.getFileName()+ "\n";
    }
    String outPut = "";
    Directory dir = PathFinder.findDir(path, fileSystem);
    outPut = outPut + dir.getName() + ":\n";
    for (Directory d : dir.getDirectories()) {
      outPut = outPut + d.getName() + "\n";
    }
    for (File f : dir.getFiles()) {
      outPut = outPut + f.getFileName() + "\n";
    }
    outPut = outPut + "\n";
    return outPut;

  }


  void pathCheck(String[] paths) {


  }
}