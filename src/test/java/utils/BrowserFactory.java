package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.InputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;

public class BrowserFactory {
    private static final Properties properties = new Properties();
    private static final int IMPLICIT_WAIT_SECONDS = 5;

    static {
        try (InputStream input = BrowserFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static WebDriver getDriver() {
        String browser = System.getProperty("browser",
                        properties.getProperty("browser", "chrome"))
                .toLowerCase();

        System.out.println("Using browser: " + browser);

        WebDriver driver;
        switch (browser) {
            case "yandex":
                driver = startYandexBrowser();
                break;
            case "chrome":
            default:
                driver = startChrome();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        return driver;
    }

    private static WebDriver startYandexBrowser() {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);

        WebDriverManager.chromedriver().browserVersion("136").setup();

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe");
        options.addArguments("--remote-allow-origins=*", "--disable-blink-features=AutomationControlled");
        return new ChromeDriver(options);
    }

    private static WebDriver startChrome() {

        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--remote-allow-origins=*",
                "--disable-dev-shm-usage",
                "--no-sandbox",
                "--disable-blink-features=AutomationControlled",
                "--disable-logging",
                "--log-level=3"  // Минимальный уровень логов
        );

        options.setExperimentalOption("excludeSwitches",
                new String[]{"enable-automation", "enable-logging"});

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }
}
