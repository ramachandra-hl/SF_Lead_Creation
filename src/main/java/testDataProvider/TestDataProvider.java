package testDataProvider;

import org.testng.annotations.DataProvider;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class TestDataProvider {

    @DataProvider(name = "leadDataProvider")
    public static Object[][] provideLeadData() {
        String csvFile = "testData/Lead_Data.csv";
        List<Map<String, String>> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String[] headers = br.readLine().split(",");
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                Map<String, String> dataMap = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    dataMap.put(headers[i], values[i]);
                }
                dataList.add(dataMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Object[][] result = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            result[i][0] = dataList.get(i);
        }

        return result;
    }
}
