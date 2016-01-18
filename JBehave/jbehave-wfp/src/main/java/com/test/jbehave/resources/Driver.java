package com.test.jbehave.resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class returns WebDrivers that are logged in and already navigated to the correct url
 *
 * Created by camiel on 11/18/15.
 */
public class Driver {

    private static com.test.jbehave.pages.LoginPage loginPage = new com.test.jbehave.pages.LoginPage();
    private static List<WebDriver> driverList = new ArrayList<WebDriver>(); //a list of all opened drivers

    //returns a WebDriver that is logged into the wfp frontend
    public WebDriver getFrontendDriver() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.openLoginPage(driver, "frontend");
        loginPage.enterUsername("demo");
        loginPage.enterPassword("demo");
        loginPage.submitForm();
        driverList.add(driver);
        return driver;
    }

    //returns a WebDriver that is logged into the wfp backend
    public WebDriver getBackendDriver() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(11, TimeUnit.SECONDS);
        loginPage.openLoginPage(driver, "backend");
        loginPage.enterUsername("admin");
        loginPage.enterPassword("dUfKB2yacJ");//dUfKB2yacJ
        loginPage.submitForm();
        driverList.add(driver);
        return driver;
    }

    //closes all open drivers
    public void closeAllDrivers() {
        for (int i = 0; i < driverList.size(); i++) {
            driverList.get(i).quit();
        }
    }
}
