package pages;

import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;


public class RegistrationPage {
    private static final String REGISTER_URL = "https://stellarburgers.nomoreparties.site/register";
    private static final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/login";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameField = By.xpath(".//fieldset[contains(.,'Имя')]//input");
    private final By emailField = By.xpath(".//fieldset[contains(.,'Email')]//input");
    private final By passwordField = By.xpath(".//fieldset[contains(.,'Пароль')]//input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath(".//a[text()='Войти']");
    private final By passwordError = By.xpath(".//p[text()='Некорректный пароль']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    @Step("Открытие страницы регистрации")
    public void open() {
        driver.get(REGISTER_URL);
        waitForPageLoad();
    }
    @Step("Ожидание загрузки страницы регистрации")
    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(registerButton));
    }
    @Step("Регистрация пользователя c вводом Имени, Email, Пароля")
    public void register(String name, String email, String password) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(registerButton).click();
    }
    @Step("Проверка отображения ошибки Некорректный пароль")
    public boolean isPasswordErrorDisplayed() {
        return driver.findElement(passwordError).isDisplayed();
    }
    @Step("Клик по ссылке Войти")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    @Step("Ожидание перехода на страницу входа после регистрации")
    public void waitForRedirectToLogin() {
        wait.until(ExpectedConditions.urlToBe(LOGIN_URL));
    }
}