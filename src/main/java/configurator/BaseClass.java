package configurator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;


import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseClass extends ApiService {

    public static final Logger log = LogManager.getLogger(BaseClass.class);
    public static HashMap<String, Object> testData = new HashMap<>();

    public static String customerType;
    static {
        addToTestDataFromYml("Configuration/standardConfigurations.yml", testData);
        customerType = (String) testData.get("customerType");
        loadCustomerConfig(customerType);
        loadCityConfig();
    }

    private static void loadCustomerConfig(String customerType) {
        String configFile = "configuration/credential.yml";
            addToTestDataFromYml(configFile, testData);

    }

    private static void loadCityConfig() {
            addToTestDataFromProperties("configuration/cityData/Bengaluru.properties", testData);
    }


    public static void addToTestDataFromProperties(String fileName, Map<String, Object> testData) {
        Properties prop = new Properties();
        try {
            FileReader reader = new FileReader(fileName);
            if (reader.ready()) {
                prop.load(reader);
                for (String name : prop.stringPropertyNames()) {
                    testData.put(name, prop.getProperty(name));
                }
            } else {
                log.warn("Test Data File not present: {}", fileName);
            }
        } catch (IOException e) {
            log.error("Failed to read {}: {}", fileName, e.getMessage(), e);
        }
    }

    public static void addToTestDataFromYml(String fileName, Map<String, Object> testData) {
        Yaml yaml = new Yaml();
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            Map<String, Object> yamlData = yaml.load(inputStream);
            if (yamlData != null) {
                testData.putAll(yamlData);
            }
        } catch (IOException e) {
            log.error("Failed to read {}: {}", fileName, e.getMessage(), e);
        }
    }
}
