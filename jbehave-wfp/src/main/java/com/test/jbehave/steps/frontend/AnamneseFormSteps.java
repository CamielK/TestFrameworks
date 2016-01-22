package com.test.jbehave.steps.frontend;

import com.test.jbehave.pages.AnamnesePage;
import com.test.jbehave.resources.Driver;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

/**
 * This class contains step definitions to make story's for the anamnese form
 *
 * Created by camiel on 12/15/15.
 */
public class AnamneseFormSteps {

    private WebDriver driver;
    private boolean frontendAvailable = false;
    private boolean formIsOpen = false;
    private AnamnesePage formPage = new AnamnesePage();
    private boolean ziektenbeeldFieldsVisible = false;
    //private Driver driverFactory = new Driver();

    @Given("the anamnese form is opened")
    public void openForm () {
        openFrontend();

        if (!formIsOpen) {
            formIsOpen = formPage.openAnamneseForm(driver);
        }
        else if (formIsOpen) {
            formPage.closeForm();
            formIsOpen = formPage.openAnamneseForm(driver);
        }
        assertEquals(true, formIsOpen);
    }



    @When("I search for a client based on <searchtype>")
    public void searchClient (@Named("searchtype") String searchType) {
        //try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }

        if (searchType.equals("Achternaam")) { formPage.enterFamilyName("Robbescheuten - Issum, van"); }
        else if (searchType.equals("Geboortedatum")) {
            formPage.enterBirthdate("07021939");
            formPage.clickSearchButton();
            formPage.selectClient("154390");
        }
        else if (searchType.equals("BSN")) { formPage.enterBSN("165736070"); }
        else if (searchType.equals("Clientnummer")) { formPage.enterClientnummer("154390"); }
        formPage.clickSearchButton();
    }

    @When("I click on each ziektenbeeld")
    public void clickZiektenbeelden () {
        ziektenbeeldFieldsVisible = formPage.checkZiektenbeeldFields();
    }

    @Then("the correct client should be displayed")
    public void checkClientIsDisplayed () {
        assertEquals("Issum", formPage.getResultName());

    }

    @Then("each ziektenbeeld should display an explenation field")
    public void assertZiektenbeeldVisibility() {
        assertEquals(true, ziektenbeeldFieldsVisible);
    }

    private void openFrontend() {
        if (!frontendAvailable) {
            driver = new Driver().getFrontendDriver();
            frontendAvailable = true;
        }
        assertEquals(true, frontendAvailable);
    }

}