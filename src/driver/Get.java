/**
 * 
 */
package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Jeffrey
 *
 */
public class Get {

  public static void execute(FileSystem fileSystem, String command) 
      throws Exception {
    // TODO Auto-generated method stub
    String[] inputedArguments = Parser.parse(command);
    String completedFileContents = "";
    URL url = new URL(inputedArguments[1]);
      String fileName = inputedArguments[1].substring(inputedArguments[1]
          .lastIndexOf("/") + 1);
      URLConnection urlConnection = url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
          .getInputStream()));
      String input;
      while ((input = in.readLine()) != null){
        completedFileContents = completedFileContents + input + "\n";
      }
      in.close();
      try {
        File possibleduplicate = PathFinder.findFile(fileSystem, fileName);
        possibleduplicate.replace(completedFileContents);
      } catch (Exception e){
        File.completeFileCreate(fileName, completedFileContents, 
            fileSystem.curDir);
      }
     


  }

}
