package resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 *
 * this class starts new drivers, opens backend or frontend and logs in. returns webdriver
 *
 * Created by camiel on 11/15/15.
 */
public class SuiteSetup {

    private WebDriver driver = new ChromeDriver();
    private BackendLogin backendLogin = new BackendLogin();
    private FrontendLogin frontendLogin = new FrontendLogin();

    public WebDriver getBackendDriver () {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to("http://localhost:8082/workflow4people");
        WebDriverWait wait = new WebDriverWait(driver, 12);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("j_username")));
        backendLogin.setDriver(driver);
        backendLogin.enterUsername("admin");
        backendLogin.enterPassword("admin");
        backendLogin.submitForm("Workflow Definition List");
        return driver;
    }

    public WebDriver getFrontendDriver () {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to("http://localhost:8084/forms");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        frontendLogin.setDriver(driver);
        frontendLogin.enterUsername("demo");
        frontendLogin.enterPassword("demo");
        frontendLogin.submitForm("Welcome to Workflow4people Forms");
        return driver;
    }
}
