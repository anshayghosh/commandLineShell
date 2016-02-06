package driver;

import java.util.LinkedList;

import driver.Echo.InvalidCommand;

public class DirectoryStack {
     private static DirectoryStack singleReference = null;
     private LinkedList <Directory> AllDirectory;
     
     
     private DirectoryStack(){
       AllDirectory=new LinkedList<Directory>();
       
     }
     
     
     public static DirectoryStack CreateDirectoryStack(){
       if (singleReference == null)
         singleReference = new DirectoryStack();
       return singleReference;
     }
     
     public Directory Pop() throws InvalidCommand{
       try{
         Directory returnDir=AllDirectory.getLast();
         return returnDir;
       }catch (Exception e){
       throw new InvalidCommand("Invalid command: no previous directory exist");
       }
     }
     
     public void push (Directory dir){
       AllDirectory.add(dir);
     }
     public LinkedList getList(){
       return AllDirectory;
     }
}
