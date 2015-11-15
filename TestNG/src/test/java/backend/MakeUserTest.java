/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

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

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

/**
 *
 * @author camiel
 */
public class MakeUserTest {
    	
    private WebDriver driver;
	
    @BeforeSuite(alwaysRun = true)
    public void setupBeforeSuite(ITestContext context) {
        driver = new resources.SuiteSetup().getBackendDriver();
    }

    @Test(description="make user", priority = 1)
    public void makeUser() {
    }

    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
  	    driver.quit();
    }

}
