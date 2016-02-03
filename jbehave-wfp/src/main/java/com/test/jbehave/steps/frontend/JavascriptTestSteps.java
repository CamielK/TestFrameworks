package com.test.jbehave.steps.frontend;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by camiel on 2/3/16.
 */
public class JavascriptTestSteps {

    WebDriver driver;

    @Given("a google page is opened")
    public void openPage() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1024,768");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new ChromeDriver(capabilities);

        driver.get("http://google.com/");
    }

    @When("I execute a javascript")
    public void executeScript() {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver)
                    .executeScript("alert('hello world');");
        }
    }

    @Then("the script should be executed")
    public void checkScript() {
        try { Thread.sleep(5000); } catch (Exception e) { e.printStackTrace(); }
        driver.quit();
    }


    //TODO check ajax results (envida/wfp-data/bootstrap-forms-template/form/request-tabs-aanmeldingClient.gsp)
//        $.ajax({
//                url: "http://api.postcodedata.nl/v1/postcode/?postcode="+zipcode.replace(/ /g, '')+"&streetnumber="+number.replace(/ /g, '')+"&ref=nlcom.nl&type=xml",
//                type: "GET",
//                error: function(jqxhr, status, error) {
//            console.error(status);
//            console.error(error);
//            $("#update-document\\.aanmeldingClient\\.adresgegevens\\.street").val("");
//            $("#update-document\\.aanmeldingClient\\.adresgegevens\\.city").val("");
//            $("#update-document\\.aanmeldingClient\\.adresgegevens\\.street").prop('readonly', false);
//            $("#update-document\\.aanmeldingClient\\.adresgegevens\\.city").prop('readonly', false);
//        },
//        beforeSend: function(x) {
//            $("#update-document\\.aanmeldingClient\\.adresgegevens\\.street").val("");
//            $("#update-document\\.aanmeldingClient\\.adresgegevens\\.city").val("");
//            $("#update-document\\.aanmeldingClient\\.adresgegevens\\.street").prop('readonly', true);
//            $("#update-document\\.aanmeldingClient\\.adresgegevens\\.city").prop('readonly', true);
//        },
//        success: function(data) {
//            $("#update-document\\.aanmeldingClient\\.adresgegevens\\.street").val($(data).find("detail").find("street").text());
//            $("#update-document\\.aanmeldingClient\\.adresgegevens\\.city").val($(data).find("detail").find("city").text());
//            if (!$(data).find("detail").find("street").text()){
//                $("#update-document\\.aanmeldingClient\\.adresgegevens\\.street").prop('readonly', false);
//                $("#update-document\\.aanmeldingClient\\.adresgegevens\\.city").prop('readonly', false);
//            } else {
//                $("#update-document\\.aanmeldingClient\\.adresgegevens\\.country").select2("val", "Nederland");
//                $("#update-document\\.aanmeldingClient\\.adresgegevens\\.street").prop('readonly', false);
//                $("#update-document\\.aanmeldingClient\\.adresgegevens\\.city").prop('readonly', false);
//            }
//        },
//        timeout: 10000
//        });

}