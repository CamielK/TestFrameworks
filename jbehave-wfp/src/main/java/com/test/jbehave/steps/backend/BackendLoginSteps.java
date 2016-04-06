package com.test.jbehave.steps.backend;

import com.test.jbehave.pages.LoginPage;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 *
 * This class contains all step definitions for backend login story's
 *
 * Created by camiel on 11/18/15.
 */
public class BackendLoginSteps {
    private WebDriver driver;
    private LoginPage loginPage = new LoginPage();
    private boolean loggedIn = false;
    private boolean backendStarted = false;

    @Given("The workflow4people backend is started")
    public void startWfp() {
        if (!backendStarted) {
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            loginPage.openLoginPage(driver, "backend");
            backendStarted = true;
        }
    }

    @When("I enter valid backend credentials")
    public void enterValidCredentials() {
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin");
    }

    @When("I enter invalid backend credentials")
    public void enterInvalidCredentials() {
        loginPage.enterUsername("piet");
        loginPage.enterPassword("123");
    }

    @When("I log in to the backend")
    public void submitForm() {
        loggedIn = loginPage.submitForm();
    }

    @Then("I should be logged in to the backend")
    public void checkValidLogin() {
        assertEquals(true, loggedIn);
        driver.quit();
    }

    @Then("I should not be logged in to the backend")
    public void checkInvalidLogin() {
        assertEquals(false, loggedIn);
        driver.quit();
        backendStarted= false;
    }
}
