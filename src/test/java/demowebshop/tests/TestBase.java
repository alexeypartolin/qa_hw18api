package demowebshop.tests;

import com.codeborne.selenide.Config;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import demowebshop.config.WebDriverProvider;
import demowebshop.utils.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.restassured.RestAssured.baseURI;

public class TestBase {

    @BeforeAll
    static void config() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        WebDriverProvider config = new WebDriverProvider();
        // hardcore
        //config.setConfiguration("remote"); // remote
        config.setConfiguration("local"); // local

        // Выбор конфига из system property при запуске тестов
//        if (System.getProperty("environment").equals("local")) {
//            config.setConfiguration("local");
//        } else if (System.getProperty("environment").equals("remote")) {
//            config.setConfiguration("remote");
//        }

//        String envValue = System.getProperty("env");
//
//        if (envValue == "local") {
//            System.setProperty("environment", "local");
//        } else if (envValue == "remote") {
//            System.setProperty("environment", "remote");
//        }

        // тут проблема - не пойму как правильно передать данные local или remote при запуске теста, получается только хардкод сверху
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
