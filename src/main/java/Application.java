import Colors.ConsoleColors;
import Exceptions.BracketPositionError;
import Exceptions.ParsingError;
import Mechanic.BracketsChecker;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        System.out.println(ConsoleColors.CYAN_BOLD + "Developer: " + ConsoleColors.BLUE_BOLD
                + "Kulikov Pavel, M8O-311");
        System.out.println(ConsoleColors.PURPLE_BOLD + "This program is able to check brackets in text file." +
                " Settings sets in configure file, which is first arg in program." + ConsoleColors.RESET);
        System.out.println();
        if (args.length != 2) {
            System.out.println("Not enough arguments");
            return;
        }
        String configFileName = args[0];
        String checkingFileName = args[1];
        System.out.println(ConsoleColors.WHITE_BOLD + "Checking...");
        try {
            BracketsChecker fileCheckerByConfigure = new BracketsChecker();
            fileCheckerByConfigure.setConfigFile(configFileName);
            fileCheckerByConfigure.checkBracketsCorrectOrException(checkingFileName);
        } catch (FileNotFoundException | ParsingError fileNotFoundException) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + fileNotFoundException.getMessage());
            return;
        } catch (IOException ioException) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Input output error. FileReader error. " + ioException.getMessage());
            return;
        } catch (JSONException jsonException) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "There is an error with configure file. Check it better. " + jsonException.getMessage());
            return;
        } catch (BracketPositionError bracketPositionError) {
            System.out.println(bracketPositionError.getMessage());
            return;
        }

        System.out.println(ConsoleColors.WHITE_BOLD + "Check is done.");

        System.out.println(ConsoleColors.GREEN_BOLD + "Everything is okay." + ConsoleColors.RESET);
    }
}
