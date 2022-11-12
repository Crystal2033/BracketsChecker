package Mechanic;

import Exceptions.BracketPositionError;
import Exceptions.ParsingError;
import MyCollections.Pair;
import models.BracketInfo;
import models.Direction;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;

public class BracketsChecker {
    private final ArrayDeque<Pair<String, Pair<Integer, Integer>>> stackBracketsAndPos = new ArrayDeque<>();
    private Map<String, BracketInfo> bracketsMap;
    private int lineCounter = 1;
    private StringBuilder stringBuilder;

    public void setConfigFile(String configFileName) throws ParsingError, IOException, JSONException {
        ConfigParser configParser = new ConfigParser(configFileName);
        bracketsMap = configParser.getBracketsMap();
        stringBuilder = new StringBuilder();
    }

    public void checkBracketsCorrectOrException(String checkFileName) throws IOException, BracketPositionError {
        FileDataGetter fileDataGetter = new FileDataGetter(checkFileName);
        if (!stringBuilder.isEmpty()) {
            stringBuilder.delete(0, stringBuilder.length());
        }

        while (fileDataGetter.hasNextLine()) {
            checkBracketsCorrectnessOrException(fileDataGetter.getNextLine());
            lineCounter++;
        }
        if (!stackBracketsAndPos.isEmpty()) {
            Pair<Integer, Integer> errorBracketPosition = stackBracketsAndPos.peekFirst().second;
            throw new BracketPositionError("Not found pair bracket. Opened is on: ", errorBracketPosition.first, errorBracketPosition.second);
        }
    }

    private void checkBracketsCorrectnessOrException(String textLine) throws BracketPositionError {
        for (int i = 0; i < textLine.length(); i++) {
            String currentCharInString = String.valueOf(textLine.charAt(i));
            if (bracketsMap.containsKey(currentCharInString)) {
                symbolIsBracketCase(currentCharInString, i + 1);
            }
        }
    }

    private void symbolIsBracketCase(String currentBracket, int symbolPos) throws BracketPositionError {
        BracketInfo currentBracketInfo = bracketsMap.get(currentBracket);
        if (currentBracketInfo.getDirection().equals(Direction.BOTH)) {
            bothDirectionsBracketCase(currentBracketInfo, currentBracket, symbolPos);
            return;
        }

        if (currentBracketInfo.getDirection().equals(Direction.LEFT)) {
            leftDirectionBracketCase(currentBracket, symbolPos);
            return;
        }

        rightDirectionBracketCase(currentBracketInfo, symbolPos);
    }

    private void bothDirectionsBracketCase(BracketInfo currentBracketInfo, String currentBracket, int symbolPos) {
        if (stackBracketsAndPos.isEmpty()) {
            stackBracketsAndPos.push(Pair.create(currentBracket, Pair.create(lineCounter, symbolPos)));
            return;
        }

        String prevStackBracket = stackBracketsAndPos.peekFirst().first;
        if (prevStackBracket.equals(currentBracketInfo.getPairBracket())) {
            stackBracketsAndPos.pop();
            return;
        }
        stackBracketsAndPos.push(Pair.create(currentBracket, Pair.create(lineCounter, symbolPos)));
    }

    private void leftDirectionBracketCase(String currentBracket, int symbolPos) {
        stackBracketsAndPos.push(Pair.create(currentBracket, Pair.create(lineCounter, symbolPos)));
    }


    private void rightDirectionBracketCase(BracketInfo bracketInfo, int symbolPos) throws BracketPositionError {
        if (stackBracketsAndPos.isEmpty()) {
            throw new BracketPositionError(lineCounter, symbolPos);
        }

        String prevStackBracket = stackBracketsAndPos.peekFirst().first;
        if (prevStackBracket.equals(bracketInfo.getPairBracket())) {
            stackBracketsAndPos.pop();
            return;
        }

        Pair<Integer, Integer> positionOfOpened = stackBracketsAndPos.peekFirst().second;
        throw new BracketPositionError("Not found pair bracket. Opened is on: ",
                positionOfOpened.first, positionOfOpened.second);
    }
}
