/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import resources.FrontendLogin;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

/**
 *
 * @author camiel
 */
public class FormVisibilityTest {
    	
    private WebDriver driver;
	
    @BeforeSuite(alwaysRun = true)
    public void setupBeforeSuite(ITestContext context) {
        driver = new resources.SuiteSetup().getFrontendDriver();
    }

    @Test(description="open form", priority = 1)
    public void openForm() {
        driver.findElement(By.linkText("New")).click();
        driver.findElement(By.linkText("Ontslag Procedure")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("update-document.persoonsgegevens.naam")));
    }

    @Test(description="element visibility", priority = 2)
    public void checkElementVisibility() {
        //functie should not be visible
        assertEquals(driver.findElement(By.id("update-document.persoonsgegevens.functie")).isDisplayed(), false);
        inputNaam("Piet");
        assertEquals(driver.findElement(By.id("update-document.persoonsgegevens.functie")).isDisplayed(), true);
        inputNaam("");
        assertEquals(driver.findElement(By.id("update-document.persoonsgegevens.functie")).isDisplayed(), false);
    }

    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
  	    driver.quit();
    }


    private void inputNaam(String value) {
        WebElement userElement = driver.findElement(By.id("update-document.persoonsgegevens.naam"));
        userElement.clear();
        userElement.sendKeys(value);
        driver.findElement(By.id("update-document.persoonsgegevens.geslacht")).click();
    }
}
