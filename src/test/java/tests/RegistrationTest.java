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
import pages.RegistrationPage;
import static org.junit.Assert.assertTrue;
import user.UserGenerator;
import utils.BrowserFactory;

@RunWith(JUnit4.class)
public class RegistrationTest {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String REGISTER_URL = BASE_URL + "register";
    private static final String LOGIN_URL = BASE_URL + "login";

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {

        driver = BrowserFactory.getDriver();

        registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        user = UserGenerator.getValidUser();
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Тест проверяет успешную регистрацию пользователя с валидными данными")
    public void successfulRegistrationTest() {
        registrationPage.register(user.getName(), user.getEmail(), user.getPassword());
        registrationPage.waitForRedirectToLogin();
        assertTrue("Ожидался редирект на страницу входа", driver.getCurrentUrl().contains("login"));
    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем")
    @Description("Тест проверяет отображение ошибки при регистрации с паролем короче 6 символов")
    public void registrationWithShortPasswordTest() {
        User user = UserGenerator.getUserWithShortPassword();
        registrationPage.register(user.getName(), user.getEmail(), user.getPassword());
        assertTrue("Ошибка о коротком пароле не отображается",
                registrationPage.isPasswordErrorDisplayed());
    }

    @After
    public void tearDown() {
        try {
            if (user != null && user.getEmail() != null && user.getPassword() != null) {
                accessToken = AuthApi.loginUser(user)
                        .then()
                        .extract()
                        .path("accessToken");
            }
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