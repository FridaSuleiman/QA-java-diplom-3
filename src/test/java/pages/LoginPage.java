package pages;

import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;



public class LoginPage {
    private static final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/login";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailField = By.xpath(".//input[@name='name']");
    private final By passwordField = By.xpath(".//input[@name='Пароль']");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By registerLink = By.xpath(".//a[text()='Зарегистрироваться']");
    private final By restorePasswordLink = By.xpath(".//a[text()='Восстановить пароль']");
    private final By loginLink = By.xpath(".//a[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Step("Открытие страницы входа")
    public void open() {
        driver.get(LOGIN_URL);
        waitForPageLoad();
    }
    @Step("Ожидание загрузки страницы входа")
    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }
    @Step("Вход с вводом email и пароля")
    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
    @Step("Клик по ссылке 'Восстановить пароль'")
    public void clickRestorePasswordLink() {
        driver.findElement(restorePasswordLink).click();
    }
    @Step("Клик по ссылке 'Войти'")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    @Step("Ожидание перехода на страницу регистрации")
    public void waitForUrlContainsRegister() {
        wait.until(ExpectedConditions.urlContains("register"));
    }

}