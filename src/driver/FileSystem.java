package driver;

public class FileSystem {

  /**
   * singleReference ensures there is only one instance of a FileSystem
   * root the root Directory
   * curDir the current directory of the file system
   */
  private static FileSystem singleReference = null;
  public Directory root;
  public Directory curDir;
  public DirectoryStack DirStack; 

  /**
   * Creates a new FileSystem, called with createFileSystemInstance()
   */
  private FileSystem() {
    root = new Directory("/");
    curDir = root;
    DirStack=DirectoryStack.CreateDirectoryStack();
  }

  /**
   * Ensures only a single instance of FileSystem is made.
   * 
   * @return singleReference the FileSystem
   */
  public static FileSystem createFileSystemInstance() {
    if (singleReference == null)
      singleReference = new FileSystem();
    return singleReference;
  }

  /**
   * Removes all references inside of FileSystem, effectively destroying it
   * 
   * @param fs Target FileSystem
   */
  public static void deleteAll(FileSystem fs) {
    singleReference = null;
    fs.root = null;
    fs.curDir = null;

  }
}
