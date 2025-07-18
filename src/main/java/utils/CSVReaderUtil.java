package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVReaderUtil {

    public static Iterator<Object[]> readCSVData(String filePath) {
        List<Object[]> testData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean skipHeader = true;
            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                testData.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return testData.iterator();
    }
}
