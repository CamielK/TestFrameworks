package com.test.jbehave.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * This class contains all functionality to interact with the login screen
 *
 * Created by camiel on 11/17/15.
 */
public class LoginPage {

    private WebDriver driver;
    private String usedInterface;
    private static String frontendURL = "http://localhost:8084/forms";
    private static String backendURL = "http://localhost:8082/workflow4people";

    //open the login page by going to the correct url
    public void openLoginPage (WebDriver usedDriver, String focus) {
        driver = usedDriver;
        usedInterface = focus;
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (usedInterface.equals("frontend")) {
            driver.navigate().to(frontendURL);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        }
        else if (usedInterface.equals("backend")) {
            driver.navigate().to(backendURL);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("j_username")));
        }
    }

    //enter the given username into the username field
    public void enterUsername(String username) {
        if (usedInterface.equals("frontend")) { enterValue(username, "username"); }
        else if (usedInterface.equals("backend")) { enterValue(username, "j_username"); }

    }

    //enter the given password into the password field
    public void enterPassword(String password) {
        if (usedInterface.equals("frontend")) { enterValue(password, "password"); }
        else if (usedInterface.equals("backend")) { enterValue(password, "j_password"); }
    }

    //submits the login form
    public Boolean submitForm() {
        String originalTitle = driver.getTitle();
        Boolean loggedIn = false;
        if (usedInterface.equals("frontend")) {
            WebElement formElement = driver.findElement(By.name("username"));
            formElement.submit();
        }
        else if (usedInterface.equals("backend")) {
            WebElement formElement = driver.findElement(By.name("j_username"));
            formElement.submit();
        }
        if (driver.getTitle().equals(originalTitle)) { loggedIn = false ; }
        else { loggedIn = true; }
        return loggedIn;
    }




    //enters the given value into the correct field
    private void enterValue(String value, String fieldName) {
        WebElement passElement = driver.findElement(By.name(fieldName));
        passElement.sendKeys(value);
    }

}
