package tests;

import api.AuthApi;
import data.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import java.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.LoginPage;
import pages.RegistrationPage;
import static org.junit.Assert.assertTrue;
import user.UserGenerator;
import utils.BrowserFactory;

@RunWith(JUnit4.class)
public class LoginTest {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String REGISTER_URL = BASE_URL + "register";
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {

        driver = BrowserFactory.getDriver(); // Браузер берется из config.properties
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        user = UserGenerator.getValidUser();

        var response = AuthApi.registerUser(user); // Регистрация через API вместо UI
        accessToken = response.then().extract().path("accessToken");

    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт'")
    @Description("Тест проверяет вход через кнопку 'Войти в аккаунт' на главной странице")
    public void loginViaMainPageButtonTest() {
        driver.get(REGISTER_URL);
        loginPage.clickLoginLink();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.waitForUrlToBeBase(); // Используем метод Page Object
        assertTrue("Главная страница не отображается после входа", mainPage.isBunsActiveByDefault());
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Тест проверяет вход через кнопку 'Личный кабинет' в хедере")
    public void loginViaPersonalAccountButtonTest() {
        mainPage.open();
        mainPage.clickPersonalAccountButton();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.waitForUrlToBeBase();
        assertTrue("Главная страница не отображается после входа", mainPage.isBunsActiveByDefault());
    }

    @Test
    @DisplayName("Вход через ссылку в форме регистрации")
    @Description("Тест проверяет вход через ссылку 'Войти' на странице регистрации")
    public void loginViaRegistrationFormLinkTest() {
        driver.get(REGISTER_URL);
        loginPage.waitForUrlContainsRegister();
        loginPage.clickLoginLink();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.waitForUrlToBeBase();
        assertTrue("Главная страница не отображается после входа", mainPage.isBunsActiveByDefault());
    }

    @Test
    @DisplayName("Вход через ссылку в форме восстановления пароля")
    @Description("Тест проверяет вход через ссылку 'Войти' на странице восстановления пароля")
    public void loginViaPasswordRecoveryFormLinkTest() {
        loginPage.open();
        loginPage.clickRestorePasswordLink();
        driver.navigate().back();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.waitForUrlToBeBase();
        assertTrue("Главная страница не отображается после входа", mainPage.isBunsActiveByDefault());
    }

    @After
    public void tearDown() {

        try {
            if (accessToken != null) {
                AuthApi.deleteUser(accessToken);
            }
        } catch (Exception e) {
            System.out.println("Ошибка очистки: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}