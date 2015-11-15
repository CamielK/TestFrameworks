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
import resources.BackendLogin;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author camiel
 */
public class BackendLoginTest {
    	
    private WebDriver driver;
    private resources.BackendLogin login = new resources.BackendLogin();
	
    @BeforeSuite(alwaysRun = true)
    public void setupBeforeSuite(ITestContext context) {
  	    driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        login.setDriver(driver);
    }

    @Test(description="wfp backend setup", priority = 1)
    public void frontendSetup() {
        driver.navigate().to("http://localhost:8082/workflow4people");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("j_username")));
    }

    @Test(description="wfp frontend invalid username login", priority = 2)
    public void frontendInvalidUserLogin() {
        login.enterUsername("piet");
        login.enterPassword("admin");
        login.submitForm("Login");
    }

    @Test(description="wfp frontend invalid password login", priority = 2)
    public void frontendInvalidPassLogin() {
        login.enterUsername("admin");
        login.enterPassword("123");
        login.submitForm("Login");
    }

    @Test(description="wfp frontend invalid credentials login", priority = 2)
    public void frontendInvalidLogin() {
        login.enterUsername("piet");
        login.enterPassword("123");
        login.submitForm("Login");
    }

    @Test(description="5. wfp frontend valid login", priority = 3)
    public void frontendValidLogin() {
        login.enterUsername("admin");
        login.enterPassword("admin");
        login.submitForm("Workflow Definition List");
    }

    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
  	driver.quit();
  }




}
