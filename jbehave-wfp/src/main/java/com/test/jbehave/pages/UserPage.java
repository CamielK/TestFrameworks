package com.test.jbehave.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 *
 * This class contains methods to interact with the user page and new user form
 *
 * Created by camiel on 11/20/15.
 */
public class UserPage {

    public WebDriver driver;
    private boolean userTabOpen = false;
    private boolean passwordTabOpen = false;
    private String newUsername = "";

    //opens the user page
    public void openUserPage(WebDriver usedDriver) {
        driver = usedDriver;

        //click menu items
        //clickMobileDropdown();
        driver.findElement(By.partialLinkText("Admin")).click();
        driver.findElement(By.partialLinkText("Users")).click();

        //validate that form is opened
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@onclick=\"dialog.formDialog(null,'person', { refresh : 'detailTable_person'}, {})\"]")));
        // WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newButton_detailTable_person")));
    }

    //opens the new user form if its not already open
    public void openNewUserForm() {
        if (!userTabOpen && !passwordTabOpen) {
            //click 'new' button
            WebDriverWait wait = new WebDriverWait(driver, 10);
            driver.findElement(By.xpath("//span[@onclick=\"dialog.formDialog(null,'person', { refresh : 'detailTable_person'}, {})\"]")).click();

            //check form is opened
            WebElement elementUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            userTabOpen = true;
        }
    }

    //enters the given username into the username field
    public void enterUsername(String username) { userTab(); textInput("username", username); newUsername = username;}

    //enters the given realname into the realname field
    public void enterUserRealName(String realName) { userTab(); textInput("userRealName", realName); }

    //enters the given name into the correct field
    public void enterGivenName(String givenName) { userTab(); textInput("givenName", givenName); }

    //enters the given family name into the correct field
    public void enterFamilyName(String familyName) { userTab(); textInput("familyName",familyName); }

    //enters the given email adres into the correct field
    public void enterEmail(String email) { userTab(); textInput("email",email); }

    //enters the given password into the password1 and password2 fields
    public void enterPassword(String password) { passwordTab(); textInput("newPassword",password); textInput("newPassword2",password); }

    //submits the new user form and checks if it is closed
    public void sendNewUserForm() {
        driver.findElement(By.xpath("//span[text()='OK']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@onclick=\"dialog.formDialog(null,'person', { refresh : 'detailTable_person'}, {})\"]")));
        userTabOpen = false; passwordTabOpen = false;
    }

    //checks if the user was added by sorting the table to show the highest id number first (this should be the new user)
    public boolean checkUserWasAdded() {
        boolean userAdded = false;

        //wait untill table is visible
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("detailTable_person")));

        //sort descending(newest on top) by clicking on 'id'
        WebElement table = driver.findElement(By.id("detailTable_person"));
        WebElement idCell = table.findElement(By.xpath("//th[text()='Id']"));
        idCell.click();
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

        //select first table entry
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        String userId = tableRows.get(2).findElement(By.xpath("//td[1]")).getText();
        String nameCell = tableRows.get(2).findElement(By.xpath("//td[2]")).getText();

        //validate if first entry is the new user. delete user if true, fails test if false
        if (nameCell.equals(newUsername)) {
            deleteNewUser(table, userId);
            userAdded = true;
        }

        return userAdded;

    }

    //deletes the new user by clicking the first table entry's remove button
    private void deleteNewUser (WebElement table, String userId) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement delete = table.findElement(By.xpath("//span[@onclick=\"dialog.deleteDialog('"+userId+"','person',{ refresh : 'null'}, null)\"]"));
        delete.click();
        //try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }

        WebElement elementConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Delete']")));
        WebElement confirm = table.findElement(By.xpath("//span[text()='Delete']"));
        confirm.click();
        //try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }

        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@onclick=\"dialog.formDialog(null,'person', { refresh : 'detailTable_person'}, {})\"]")));
    }

    //enters the given value into the correct element
    private void textInput (String elementID, String value) {
        WebElement inputElement = driver.findElement(By.id(elementID));
        inputElement.sendKeys(value);
    }

    //opens the password tab if its not already open
    private void passwordTab () {
        if (!passwordTabOpen) {
            driver.findElement(By.partialLinkText("Password")).click();
            WebDriverWait wait = new WebDriverWait(driver, 11);
            WebElement elementPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newPassword")));
            userTabOpen = false; passwordTabOpen = true;
        }
    }

    //open the user tab if its not already open
    private void userTab () {
        if (!userTabOpen) {
            driver.findElement(By.partialLinkText("Details")).click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement elementUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            userTabOpen = true; passwordTabOpen = false;
        }
    }

    //click hamburger icon to display menu items
    //TODO decrease time spend checking for hamburger
    private void clickMobileDropdown() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
//        System.out.println("searching menu items");
//        List<WebElement> menuItem = driver.findElements(By.partialLinkText("Nieuw"));
//        System.out.println("finished searching menu items");
//        if (menuItem.size() == 0) {
//            System.out.println("searching hamburger items");
//            List<WebElement> hamburger = driver.findElements(By.xpath("//*[contains(@class, 'btn btn-navbar')]"));
//            System.out.println("finished searching hamburger items");
//            if (hamburger.size() == 1) {
//                hamburger.get(0).click();
//            }
//        }

        try {
            driver.findElement(By.xpath("//*[contains(@class, 'btn btn-navbar')]")).click();
        }
        catch (Exception e) {

        }

    }

}
