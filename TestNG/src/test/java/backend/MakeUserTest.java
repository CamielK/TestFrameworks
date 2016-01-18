/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;

/**
 *
 * This class contains methods to interact with the user screen and new user form
 *
 * @author camiel
 */
public class MakeUserTest {
    	
    private WebDriver driver;

    //get a new logged in backend driver at start of test cases
    @BeforeSuite(alwaysRun = true)
    public void setupBeforeSuite(ITestContext context) {
        driver = new resources.SuiteSetup().getBackendDriver();
    }

    //open the user page
    @Test(description="open user panel", priority = 1)
    public void openUserPage() {
        driver.findElement(By.partialLinkText("Admin")).click();
        driver.findElement(By.partialLinkText("Users")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@onclick=\"dialog.formDialog(null,'person', { refresh : 'detailTable_person'}, {})\"]")));
    }

    //make a new user
    @Test(description="create user", priority = 2, dependsOnMethods = { "openUserPage" })
    public void makeUser() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.xpath("//span[@onclick=\"dialog.formDialog(null,'person', { refresh : 'detailTable_person'}, {})\"]")).click();
        WebElement elementUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        textInput("username","testUser123");
        textInput("userRealName","test user");
        textInput("givenName","test");
        textInput("familyName","user");
        textInput("email","testuser@gmail.com");
        driver.findElement(By.partialLinkText("Password")).click();
        WebElement elementPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newPassword")));
        textInput("newPassword","test123");
        textInput("newPassword2","test123");
        driver.findElement(By.xpath("//span[text()='OK']")).click();
        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@onclick=\"dialog.formDialog(null,'person', { refresh : 'detailTable_person'}, {})\"]")));
    }

    //check new user was added by sorting the table by Id. first entry should be highest Id, wich should be the new user
    @Test(description="check if new user was succesfully added", priority = 3, dependsOnMethods = { "makeUser" })
    public void validateNewUser() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //sort descending(newest on top)
        WebElement table = driver.findElement(By.id("detailTable_person"));
        WebElement idCell = table.findElement(By.xpath("//th[text()='Id']"));
        idCell.click();
        Thread.sleep(500);

        //select first table entry
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        String userId = tableRows.get(2).findElement(By.xpath("//td[1]")).getText();
        String nameCell = tableRows.get(2).findElement(By.xpath("//td[2]")).getText();


        //validate if first entry is the new user. delete user if true, fails test if false
        if (nameCell.equals("testUser123")) {
            WebElement delete = table.findElement(By.xpath("//span[@onclick=\"dialog.deleteDialog('"+userId+"','person',{ refresh : 'null'}, null)\"]"));
            delete.click();
            WebElement elementConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Delete']")));
            WebElement confirm = table.findElement(By.xpath("//span[text()='Delete']"));
            confirm.click();
            WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@onclick=\"dialog.formDialog(null,'person', { refresh : 'detailTable_person'}, {})\"]")));
        }
        else {
            org.testng.Assert.fail("Test user was not succesfully created.");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
  	    driver.quit();
    }

    private void textInput (String elementID, String value) {
        WebElement inputElement = driver.findElement(By.id(elementID));
        inputElement.sendKeys(value);
    }
}
