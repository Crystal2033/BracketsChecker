package Exceptions;

import Colors.ConsoleColors;

public class ParsingError extends Exception{
    private final String notExistingFileName;
    private final int problemPos;
    public ParsingError(String fileName, int problemPos){
        this.notExistingFileName = fileName;
        this.problemPos = problemPos;
    }

    public ParsingError(String fileName){
        this(fileName, 0);
    }

    @Override
    public String getMessage(){
        return "File " + notExistingFileName + " impossible to parse, because there is a mistake on "
                + ConsoleColors.YELLOW + problemPos + ".";
    }
}
