package configurator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Base {

    protected WebDriver driver;

    public void initializeDriver() {
        driver = new ChromeDriver();
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
