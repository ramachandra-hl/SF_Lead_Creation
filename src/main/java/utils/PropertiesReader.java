package utils;

import configurator.BaseClass;

import java.util.Map;

public class PropertiesReader extends BaseClass {
    public static String baseURl = null;
    public static String ScProUrl = null;
    public static String synapseUrl = null;
    public static String roasterBaseUrl = null;
    public static String logged_in_user_id = null;
    public static String appointment_venue = null;
    public static String rosterCookies = null;
    public static String dp_email = null;
    public static String dp_password = null;
    public static String finance_dp = null;
    public static String finance_Password = null;

    // Test Data Variables
    public static String projectID = (String) testData.getOrDefault("projectID", "");
    public static String senderEmail = (String) testData.getOrDefault("senderEmail", "");
    public static String gmailPassword = (String) testData.getOrDefault("gmail.app.password", "");
  public static String environment = (String) testData.getOrDefault("environment", "preProd");
    public static String customerGmailStartingPrefix = (String) testData.getOrDefault("customerGmailStartingPrefix", "");
    public static Map<String, Object> environments = (Map<String, Object>) testData.get("projectEnvironments");
    public static Map<String, Object> login_Credential = (Map<String, Object>) testData.get("login_Credential");

    private static void initializeEnvironment(String env) {
        Map<String, String> envData = getSubMap(environments, env);
        Map<String, String> loginData = getSubMap(login_Credential, env);

        if (envData == null || loginData == null) {
            throw new IllegalStateException("Environment or login credentials not found for: " + env);
        }
        ScProUrl=envData.getOrDefault("ScProUrl","");
        baseURl = envData.getOrDefault("BaseURL", "");
        synapseUrl = envData.getOrDefault("SynapseUrl", "");
        roasterBaseUrl = envData.getOrDefault("RoasterBaseUrl", "");
        rosterCookies = envData.getOrDefault("cookies", "");
        finance_dp=loginData.getOrDefault("finance_email", "");
        finance_Password = loginData.getOrDefault("finance_password", "");
        logged_in_user_id = loginData.getOrDefault("logged_in_user_id", "");
        dp_email = loginData.getOrDefault("dp_email", "");
        dp_password = loginData.getOrDefault("dp_password", "");
        appointment_venue = String.valueOf(testData.getOrDefault(env + "Appointment_venue", ""));
    }

    static {
        if (environment != null && !environment.isEmpty()) {
            initializeEnvironment(environment);
        } else {
            throw new IllegalStateException("Environment variable is not set.");
        }
    }

    private static Map<String, String> getSubMap(Map<String, Object> parentMap, String key) {
        if (parentMap == null || key == null) return null;
        for (String mapKey : parentMap.keySet()) {
            if (mapKey.equalsIgnoreCase(key)) {
                Object value = parentMap.get(mapKey);
                if (value instanceof Map) {
                    return (Map<String, String>) value;
                }
            }
        }
        return null;
    }
}