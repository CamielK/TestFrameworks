package com.test.jbehave.steps.backend;

import com.test.jbehave.pages.UserPage;
import com.test.jbehave.resources.Driver;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

/**
 * This class contains all step definitions used in the makeUser story
 *
 * Created by camiel on 11/19/15.
 */
public class MakeUserSteps {

    private WebDriver driver;
    private boolean backendAvailable = false;
    private UserPage userPage = new UserPage();

    @Given("the person list is opened")
    public void openPersonList() {
        openBackend();
        userPage.openUserPage(driver);
    }

    @When("I make a new user")
    public void addUser() {
        userPage.openNewUserForm();
        userPage.enterUsername("testUser123");
        userPage.enterUserRealName("test user");
        userPage.enterPassword("test123");
        userPage.enterEmail("testuser@gmail.com");
        userPage.sendNewUserForm();
    }

    @Then("the user should be added")
    public void validateUser() {
        assertEquals(userPage.checkUserWasAdded(), true);
        driver.quit();
        backendAvailable = false;
    }

    private void openBackend() {
        if (!backendAvailable) {
            driver = new Driver().getBackendDriver();
            backendAvailable = true;
        }
        assertEquals(true, backendAvailable);
    }
}
