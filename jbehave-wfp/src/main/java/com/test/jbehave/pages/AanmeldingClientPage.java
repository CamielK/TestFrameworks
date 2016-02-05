package com.test.jbehave.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 *
 * This class contains all methods to open and interact with the 'Aanmelding client' Form.
 *
 * Created by camiel on 12/9/15.
 */
public class AanmeldingClientPage {

    private WebDriver driver; //uses same instance of WebDriver
    private boolean aanmeldingFormIsOpen = false, persoonsgegevensTabIsOpen = true, adresgegevensTabIsOpen = false; //used to switch between tabs
    private WebElement bsnField, bsnIcon, dateFld, adresgegevensTab, persoonsgegevensTab, dateValueFld;
    private WebElement postcodeFld, huisnrFld, straatFld, plaatsFld;
    private String oldBsnIconClass, newBsnIconClass;
    private String currentListName, currentListId;

    //method to open the Aanmelding Client form by clicking the menu items
    public boolean openAanmeldingForm(WebDriver usedDriver) {
        driver = usedDriver;

        //click menu items
        clickMobileDropdown(); //click hamburger to show menu items in case screen is to small
        driver.findElement(By.partialLinkText("Nieuw")).click();
        driver.findElement(By.partialLinkText("Aanmelding cliënt")).click();

        //check if form has been opened
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("update-document.aanmeldingClient.persoonsgegevens.bsn")));

        //initiate tabs and elements currently in view
        initiateTabs();
        initPersoonsgegevensElements();

        //form is open when elements are visible
        if (bsnField.isDisplayed()) {
            aanmeldingFormIsOpen = true;
        }
        return aanmeldingFormIsOpen;
    }

    //enters the given bsn into the bsn field
    public void enterBSN(int bsn) {
        openPersoonsgegevensTab();
        oldBsnIconClass = bsnIcon.getAttribute("class");
        bsnField.clear();
        bsnField.sendKeys(Integer.toString(bsn));
        if (bsn == 128663649) { oldBsnIconClass="icon-globe"; }
    }

    //returns the class of the bsn icon to check if its finished loading
    public String getBsnIconClass() {
        openPersoonsgegevensTab();
        int timeoutCounter = 30000; //wait for AJAX timeout on bsn check
        while (timeoutCounter > 0) {
            newBsnIconClass = bsnIcon.getAttribute("class");

            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            timeoutCounter -= 500;

            if (!newBsnIconClass.equals(oldBsnIconClass) && !newBsnIconClass.equals("icon-globe")) {
                timeoutCounter = 0;
            }
        }
        return newBsnIconClass;
    }

    //clicks on the given list to open its drowdownlist
    public void expandList(String listName) {
        openPersoonsgegevensTab();
        this.currentListName = listName;
        if (listName.equals("aanhef")) { currentListId = "select2-update-document.aanmeldingClient.persoonsgegevens.salutation"; } //-container
        else if (listName.equals("geslacht")) { currentListId = "select2-update-document.aanmeldingClient.persoonsgegevens.gender"; }
        else if (listName.equals("burgerlijke staat")) { currentListId = "select2-update-document.aanmeldingClient.persoonsgegevens.maritalStatus"; }
        else if (listName.equals("type relatie")) { currentListId = "select2-update-document.aanmeldingClient.relation.contactType"; }
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(currentListId + "-container")));
        WebElement expandBtn = driver.findElement(By.id(currentListId + "-container"));
        expandBtn.click();
    }

    //checks if the opened list is filled with the correct items
    public boolean listIsFilled(String value1, String value2) {
        boolean filled = false;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'select2-container select2-container--default select2-container--open')]")));
        List<WebElement> listContent = driver.findElement(By.id(currentListId+"-results")).findElements(By.tagName("li"));
        for (WebElement li : listContent) {
            String text = li.getText();
            if (text.equals(value1) || text.equals(value2)) { filled = true; }
        }
        expandList(currentListName);
        return filled;
    }

    //enters the given date into the
    public void enterDate(String date) {
        openPersoonsgegevensTab();
        dateFld.sendKeys(date);
    }

    //returns the value of the date field
    public String getDateValue() {
        return dateValueFld.getAttribute("value");
    }

    //enters the given postcode into the correct field
    public void enterPostcode(String postcode) {
        openAdresgegevensTab();
        postcodeFld.clear();
        postcodeFld.sendKeys(postcode);
    }

    //enters the given huisnr into the correct field
    public void enterHuisnr(String huisnr) {
        openAdresgegevensTab();
        huisnrFld.clear();
        huisnrFld.sendKeys(huisnr);
    }

    //use the source script to get the street and city name from the api
    public void javascriptTest(String zipcode, String number) {
        openAdresgegevensTab();
        ((JavascriptExecutor) driver)
                .executeScript(
                        "$.ajax({"+
                        "url: \"http://api.postcodedata.nl/v1/postcode/?postcode="+zipcode.replace(" ", "")+"&streetnumber="+number.replace(" ", "")+"&ref=nlcom.nl&type=xml\"," +
                        "type: \"GET\"," +
                        "error: function(jqxhr, status, error) {" +
                        "    console.error(status);"+
                        "    console.error(error);"+
                        "    $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.street\").val(\"\");"+
                        "    $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.city\").val(\"\");"+
                        "    $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.street\").prop('readonly', false);"+
                        "    $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.city\").prop('readonly', false);"+
                        "},"+
                        "beforeSend: function(x) {"+
                        "    $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.street\").val(\"\");"+
                        "    $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.city\").val(\"\");"+
                        "    $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.street\").prop('readonly', true);"+
                        "    $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.city\").prop('readonly', true);"+
                        "},"+
                        "success: function(data) {"+
                        "    $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.street\").val($(data).find(\"detail\").find(\"street\").text());"+
                        "    $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.city\").val($(data).find(\"detail\").find(\"city\").text());"+
                        "    if (!$(data).find(\"detail\").find(\"street\").text()){"+
                        "        $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.street\").prop('readonly', false);"+
                        "        $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.city\").prop('readonly', false);"+
                        "    } else {"+
                        "        $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.country\").select2(\"val\", \"Nederland\");"+
                        "        $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.street\").prop('readonly', false);"+
                        "        $(\"#update-document\\\\.aanmeldingClient\\\\.adresgegevens\\\\.city\").prop('readonly', false);"+
                        "    }"+
                        "},"+
                        "timeout: 10000"+
                        "});");
        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
    }


    //returns the streetname
    public String getStreet() {
        int timeoutCounter = 10000;
        while (straatFld.getAttribute("value").equals("") && timeoutCounter > 0) {
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            timeoutCounter -= 500;
        }
        return straatFld.getAttribute("value");
    }

    //returns the city name
    public String getPlaats() {
        return plaatsFld.getAttribute("value");
    }


    private void initiateTabs() {
        adresgegevensTab = driver.findElement(By.id("tabhead-3-aa"));
        persoonsgegevensTab = driver.findElement(By.id("tabhead-1-aa"));
    }

    //opens the persoonsgegevens tab if its not already open
    private void openPersoonsgegevensTab() {
        if (!persoonsgegevensTabIsOpen) {
            persoonsgegevensTab.click();
            persoonsgegevensTabIsOpen = true;
            adresgegevensTabIsOpen = false;
            initPersoonsgegevensElements();

            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("update-document.aanmeldingClient.persoonsgegevens.bsn")));
        }
    }

    //opens the adresgegevens tab if its not already open
    private void openAdresgegevensTab() {
        if (!adresgegevensTabIsOpen) {

            //adresgegevensTab.click();
            ((JavascriptExecutor) driver).executeScript("$(document.getElementById(\"tabhead-3-aa\")).click()");
            //try { Thread.sleep(10000); } catch (Exception e) { e.printStackTrace(); }

            persoonsgegevensTabIsOpen = false;
            adresgegevensTabIsOpen = true;
            initAdresgegevensElements();

            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement elementButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("update-document.aanmeldingClient.adresgegevens.zipcode")));
        }
    }

    //finds all elements of the persoonsgegevens tab
    private void initPersoonsgegevensElements() {
        bsnField = driver.findElement(By.id("update-document.aanmeldingClient.persoonsgegevens.bsn"));
        bsnIcon = driver.findElement(By.id("bsnIconCheck"));
        dateFld = driver.findElement(By.id("entry-document.aanmeldingClient.persoonsgegevens.dateOfBirth"));
        dateValueFld = driver.findElement(By.id("update-document.aanmeldingClient.persoonsgegevens.dateOfBirth"));
    }

    //finds all elements of the adresgegevens tab
    private void initAdresgegevensElements() {
        postcodeFld = driver.findElement(By.id("update-document.aanmeldingClient.adresgegevens.zipcode"));
        huisnrFld = driver.findElement(By.id("update-document.aanmeldingClient.adresgegevens.houseNumber"));
        straatFld = driver.findElement(By.id("update-document.aanmeldingClient.adresgegevens.street"));
        plaatsFld = driver.findElement(By.id("update-document.aanmeldingClient.adresgegevens.city"));
    }

    //clicks the hamburger icon if its visible
    private void clickMobileDropdown() {
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        try {
            driver.findElement(By.xpath("//*[contains(@class, 'btn btn-navbar')]")).click();
        }
        catch (Exception e) {
        }
    }
}
