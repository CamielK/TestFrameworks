package com.test.jbehave.steps.javascript;

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

}