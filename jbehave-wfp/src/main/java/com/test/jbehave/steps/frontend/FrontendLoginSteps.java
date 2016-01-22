package com.test.jbehave.steps.frontend;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * This class contains step definitions to make the frontend login story
 *
 * Created by camiel on 11/18/15.
 */
public class FrontendLoginSteps {
    private WebDriver driver;
    private com.test.jbehave.pages.LoginPage loginPage = new com.test.jbehave.pages.LoginPage();
    private Boolean loggedIn = false;
    private boolean frontendStarted = false;

    @Given("The workflow4people frontend is started")
    public void startWfp() {
        if (!frontendStarted) {
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            loginPage.openLoginPage(driver, "frontend");
            frontendStarted = true;
        }
        assertEquals(frontendStarted, true);
    }

    @When("I enter valid frontend credentials")
    public void enterValidCredentials() {
        loginPage.enterUsername("demo");
        loginPage.enterPassword("demo");
    }

    @When("I enter invalid frontend credentials")
    public void enterInvalidCredentials() {
        loginPage.enterUsername("piet");
        loginPage.enterPassword("123");
    }

    @When("I log in to the frontend")
    public void submitForm() {
        loggedIn = loginPage.submitForm();
    }

    @Then("I should not be logged in to the frontend")
    public void checkInvalidLogin() {
        assertEquals(false, loggedIn);
    }

    @Then("I should be logged in to the frontend")
    public void checkValidLogin() {
        assertEquals(true, loggedIn);
        driver.quit();
        frontendStarted= false;
    }
}
