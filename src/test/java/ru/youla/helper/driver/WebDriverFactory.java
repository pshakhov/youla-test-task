package ru.youla.helper.driver;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {
    public static WebDriver createWebDriver() {
        String webdriver = System.getProperty("browser", "chrome");
        switch(webdriver) {
            case "firefox":
                return DriverConfiguration.createFirefoxDriver();
            case "chrome":
                return DriverConfiguration.createChromeDriver();
            default:
                throw new RuntimeException("Unsupported webdriver: " + webdriver);
        }
    }
}
