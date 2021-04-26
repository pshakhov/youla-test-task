package ru.youla.pages;

import java.io.File;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class MainPage {
    public WebDriver driver;

    String HOME_PAGE = "https://yandex.ru/";

    private Wait<WebDriver> wait;

    String testResourcesBase = "src/test/resources/";

    public MainPage(final WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//a[@data-id = 'images']") // TODO настроить переход в той же вкладке, если это возможно
    WebElement picturesButton;

    @FindBy(xpath = ".//button[@aria-label='Поиск по изображению']")
    WebElement searchByImageButton;

    @FindBy(css = ".cbir-panel__file-input")
    WebElement uploadButton;

    public void openAndWaitMainPage() {
        driver.navigate().to(HOME_PAGE);
        wait = new WebDriverWait(driver, 30,
                500);
        wait.until(ExpectedConditions.
                titleContains("Яндекс"));
    }

    public void goToImages() {
        picturesButton.click();
        Set<String> allHandles = driver.getWindowHandles();
        allHandles.remove(allHandles.iterator().next());
        driver.switchTo().window(allHandles.iterator().next());
    }

    public void clickSearchByImage() {
        Actions actions = new Actions(driver);
        actions.moveToElement(searchByImageButton).perform();
        searchByImageButton.click();
    }

    public void uploadImage(String path) {
        File file = new File(testResourcesBase + path);
        uploadButton.sendKeys(file.getAbsolutePath());
    }

    public void checkResults(String result) {
        String parent = "//*[contains(text(),'Кажется, на изображении')]";
        String child = "/..//*[contains(text(),'%s')]";
        String xpath = parent + child;
        String resultPath = String.format(xpath, result);
        String text = driver.findElement(By.xpath(resultPath)).getText();
        log.info(text);
        assertThat(text).contains(result);
    }
}
