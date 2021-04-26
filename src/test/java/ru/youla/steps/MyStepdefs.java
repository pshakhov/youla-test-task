package ru.youla.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import ru.youla.configuration.ChromeDriverConfiguration;
import ru.youla.pages.MainPage;

public class MyStepdefs implements ChromeDriverConfiguration {
    private static MainPage mainPage;

    @Дано("Открыть главную Яндекса")
    public void открытьГлавнуюЯндекса() {
        mainPage = new MainPage(driver);
        mainPage.openAndWaitMainPage();
    }

    @И("Перейти в блок Картинки")
    public void перейтиВБлокКартинки() {
        mainPage.goToImages();
    }

    @И("Нажать на поиск по картинке")
    public void нажатьНаПоискПоКартинке() {
        mainPage.clickSearchByImage();
    }

    @Когда("Загрузить картинку с предусловия {string}")
    public void загрузитьКартинкуСПредусловия(String image) {
        mainPage.uploadImage(image);
    }

    @Тогда("Убедиться, что в блоке Кажется, на картинке есть в одном из буллитов отобразилось слово {string}")
    public void убедитьсяЧтоВБлокеКажетсяНаКартинкеЕстьВОдномИзБуллитовОтобразилосьСлово(String text) {
        mainPage.checkResults(text);
    }
}
