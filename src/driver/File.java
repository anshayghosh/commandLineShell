package driver;

public class File {
  /**
   * contents the text within the file
   * fileName the name of the file used to identify it
   */
  private String contents;
  private String fileName;

  /**
   * Creates a File given only a name and no contents.
   * Called with fileNameCreate factory method
   * @param nameInput the String input for file name
   */
  private File(String nameInput) {
    setFileName(nameInput);
    contents = "";
  }

  /**
   * Creates a File with name, and contents as input.
   * Called with completeFileCreate factory method
   * 
   * @param nameInput the String input for file name
   * @param contentInput String of contents of File
   */
  private File(String nameInput, String contentInput) {
    setFileName(nameInput);
    contents = contentInput;
  }

  /**
   * Creates a file with empty contents
   * 
   * @param nameInput String input of file name
   * @param targetDir Directory in which File is placed
   */
  public static void fileNameCreate(String nameInput, Directory targetDir) {
    targetDir.getFiles().add(new File(nameInput));
  }

  /**
   * Creates a file with specified name and contents
   * 
   * @param nameInput String input of file name
   * @param contentInput String input of file's contents
   * @param targetDir Directory in which File is placed
   */
  public static void completeFileCreate(String nameInput, String contentInput,
      Directory targetDir) {
    targetDir.getFiles().add(new File(nameInput, contentInput));
  }

  /**
   * Adds string to File's contents.
   * 
   * @param addedString String to be added to end of File's contents
   */
  public void append(String addedString) {
    contents += addedString;
  }
  
  /**
   * Replaces contents of File with new input
   * 
   * @param newString String to replace File's contents
   */
  public void replace(String newString) {
    contents = newString;
  }

  /**
   * Getter for File contents
   * 
   * @return contents
   */
  public String getContents() {
    return contents;
  }

  /**
   * Getter for File name
   * 
   * @return fileName
   */
  public String getName() {
    return getFileName();
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
