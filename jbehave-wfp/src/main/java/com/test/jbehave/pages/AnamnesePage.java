package com.test.jbehave.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by camiel on 12/15/15.
 *
 * description: contains all functionality of the Anamnese form. this includes opening the form and allowing the forms elements to be used
 *
 */

public class AnamnesePage {

    private WebDriver driver;
    private boolean anamneseFormIsOpen = false;
    private WebElement clientnrField, bsnField, birthdateField, nameField, nameResultField;

    //opens the anamnese form
    public boolean openAnamneseForm(WebDriver usedDriver) {
        driver = usedDriver;

        clickMobileDropdown();
        driver.findElement(By.partialLinkText("Nieuw")).click();
        driver.findElement(By.partialLinkText("Anamnese")).click();
        WebDriverWait wait = new WebDriverWait(driver, 11);
        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("update-document.anamnese.clientInformatie.base.identification.uniqueNumber")));

        initFields();

        if (clientnrField.isDisplayed()) {
            anamneseFormIsOpen = true;
        }
        return anamneseFormIsOpen;
    }

    //enters the given clientmr into the correct field
    public void enterClientnummer(String clientnr) {
        clientnrField.clear();
        clientnrField.sendKeys(clientnr);
    }

    //enters the given bsn into the bsn field
    public void enterBSN(String bsn) {
        bsnField.clear();
        bsnField.sendKeys(bsn);
    }

    //enters the given value into the birthdate field
    public void enterBirthdate(String birthdate) {
        birthdateField.sendKeys(birthdate);
    }

    //selects the client that matches the given Id
    public void selectClient(String onclickId) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@onclick='chooseClient("+onclickId+")']")));
        //driver.findElement(By.xpath("//input[@onclick='chooseClient("+onclickId+")']")).click();
        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td//button[@onclick='chooseClient("+onclickId+")']")));

        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }

        driver.findElement(By.xpath("//td//button[@onclick='chooseClient("+onclickId+")']")).click();
    }

    //close the form
    public void closeForm() {
        driver.navigate().to("http://localhost:8084/forms/");
        anamneseFormIsOpen = false;
    }

    //click on the search button
    public void clickSearchButton() {
        driver.findElement(By.xpath("//*[contains(@class, 'icon-search')]")).click();
    }

    //returns the name of the person whose credentials were loaded to validate if the correct person was loaded
    public String getResultName() {
        int timeoutCounter = 30000;
        while (timeoutCounter > 0 && nameResultField.getText().equals("...")) {
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            timeoutCounter -= 500;
        }
        return nameResultField.getText();
    }

    //enter the given value into the family name field
    public void enterFamilyName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }

    //opens all ziektenbeelden and checks if the explenation fields are displayed
    public boolean checkZiektenbeeldFields() {
        boolean fieldsVisible = true;

        driver.findElement(By.id("tabhead-3-an")).click();
        anamneseFormIsOpen = false;

        List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@class, 'bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-off bootstrap-switch-id-entry-document.anamnese.ziektenbeelden.zb_')]"));
        for (WebElement element : elements) { element.click(); }
        List<WebElement> toelichtingElements = driver.findElements(By.xpath("//div[contains(@class, 'update-document.anamnese.ziektenbeelden.zb_')]"));
        if (elements.size() == (toelichtingElements.size()/2)) {fieldsVisible = true;}

        return fieldsVisible;
    }

    //initialize the fields currently on screen
    private void initFields() {
        //find the WebElements when the form is opened
        clientnrField = driver.findElement(By.id("update-document.anamnese.clientInformatie.base.identification.uniqueNumber"));
        bsnField = driver.findElement(By.id("update-document.anamnese.clientInformatie.base.identification.bsn"));
        birthdateField = driver.findElement(By.id("entry-document.anamnese.clientInformatie.base.dateOfBirth"));
        nameField = driver.findElement(By.id("update-document.anamnese.clientInformatie.base.name.writtenName"));
        nameResultField = driver.findElement(By.id("birthname"));
    }

    //Click hamburger to display menu items
    private void clickMobileDropdown() {
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        try {
            driver.findElement(By.xpath("//*[contains(@class, 'btn btn-navbar')]")).click();
        }
        catch (Exception e) {
        }
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
    }


}
