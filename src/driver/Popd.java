package driver;

import driver.Echo.InvalidCommand;

public class Popd {
    /**
     * Changes current directory to the directory referenced by the top most 
     * directory in the DirectoryStack.
     * @param fs FileSystem being accessed
     * @throws InvalidCommand if Directory in DirectoryStack doesn't exist
     */
    public static void execute(FileSystem fs) throws InvalidCommand{
     try{
       fs.curDir=fs.DirStack.Pop();
     }catch (Exception e){
       throw new InvalidCommand (e.getMessage());
     }
      
    }
}
