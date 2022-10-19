package Exceptions;

import Colors.ConsoleColors;

public class ParsingError extends Exception{
    private final String notExistingFileName;
    private final String errorMessage;
    public ParsingError(String errorMessage, String fileName){
        this.notExistingFileName = fileName;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage(){
        return "File " + notExistingFileName + " impossible to parse. Cause:  "
                + ConsoleColors.RED_UNDERLINED + errorMessage;
    }
}
