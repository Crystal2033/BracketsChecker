package Mechanic;

import Exceptions.BracketPositionError;
import Exceptions.ParsingError;
import MyCollections.Pair;
import Settings.SettingNames;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;

public class BracketsChecker {
    private Map<String, Pair<String, String>> bracketsMap;
    private int lineCounter = 0;
    private StringBuilder stringBuilder;
    private final ArrayDeque<Pair<String, Pair<Integer, Integer>>> stackBracketsAndPos = new ArrayDeque<>();

    public void setConfigFile(String configFileName) throws ParsingError, IOException, JSONException {
        ConfigParser configParser = new ConfigParser(configFileName);
        bracketsMap = configParser.getBracketsMap();
        stringBuilder = new StringBuilder();
    }

    public void checkBracketsCorrectOrException(String checkFileName) throws IOException, BracketPositionError {
            FileDataGetter fileDataGetter = new FileDataGetter(checkFileName);
            if(!stringBuilder.isEmpty()){
                stringBuilder.delete(0, stringBuilder.length());
            }

            while(fileDataGetter.hasNextLine()){
                checkCorrectnessOrException(fileDataGetter.getNextLine());
                lineCounter++;
            }
            if(!stackBracketsAndPos.isEmpty()){
                Pair<Integer, Integer> errorBracketPosition = stackBracketsAndPos.peekFirst().second;
                throw new BracketPositionError("Not found pair bracket. Opened is on: ",errorBracketPosition.first, errorBracketPosition.second);
            }
    }

    private void checkCorrectnessOrException(String textLine) throws BracketPositionError{
        for (int i = 0; i < textLine.length(); i++){
            String currentCharInString = String.valueOf(textLine.charAt(i));
            if(bracketsMap.containsKey(currentCharInString)){
                symbolIsBracketCase(currentCharInString, i);
            }
        }
    }

    private void symbolIsBracketCase(String currentBracket,int symbolPos) throws BracketPositionError{
        Pair<String, String> directionAndPairBracket = bracketsMap.get(currentBracket);
        if(directionAndPairBracket.first.equals(SettingNames.bothBracketJSONKey)) {
            bothDirectionsBracketCase(directionAndPairBracket, currentBracket, symbolPos);
            return;
        }

        if(directionAndPairBracket.first.equals(SettingNames.leftBracketJSONKey)){
            leftDirectionBracketCase(currentBracket, symbolPos);
            return;
        }

        rightDirectionBracketCase(directionAndPairBracket, symbolPos);

    }

    private void bothDirectionsBracketCase(Pair<String, String> directionAndPairBracket, String currentBracket, int symbolPos){
            if(stackBracketsAndPos.isEmpty()){
                stackBracketsAndPos.push(Pair.create(currentBracket, Pair.create(lineCounter, symbolPos)));
                return;
            }

            String prevStackBracket = stackBracketsAndPos.peekFirst().first;
            if(prevStackBracket.equals(directionAndPairBracket.second)){
                stackBracketsAndPos.pop();
                return;
            }
            stackBracketsAndPos.push(Pair.create(currentBracket, Pair.create(lineCounter, symbolPos)));
        }

    private void leftDirectionBracketCase(String currentBracket, int symbolPos){
        stackBracketsAndPos.push(Pair.create(currentBracket, Pair.create(lineCounter, symbolPos)));
    }

    private void rightDirectionBracketCase(Pair<String, String> directionAndPairBracket, int symbolPos) throws BracketPositionError {
        if(stackBracketsAndPos.isEmpty()){
            throw new BracketPositionError(lineCounter, symbolPos);
        }

        String prevStackBracket = stackBracketsAndPos.peekFirst().first;
        if(prevStackBracket.equals(directionAndPairBracket.second)){
            stackBracketsAndPos.pop();
            return;
        }

        Pair<Integer, Integer> errorBracketPosition = stackBracketsAndPos.peekFirst().second;
        throw new BracketPositionError("Not found pair bracket. Opened is on: ",
                errorBracketPosition.first, errorBracketPosition.second);
    }
}
