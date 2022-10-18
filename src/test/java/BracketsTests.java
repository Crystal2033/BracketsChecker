import Exceptions.BracketPositionError;
import Exceptions.ParsingError;
import Mechanic.BracketsChecker;
import Mechanic.ConfigParser;
import Mechanic.FileDataGetter;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class BracketsTests {
    private final String PATH = "D:\\Paul\\Programming\\Java\\RPKS\\Labs\\BracketsChecker\\TestCases\\";
    @Nested
    @DisplayName("ConfigParser ClassTest")
    class TestingConfigParser{

        @ParameterizedTest
        @DisplayName("ConstructorTestNotExist")
        @ValueSource(strings = {PATH+"text3.txt", PATH+"configTesting1.txt", PATH+"tex.txt", PATH+"text7.txt"})
        void setConfigFileTestNotExist(String fileName) {
            Assertions.assertThrows(IOException.class, ()-> new FileDataGetter(fileName));
        }

        @ParameterizedTest
        @DisplayName("ConstructorTestExist")
        @ValueSource(strings = {PATH+"confTestBad0.txt", PATH+"confTest2.txt", PATH+"confTest3.txt", PATH+"confTest1.txt"})
        void setConfigFileTestExist(String fileName) {
            Assertions.assertDoesNotThrow(()-> new FileDataGetter(fileName));
        }

        @Test
        @DisplayName("Testing JSON exception")
        void getBracketsMapBadJSON() throws IOException{
            ConfigParser configParser1 = new ConfigParser(PATH+"confTestBad0.txt");
            Assertions.assertThrowsExactly(JSONException.class, configParser1::getBracketsMap);

            ConfigParser configParser2 = new ConfigParser(PATH+"confTestBad1.txt");
            Assertions.assertThrowsExactly(JSONException.class, configParser2::getBracketsMap);

            ConfigParser configParser3 = new ConfigParser(PATH+"confTestBad2.txt");
            Assertions.assertThrowsExactly(JSONException.class, configParser3::getBracketsMap);

            ConfigParser configParser4 = new ConfigParser(PATH+"confTestBad3.txt");
            Assertions.assertThrowsExactly(ParsingError.class, configParser4::getBracketsMap);
        }

        @Test
        @DisplayName("Testing Good JSON")
        void getBracketsMapGoodJSON() throws IOException{
            ConfigParser configParser1 = new ConfigParser(PATH+"confTest1.txt");
            Assertions.assertDoesNotThrow(configParser1::getBracketsMap);

            ConfigParser configParser2 = new ConfigParser(PATH+"confTest2.txt");
            Assertions.assertDoesNotThrow(configParser2::getBracketsMap);

            ConfigParser configParser3 = new ConfigParser(PATH+"confTest3.txt");
            Assertions.assertDoesNotThrow(configParser3::getBracketsMap);
        }

    }

    @Nested
    @DisplayName("FileDataGetter ClassTest")
    class TestingFileDataGetter{
        @ParameterizedTest
        @DisplayName("ConstructorTestNotExist")
        @ValueSource(strings = {PATH+"text3.txt", PATH+"configTesting1.txt", PATH+"tex.txt", PATH+"text7.txt"})
        void constructorTestNotExist(String fileName) {
            Assertions.assertThrows(IOException.class, ()-> new FileDataGetter(fileName));
        }

        @ParameterizedTest
        @DisplayName("ConstructorTestExist")
        @ValueSource(strings = {PATH+"test1.txt", PATH+"test2.txt", PATH+"test4Bad1.txt", PATH+"test5Bad2.txt",
                PATH+"confTestBad0.txt", PATH+"confTest2.txt", PATH+"confTest3.txt", PATH+"confTest1.txt"})
        void constructorTestExist(String fileName) {
            Assertions.assertDoesNotThrow(()-> new FileDataGetter(fileName));
        }

        @Test
        @DisplayName("getNextLineTest")
        void getNextLineTest() throws IOException {
            FileDataGetter fileDataGetter = new FileDataGetter(PATH+"test2.txt");
            Assertions.assertEquals("[some(one{1!|value|2?}jar)none]", fileDataGetter.getNextLine());

            FileDataGetter fileDataGetter2 = new FileDataGetter(PATH+"test3.txt");
            Assertions.assertEquals("[some(one{1!|value|2?}jar))none]", fileDataGetter2.getNextLine());

            FileDataGetter fileDataGetter3 = new FileDataGetter(PATH+"test4Bad1.txt");
            Assertions.assertEquals("[some(one{1!|value|2?jar))none]", fileDataGetter3.getNextLine());

            FileDataGetter fileDataGetter4 = new FileDataGetter(PATH+"confTestBad3.txt");
            Assertions.assertEquals("{\"bracket\":[{\"left\":\"[\",\"right\":\"]\"},{\"left\":\"{\",\"right\":\"}\"}, {\"left\":\"{\",\"right\":\"}\"}]}", fileDataGetter4.getNextLine());

            FileDataGetter fileDataGetter5 = new FileDataGetter(PATH+"confTest2.txt");
            Assertions.assertEquals("{\"bracket\":[{\"left\":\"[\",\"right\":\"]\"},{\"left\":\"{\",\"right\":\"}\"}, {\"left\":\"(\",\"right\":\")\"}, {\"left\":\"|\",\"right\":\"|\"}]}", fileDataGetter5.getNextLine());
        }

        @Test
        @DisplayName("hasNextLineTest")
        void hasNextLineTest() throws IOException {
            FileDataGetter fileDataGetter = new FileDataGetter(PATH+"test2.txt");
            Assertions.assertTrue(fileDataGetter.hasNextLine());

            FileDataGetter fileDataGetter2 = new FileDataGetter(PATH+"test3.txt");
            Assertions.assertTrue(fileDataGetter2.hasNextLine());

            FileDataGetter fileDataGetter3 = new FileDataGetter(PATH+"test4Bad1.txt");
            Assertions.assertTrue(fileDataGetter3.hasNextLine());

            FileDataGetter fileDataGetter4 = new FileDataGetter(PATH+"EmptyFile.txt");
            Assertions.assertFalse(fileDataGetter4.hasNextLine());

        }
    }

    @Nested
    @DisplayName("BracketsChecker")
    class BracketsCheckerTest{

        @ParameterizedTest
        @DisplayName("setConfigNotExceptions")
        @ValueSource(strings = {PATH+"text3.txt", PATH+"configTesting1.txt", PATH+"tex.txt", PATH+"text7.txt"})
        void setConfigFileTestNotExist(String fileName) {
            Assertions.assertThrows(IOException.class, ()-> new FileDataGetter(fileName));
        }

        @Test
        @DisplayName("setConfigHasExceptions")
        void setConfigFileTestException() {
            BracketsChecker bracketsChecker0 = new BracketsChecker();
            Assertions.assertThrows(JSONException.class, ()->bracketsChecker0.setConfigFile(PATH+"confTestBad0.txt"));

            BracketsChecker bracketsChecker1 = new BracketsChecker();
            Assertions.assertThrows(JSONException.class, ()->bracketsChecker1.setConfigFile(PATH+"confTestBad1.txt"));

            BracketsChecker bracketsChecker2 = new BracketsChecker();
            Assertions.assertThrows(JSONException.class, ()->bracketsChecker2.setConfigFile(PATH+"confTestBad2.txt"));

            BracketsChecker bracketsChecker3 = new BracketsChecker();
            Assertions.assertThrows(ParsingError.class, ()->bracketsChecker3.setConfigFile(PATH+"confTestBad3.txt"));
        }

        @Test
        @DisplayName("setConfigHasExceptions")
        void setConfigFileTestNOException() {
            BracketsChecker bracketsChecker0 = new BracketsChecker();
            Assertions.assertDoesNotThrow(()->bracketsChecker0.setConfigFile(PATH+"confTest1.txt"));

            BracketsChecker bracketsChecker1 = new BracketsChecker();
            Assertions.assertDoesNotThrow(()->bracketsChecker1.setConfigFile(PATH+"confTest2.txt"));

            BracketsChecker bracketsChecker2 = new BracketsChecker();
            Assertions.assertDoesNotThrow(()->bracketsChecker2.setConfigFile(PATH+"confTest3.txt"));

        }


        @Test
        @DisplayName("Good brackets")
        void goodBrackets() throws ParsingError, IOException {
            BracketsChecker bracketsChecker0 = new BracketsChecker();
            bracketsChecker0.setConfigFile(PATH+"confTest1.txt");
            Assertions.assertDoesNotThrow(()-> bracketsChecker0.checkBracketsCorrectOrException(PATH+"test1.txt"));

            BracketsChecker bracketsChecker1 = new BracketsChecker();
            bracketsChecker1.setConfigFile(PATH+"confTest2.txt");
            Assertions.assertDoesNotThrow(()-> bracketsChecker1.checkBracketsCorrectOrException(PATH+"test2.txt"));

            BracketsChecker bracketsChecker2 = new BracketsChecker();
            bracketsChecker2.setConfigFile(PATH+"confTest3.txt");
            Assertions.assertDoesNotThrow(()-> bracketsChecker2.checkBracketsCorrectOrException(PATH+"test3.txt"));


        }

        @Test
        @DisplayName("Bad Brackets")
        void badBrackets() throws ParsingError, IOException {
            BracketsChecker bracketsChecker0 = new BracketsChecker();
            bracketsChecker0.setConfigFile(PATH+"confTest1.txt");
            Assertions.assertThrows(BracketPositionError.class,()-> bracketsChecker0.checkBracketsCorrectOrException(PATH+"test4Bad1.txt"));

            BracketsChecker bracketsChecker1 = new BracketsChecker();
            bracketsChecker1.setConfigFile(PATH+"confTest2.txt");
            Assertions.assertThrows(BracketPositionError.class,()-> bracketsChecker1.checkBracketsCorrectOrException(PATH+"test5Bad2.txt"));

        }

    }

}
