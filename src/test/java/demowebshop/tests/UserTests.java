package demowebshop.tests;


import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import demowebshop.pages.UserPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

@DisplayName("UI User tests")
public class UserTests extends TestBase {
    private final String authCookieName = "NOPCOMMERCE.AUTH";
    private final String verificationTokenName = "__RequestVerificationToken";
    private final String verificationTokenInputValue = "10QcxkN4-Gk5PEeZlTrtVTuN7xtnRi_RY4ssN4Kd1kn--wsjFIdx3MtZG3cs6EsIYcWSCd3dIikpNcaAeVkJyMaRx50q_u84GfmcWQFqcqw1";
    private final String verificationTokenHeaderValue = "at9THwDl4iOCtU40qL9aL87W4x6vnP7C7vDFXJ6VVruf0QlYjJGo4vKOOZ37as2KBsbYUCMAENIXnFqvW6QHp-85oL4JZadn5TQu5MPCDv41;";
    Faker faker = new Faker();
    UserPage userPage = new UserPage();
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String anotherEmail;
    private String anotherName;
    private String anotherLastName;


    @BeforeEach
    void setUpUser() {
        password = faker.numerify("##########");
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = faker.internet().emailAddress();
        anotherEmail = faker.internet().emailAddress();
        anotherName = faker.name().firstName();
        anotherLastName = faker.name().lastName();
    }

    @AfterEach
    public void clearBrowser() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    @Feature("Регистрация пользователя")
    @Story("Регистрация пользователя с цифровым паролем ")
    @Owner("Aleksei Partolin")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://demowebshop.tricentis.com/register")
    @DisplayName("Регистрация пользователя UI тест")
    void registerNewUserTest() {

        step("Открываем страницу регистрации пользователя", () -> {
            open("/register");
        });

        step("Заполняем обязательные поля формы регистрации", () -> {
            userPage
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setPassword(password);
        });

        step("Отправляем заполненную форму на сервер", () -> {
            userPage.sendRegistrationForm();
        });

        step("Проверяем, что пользователь зарегистрирован", () -> {
            userPage.checkUserCreated(email);
        });
    }

    @Test
    @Feature("Регистрация пользователя")
    @Story("Регистрация пользователя с цифровым паролем ")
    @Owner("Aleksei Partolin")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://demowebshop.tricentis.com/register")
    @DisplayName("Регистрация пользователя API тест")
    void registerNewUserAPITest() {
        String authCookieValue = step("Регистрируем пользователя через API ", () -> given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam(verificationTokenName, verificationTokenInputValue)
                .formParam("FirstName", firstName)
                .formParam("LastName", lastName)
                .formParam("Email", email)
                .formParam("Password", password)
                .formParam("ConfirmPassword", password)
                .cookie(verificationTokenName, verificationTokenHeaderValue)
                .post("/register")
                .then()
                .extract().cookie(authCookieName));


        step("Подкладываем куки созданного пользователя", () -> {
            open("/Themes/DefaultClean/Content/images/logo.png");
            Cookie authCookie = new Cookie(authCookieName, authCookieValue);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });

        step("Открываем сайт", () -> {
            open("/registerresult/1");
        });

        step("Проверяем, что пользователь зарегистрирован", () -> {
            userPage.checkUserCreated(email);
        });
    }

    @Test
    @Feature("Редактирование профиля")
    @Story("Зарегистрированный пользователь может редактировать профиль")
    @Owner("Aleksei Partolin")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://demowebshop.tricentis.com/info")
    @DisplayName("Редактирование профиля UI тест")
    void userCanModifyProfileTest() {
        String anotherEmail = faker.internet().emailAddress();
        String anotherName = faker.name().firstName();
        String anotherLastName = faker.name().lastName();

        step("Открываем страницу регистрации пользователя", () -> {
            open("/register");
        });

        step("Заполняем обязательные поля формы регистрации", () -> {
            userPage
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setPassword(password);
        });

        step("Отправляем заполненную форму на сервер", () -> {
            userPage.sendRegistrationForm();
        });

        step("Открываем профиль", () -> {
            userPage.openProfile();
        });

        step("Заполняем профиль новыми занчениями", () -> {
            userPage
                    .setFirstName(anotherName)
                    .setLastName(anotherLastName)
                    .setEmail(anotherEmail);
        });

        step("Сохраняем профиль", () -> {
            userPage.saveProfile();
        });

        step("Повторно открываем профиль", () -> {
            userPage.openProfile();
        });

        step("Проверяем, что изменения сохранились", () -> {
            userPage.checkChangesSaved(anotherEmail, anotherName, anotherLastName);
        });
    }

    @Test
    @Feature("Редактирование профиля")
    @Story("Зарегистрированный пользователь может редактировать профиль")
    @Owner("Aleksei Partolin")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://demowebshop.tricentis.com/info")
    @DisplayName("Редактирование профиля API тест")
    void userCanModifyProfileAPITest() {
        String authCookieValue = step("Регистрируем пользователя через API ", () -> given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam(verificationTokenName, verificationTokenInputValue)
                .formParam("FirstName", firstName)
                .formParam("LastName", lastName)
                .formParam("Email", email)
                .formParam("Password", password)
                .formParam("ConfirmPassword", password)
                .cookie(verificationTokenName, verificationTokenHeaderValue)
                .post("/register")
                .then()
                .extract().cookie(authCookieName));

        step("Подкладываем куки созданного пользователя", () -> {
            open("/Themes/DefaultClean/Content/images/logo.png");
            Cookie authCookie = new Cookie(authCookieName, authCookieValue);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });

        step("Открываем профиль", () -> {
            open("/customer/info");
        });

        step("Заполняем профиль новыми занчениями", () -> {
            userPage
                    .setFirstName(anotherName)
                    .setLastName(anotherLastName)
                    .setEmail(anotherEmail);
        });

        step("Сохраняем профиль", () -> {
            userPage.saveProfile();
        });

        step("Повторно открываем профиль", () -> {
            userPage.openProfile();
        });

        step("Проверяем, что изменения сохранились", () -> {
            userPage.checkChangesSaved(anotherEmail, anotherName, anotherLastName);
        });
    }

}
