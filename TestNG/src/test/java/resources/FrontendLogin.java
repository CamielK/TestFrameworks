package resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.testng.Assert.assertEquals;

/**
 * Created by camiel on 11/14/15.
 */
public class FrontendLogin {

    private WebDriver driver;

    public void setDriver(WebDriver usedDriver) {
        this.driver = usedDriver;
    }

    public void enterUsername(String username) {
        WebElement userElement = driver.findElement(By.id("username"));
        userElement.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passElement = driver.findElement(By.name("password"));
        passElement.sendKeys(password);
    }

    public void submitForm(String validator) {
        WebElement formElement = driver.findElement(By.name("username"));
        formElement.submit();
        assertEquals(driver.getTitle(), validator);
    }
}
