package ru.youla.configuration;

import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import ru.youla.helper.driver.DriverFactory;

public interface ChromeDriverConfiguration {
    WebDriver driver = new DriverFactory().createChromeDriver();

    @AfterEach
    static void teardown() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.close();
            driver.quit();
        }
    }
}
