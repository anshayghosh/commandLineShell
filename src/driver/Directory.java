package driver;

import java.util.ArrayList;

public class Directory {
  /**
   * name Directory's name. Used for identification
   * subDirectories ArrayList of subdirectories
   * files ArrayList of files
   * pathname Stores where the Directory is located
   * parent Directory's parent
   */
  private String name;
  private ArrayList<Directory> subDirectories;
  private ArrayList<File> files;
  private String pathname = "";

  /**
   * Creates a Directory with name n
   * 
   * @param n name of directory
   */
  public Directory(String n) {
    name = n;
    files = new ArrayList<File>();
    subDirectories = new ArrayList<Directory>();
  }

  /**
   * Adds a file to Directory
   * 
   * @param name of file
   */
  public void addFile(File name) {
    this.files.add(name);
  }

  /**
   * Adds a subDirectory to Directory
   * 
   * @param input name of Directory
   */
  public void addDirectory(Directory input) {
    this.subDirectories.add(input);
  }

  /**
   * Gets ArrayList of Files
   * @return files
   */
  public ArrayList<File> getFiles() {
    return files;
  }
  
  /**
   * Gets ArrayList of Directories
   * @return subDirectories
   */
  public ArrayList<Directory> getDirectories() {
    return subDirectories;
  }

  /**
   * Gets name of Directory
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets pathname of Directory
   * @return pathname
   */
  public String getPathname(){
    return pathname;
  }
  
  public void setPathname(String newPathname){
    pathname = newPathname;
  }
}


