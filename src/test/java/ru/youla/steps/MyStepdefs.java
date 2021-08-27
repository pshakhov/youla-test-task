package ru.youla.steps;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.cucumber.junit.Cucumber;
import io.qameta.allure.Allure;
import java.io.File;
import java.util.Arrays;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.qameta.allure.Allure.addAttachment;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.youla.helper.driver.WebDriverFactory.createWebDriver;

@Slf4j
public class MyStepdefs {
    WebDriver driver = createWebDriver();

    @Дано("Открыть главную Яндекса")
    public void открытьГлавнуюЯндекса() {
        driver.navigate().to("https://yandex.ru/");
        Wait<WebDriver> wait = new WebDriverWait(driver, 30,
                500);
        wait.until(ExpectedConditions.
                titleContains("Яндекс"));
    }

    @И("Перейти в блок Картинки")
    public void перейтиВБлокКартинки() {
        WebElement picturesButton = driver.findElement(By.xpath("//a[@data-id = 'images']"));
        picturesButton.click();
        Set<String> allHandles = driver.getWindowHandles();
        allHandles.remove(allHandles.iterator().next());
        driver.switchTo().window(allHandles.iterator().next());
    }

    @И("Нажать на поиск по картинке")
    public void нажатьНаПоискПоКартинке() {
        Actions actions = new Actions(driver);
        WebElement searchByImageButton = driver.findElement(By.xpath(".//button[@aria-label='Поиск по изображению']"));
        actions.moveToElement(searchByImageButton).perform();
        searchByImageButton.click();
    }

    @Когда("Загрузить картинку с предусловия {string}")
    public void загрузитьКартинкуСПредусловия(String image) {
        String testResourcesBase = "src/test/resources/";
        File file = new File(testResourcesBase + image);
        WebElement uploadButton = driver.findElement(By.xpath(".//*[@class=\"CbirCore-FileInput\"]"));

        uploadButton.sendKeys(file.getAbsolutePath());
    }

    @Тогда("Убедиться, что в блоке Кажется, на картинке есть в одном из буллитов отобразилось слово {string}")
    public void убедитьсяЧтоВБлокеКажетсяНаКартинкеЕстьВОдномИзБуллитовОтобразилосьСлово(String result) {
        String parent = "//*[contains(text(),'Кажется, на изображении')]";
        String child = "/..//*[contains(text(),'%s')]";
        String xpath = parent + child;
        String resultPath = String.format(xpath, result);
        WebElement results = driver.findElement(By.xpath(resultPath));
        String text = results.getText();
        log.info(text);
        assertThat(text).contains(result);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        }
        driver.quit();
    }
}
