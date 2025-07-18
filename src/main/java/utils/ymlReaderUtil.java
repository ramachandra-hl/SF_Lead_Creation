package utils;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.util.Map;

public class ymlReaderUtil {
    public static Map<String, Object> readCredentials(String env, String credFilePath) {
        Yaml yaml = new Yaml();
        try (FileInputStream inputStream = new FileInputStream(credFilePath)) {
            Map<String, Object> credYaml = yaml.load(inputStream);
            Object loginCredentialObj = credYaml.get("login_Credential");
            if (!(loginCredentialObj instanceof Map<?, ?> loginCredentialMap)) {
                throw new RuntimeException("login_Credential section is not a map");
            }
            Object envObj = loginCredentialMap.get(env);
            if (envObj instanceof Map<?, ?> envMap) {
                Map<String, Object> result = new java.util.HashMap<>();
                for (Map.Entry<?, ?> entry : envMap.entrySet()) {
                    result.put(String.valueOf(entry.getKey()), entry.getValue());
                }
                return result;
            } else {
                throw new RuntimeException("Environment credentials are not a map");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read credentials from YAML", e);
        }
    }
}
