package com.test.jbehave.steps.frontend;

import com.test.jbehave.pages.AanmeldingClientPage;
import com.test.jbehave.resources.Driver;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

/**
 *
 * This class contains step definitions to make aanmelding client story's
 *
 * Created by camiel on 12/9/15.
 */
public class AanmeldingClientSteps {

    private WebDriver driver;
    private boolean frontendAvailable = false;
    private boolean formIsOpen = false;
    private AanmeldingClientPage formPage = new AanmeldingClientPage();
    private int run = 0;

    @Given("the aanmelding client form is opened")
    public void openForm() {
        openFrontend();

        if (!formIsOpen) {
            formIsOpen = formPage.openAanmeldingForm(driver);
        }
        assertEquals(true, formIsOpen);
    }

    @When("I enter a valid BSN")
    public void enterValidBSN() {
        formPage.enterBSN(13261246); //voldoet aan 11 check
    }

    @When("I enter an invalid BSN")
    public void enterInvalidBSN() {
        formPage.enterBSN(13261246); //voldoet niet aan 11 check
    }

    @When("I enter a used BSN")
    public void enterUsedBSN() {
        formPage.enterBSN(13261246); //is al in gebruik
    }

    @When("I expand the <list> list")
    public void expandList(@Named("list") String listName) {
        if (listName.equals("aanhef")) { formPage.expandList("aanhef"); }
        else if (listName.equals("geslacht")) { formPage.expandList("geslacht"); }
        else if (listName.equals("burgerlijke staat")) { formPage.expandList("burgerlijke staat"); }
        else if (listName.equals("type relatie")) { formPage.expandList("type relatie"); }
    }

    @When("I pick a date")
    public void pickDate() { formPage.enterDate("11112015"); }

    @When("I enter a valid postcode and huisnummer")
    public void enterValidAdress() {
        formPage.javascriptTest("5132GS", "135");
    }

    @Then("the BSN should be rejected")
    public void checkBSNRejected() {
        run++;
        assertEquals("icon-remove", formPage.getBsnIconClass());
        if (run == 2) {
            driver.quit(); frontendAvailable = false; formIsOpen = false;
        }
    }

    @Then("the date should be displayed")
    public void thenTheDateShouldBeDisplayed() { assertEquals("2015-11-11T00:00:00", formPage.getDateValue()); }

    @Then("the BSN should be accepted")
    public void checkBSNAccepted() {
        assertEquals( "icon-ok", formPage.getBsnIconClass());
    }

    @Then("the list should be filled with at least <value1> and <value2>")
    public void checkListItems(@Named("value1") String value1, @Named("value2") String value2) {
        assertEquals(true, formPage.listIsFilled(value1, value2));
    }

    @Then("the straat and plaats should correctly be generated")
    public void checkGeneratedAdress() {
        assertEquals("Burgemeester Beckersweg", formPage.getStreet());
        assertEquals("Mheer", formPage.getPlaats());

        driver.quit(); frontendAvailable = false; formIsOpen = false;
    }

    private void openFrontend() {
        if (!frontendAvailable) {
            driver = new Driver().getFrontendDriver();
            frontendAvailable = true;
        }
        assertEquals(true, frontendAvailable);
    }
}
