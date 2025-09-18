package pages;

import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;


public class MainPage {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String ACTIVE_TAB_CLASS = "current";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By constructorTitle = By.xpath("//h1[text()='Соберите бургер']");
    private final By bunsTab = By.xpath(".//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath(".//span[text()='Начинки']/parent::div");
    private final By loginAccountButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath(".//p[text()='Личный Кабинет']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Step("Открытие главной страницы")
    public void open() {
        driver.get(BASE_URL);
        waitForPageLoad();
    }
    @Step("Ожидание загрузки главной страницы")
    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(constructorTitle));
    }
    @Step("Клик по разделу Булки")
    public void clickBunsSection() {
        driver.findElement(bunsTab).click();
        waitBunsLoad();
    }
    @Step("Клик по разделу Соусы")
    public void clickSaucesSection() {
        driver.findElement(saucesTab).click();
        waitSaucesLoad();
    }
    @Step("Клик по разделу Начинки")
    public void clickFillingsSection() {
        driver.findElement(fillingsTab).click();
        waitFillingsLoad();
    }
    @Step("Ожидание загрузки раздела Булки")
    public void waitBunsLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(bunsTab, "class", ACTIVE_TAB_CLASS));
    }
    @Step("Ожидание загрузки раздела Соусы")
    public void waitSaucesLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(saucesTab, "class", ACTIVE_TAB_CLASS));
    }
    @Step("Ожидание загрузки раздела Начинки")
    public void waitFillingsLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(fillingsTab, "class", ACTIVE_TAB_CLASS));
    }

    @Step("Проверка активности раздела Булки")
    public boolean isBunsActive() {
        return driver.findElement(bunsTab).getAttribute("class").contains(ACTIVE_TAB_CLASS);
    }

    @Step("Проверка активности раздела Соусы")
    public boolean isSaucesActive() {
        return driver.findElement(saucesTab).getAttribute("class").contains(ACTIVE_TAB_CLASS);
    }

    @Step("Проверка активности раздела Начинки")
    public boolean isFillingsActive() {
        return driver.findElement(fillingsTab).getAttribute("class").contains(ACTIVE_TAB_CLASS);
    }

    @Step("Проверка, что раздел Булки активен по умолчанию")
    public boolean isBunsActiveByDefault() {
        return isBunsActive() &&
                !isSaucesActive() &&
                !isFillingsActive();
    }
    @Step("Клик по кнопке Войти в аккаунт")
    public void clickLoginAccountButton() {
        driver.findElement(loginAccountButton).click();
    }

    @Step("Клик по кнопке Личный кабинет")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Ожидание перехода на главную страницу")
    public void waitForUrlToBeBase() {
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
    }
}