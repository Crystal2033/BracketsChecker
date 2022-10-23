package Mechanic;

import Exceptions.ParsingError;
import MyCollections.Pair;
import Settings.SettingNames;
import models.BracketInfo;
import models.Direction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigParser {
    private final FileDataGetter fileDataGetter;
    private final Map<String, BracketInfo> bracketsMap = new HashMap<>();
    private final String fileName;
    private JSONArray bracketsJSONArray;

    public ConfigParser(String configFileName) throws IOException {
        fileDataGetter = new FileDataGetter(configFileName);
        fileName = configFileName;
    }

    public Map<String, BracketInfo> getBracketsMap() throws IOException, JSONException, ParsingError {
        StringBuilder stringBuilder = new StringBuilder();

        while(fileDataGetter.hasNextLine()){
            stringBuilder.append(fileDataGetter.getNextLine());
        }
        parseAndAddBracketsInMap(stringBuilder);
        return bracketsMap;
    }

    private void parseAndAddBracketsInMap(StringBuilder stringBuilder) throws JSONException, ParsingError {
        JSONObject jsonParser = new JSONObject(stringBuilder.toString());
        bracketsJSONArray = jsonParser.getJSONArray(SettingNames.bracketsJSONKey);
        for (int i = 0; i < bracketsJSONArray.length(); i++){
            createNewJsonPairByPos(i);
        }
    }

    private void checkExistInMapAndInsert(String bracketJSONValue, BracketInfo bracketInfo) throws ParsingError {
//        if(bracketsMap.containsValue(bracketInfo)){
//            throw new ParsingError("Bracket " + bracketInfo.getPairBracket() + " already exists in config file. ", this.fileName);
//        }

        if(bracketsMap.containsKey(bracketJSONValue)){ // left: |, right: |
            BracketInfo foundedBracketInfo =  bracketsMap.get(bracketJSONValue);

            if(!foundedBracketInfo.getDirection().equals(bracketInfo.getDirection()) && bracketJSONValue.equals(foundedBracketInfo.getPairBracket())){
                bracketsMap.put(bracketJSONValue, new BracketInfo(Direction.BOTH, bracketJSONValue));
                return;
            }
            throw new ParsingError("Bracket " + bracketJSONValue + " already exists in config file. ", this.fileName);
        }
        bracketsMap.put(bracketJSONValue, bracketInfo);
    }

    private void createNewJsonPairByPos(int i) throws ParsingError {
        String leftBracketJSONValue = bracketsJSONArray.getJSONObject(i).getString(SettingNames.leftBracketJSONKey);
        String rightBracketJSONValue = bracketsJSONArray.getJSONObject(i).getString(SettingNames.rightBracketJSONKey);

        checkExistInMapAndInsert(leftBracketJSONValue, new BracketInfo(Direction.LEFT, rightBracketJSONValue));
        checkExistInMapAndInsert(rightBracketJSONValue, new BracketInfo(Direction.RIGHT, leftBracketJSONValue));
    }
}
