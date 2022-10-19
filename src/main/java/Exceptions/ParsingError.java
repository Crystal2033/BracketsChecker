package Exceptions;

import Colors.ConsoleColors;

public class ParsingError extends Exception{
    private final String notExistingFileName;
    private final String errorMessage;
    public ParsingError(String errorMessage, String fileName, int problemPos){
        this.notExistingFileName = fileName;
        this.errorMessage = errorMessage;
    }

    public ParsingError(String errorMessage, String fileName){
        this(errorMessage, fileName, 0);
    }

    @Override
    public String getMessage(){
        return "File " + notExistingFileName + " impossible to parse. Cause:  "
                + ConsoleColors.RED_UNDERLINED + errorMessage;
    }
}
