package driver;

import java.util.ArrayList;

import driver.Echo.InvalidCommand;

public class NumberCommand {
  public static int execute(FileSystem filesystem, String[] str,
      ArrayList<String[]> historyList) throws InvalidCommand {

    try {
      int commandNumber =
          Integer.parseInt(str[0].substring(1, str[0].length()));

      try {
        String[] arrayCommand = historyList.get(commandNumber - 1);
        String command = "";
        for (String item : arrayCommand)
          command += item;
        return Parser.execute(filesystem, arrayCommand, command);
      } catch (Exception IndexOutOfBoundsException) {
        throw new InvalidCommand(
            "ERROR: There does not exist a command at that history number.");
      }
    } catch (NumberFormatException e) {
      throw new InvalidCommand("ERROR: Input command must be a number");
    }
  }
}
