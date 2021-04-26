package ru.youla.helper.driver;

import java.net.URL;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

@Slf4j
public class DriverFactory {
    WebDriver driver;

    public WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        if (System.getenv("CI_JOB_ID") != null) {
            try {
                String urlEnv = System.getenv("selenium_chrome_url");
                String url = "http://selenium__standalone-chrome:4444/wd/hub/";
                if (urlEnv != null) url = urlEnv;
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                driver = new RemoteWebDriver(new URL(url), capabilities);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }
        log.info("Init driver - {}", this.driver);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return driver;
    }

    public WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        if (System.getenv("CI_JOB_ID") != null) {
            try {
                String urlEnv = System.getenv("selenium_firefox_url");
                String url = "http://selenium__standalone-firefox:4444/wd/hub/";
                if (urlEnv != null) url = urlEnv;
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
                driver = new RemoteWebDriver(new URL(url), capabilities);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(options);
        }
        log.info("Init driver - {}", this.driver);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return driver;
    }
}
