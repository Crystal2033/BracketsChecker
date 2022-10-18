import Colors.ConsoleColors;
import Exceptions.BracketPositionError;
import Exceptions.ParsingError;
import Mechanic.BracketsChecker;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Application {
//    private static final String PATH = "D:\\Paul\\Programming\\Java\\RPKS\\Labs\\BracketsChecker\\src\\main\\resources\\";
//    private static final String CONFIGURE_FILE_NAME = PATH + "ConfigureFile.txt";
//    private static final String TO_CHECK_FILE_NAME =PATH +  "checkingFile.txt";
    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("Not enough arguments");
            return;
        }
        String configFileName = args[0];
        String checkingFileName = args[1];
        try {
            BracketsChecker fileCheckerByConfigure = new BracketsChecker();
            fileCheckerByConfigure.setConfigFile(configFileName);
            fileCheckerByConfigure.checkBracketsCorrectOrException(checkingFileName);
        }
        catch (FileNotFoundException fileNotFoundException){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + fileNotFoundException.getMessage());
            return;
        }
        catch (IOException ioException){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Input output error. FileReader error. " + ioException.getMessage());
            return;
        }
        catch (JSONException jsonException){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "There is an error with configure file. Check it better. " + jsonException.getMessage());
            return;
        } catch (ParsingError parsingError) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + parsingError.getMessage());
            return;
        } catch (BracketPositionError bracketPositionError) {
            System.out.println(bracketPositionError.getMessage());
            return;
        }
        System.out.println("Okay");
    }
}
