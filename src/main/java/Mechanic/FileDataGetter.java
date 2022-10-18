package Mechanic;

import java.io.*;

public class FileDataGetter {
    private final BufferedReader bufferedReader;

    public FileDataGetter(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File " + file.getName() + " was not found.");
        }
        FileReader fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
    }

    public String getNextLine() throws IOException {
        String fileLine = "";
        if (!hasNextLine()) {
            bufferedReader.close();
            return fileLine;
        }
        return bufferedReader.readLine();
    }

    public boolean hasNextLine() throws IOException {
        return bufferedReader.ready();
    }
}
