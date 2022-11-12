package Exceptions;

import Colors.ConsoleColors;

public class BracketPositionError extends Exception{
    private final int errorPosition;
    private final int errorLine;
    private final String errorMessage;

    public BracketPositionError(String errorMessage, int errorLine, int errorPos){
        this.errorMessage = errorMessage;
        this.errorPosition = errorPos;
        this.errorLine = errorLine;
    }
    public BracketPositionError(int errorLine, int errorPos){
        this("Error is on: ", errorLine, errorPos);
    }
    @Override
    public String getMessage(){
        return ConsoleColors.RED_BOLD + errorMessage +
                ConsoleColors.YELLOW_BOLD + errorLine + " line and "
                + errorPosition + ConsoleColors.RED_BOLD + " position.";
    }
}
