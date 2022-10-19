package Mechanic;

import Exceptions.ParsingError;
import MyCollections.Pair;
import Settings.SettingNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigParser {
    private final FileDataGetter fileDataGetter;
    private final Map<String, Pair<String, String>> bracketsMap = new HashMap<>();
    private final String fileName;
    private JSONArray bracketsJSONArray;

    public ConfigParser(String configFileName) throws IOException {
        fileDataGetter = new FileDataGetter(configFileName);
        fileName = configFileName;
    }

    public Map<String, Pair<String, String>> getBracketsMap() throws IOException, JSONException, ParsingError {
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

    private void checkExistInMapAndInsert(String bracketJSONValue, Pair<String, String> directionAndPairBracket) throws ParsingError {
        if(bracketsMap.containsValue(Pair.create(directionAndPairBracket.first, directionAndPairBracket.second))){
            throw new ParsingError("Bracket " + directionAndPairBracket.second + " already exists in config file. ", this.fileName);
        }
        if(bracketsMap.containsKey(bracketJSONValue)){
            Pair<String, String> bracketPairValue = bracketsMap.get(bracketJSONValue);
            if(!bracketPairValue.first.equals(directionAndPairBracket.first)){
                bracketsMap.put(bracketJSONValue, Pair.create(SettingNames.bothBracketJSONKey, bracketJSONValue));
                return;
            }
        }
        bracketsMap.put(bracketJSONValue, directionAndPairBracket);
    }

    private void createNewJsonPairByPos(int i) throws ParsingError {
        String leftBracketJSONValue = bracketsJSONArray.getJSONObject(i).getString(SettingNames.leftBracketJSONKey);
        String rightBracketJSONValue = bracketsJSONArray.getJSONObject(i).getString(SettingNames.rightBracketJSONKey);

        checkExistInMapAndInsert(leftBracketJSONValue, Pair.create(SettingNames.leftBracketJSONKey, rightBracketJSONValue));
        checkExistInMapAndInsert(rightBracketJSONValue, Pair.create(SettingNames.rightBracketJSONKey, leftBracketJSONValue));
    }
}
