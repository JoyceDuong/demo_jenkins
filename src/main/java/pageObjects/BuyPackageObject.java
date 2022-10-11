package pageObjects;

import commons.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageUIs.BuyPackageUI;

import java.util.ArrayList;
import java.util.List;

public class BuyPackageObject extends BasePage {
    private WebDriver driver;


    public BuyPackageObject(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Click To Buy Button")
    public void clickToBuyButton() {
        waitForElementClickable(driver, BuyPackageUI.BUY_BUTTON);
        clickToElementByJS(driver, BuyPackageUI.BUY_BUTTON);
    }
    @Step("Select Service By Service Name")
    public void selectServiceByServiceName(String serviceName) {

        waitForElementClickable(driver, BuyPackageUI.PACKAGE_OPTION,serviceName);
        clickToElement(driver, BuyPackageUI.PACKAGE_OPTION,serviceName);

    }
    @Step("Click To Start Now Button")
    public void clickToStartNowButton() {
        waitForElementClickable(driver, BuyPackageUI.START_BUTTON);
        clickToElement(driver, BuyPackageUI.START_BUTTON);
    }
    @Step("Select Company Add Type")
    public void selectCompanyAddType(String addType) {
        waitForElementClickable(driver, BuyPackageUI.COMPANY_TYPE_BY_TEXT,addType);
        clickToElement(driver, BuyPackageUI.COMPANY_TYPE_BY_TEXT,addType);
        if(addType == "Registered Address & Digital Mailroom Service" ){
            String addValue = getElementPropertybyJS(driver,"//input[@aria-label='Address Line 1']");
            System.out.println(addValue);
            waitForElementVisible(driver,"(//div[@class=\"v-input v-text-field v-input--is-label-active v-input--is-dirty v-input--is-disabled theme--light\"])[1]");
        }
    }
    @Step("Click on Next Button")
    public void clickonNextButton() {
        waitForElementClickable(driver, BuyPackageUI.NEXT_BUTTON);
        clickToElement(driver, BuyPackageUI.NEXT_BUTTON);
    }
    @Step("Select Shareholder Number")
    public void selectShareholderNumber(String expectedNumber) {
        waitForElementClickable(driver, BuyPackageUI.PARENT_DROPDOWN);
        selectItemInDropdownList(driver,BuyPackageUI.PARENT_DROPDOWN,BuyPackageUI.CHILD_DROPDOWN,expectedNumber);
    }
    @Step("Get Toasted Message")
    public boolean getToastedMessage() {

        waitForElementVisible(driver,BuyPackageUI.TOASTED_MESSAGE);
       return isElementDisplayed(driver,BuyPackageUI.TOASTED_MESSAGE);


    }
    @Step("Select Director Option By Text")
    public void selectDirectorOptionByText(String dirOption) {
        waitForElementClickable(driver, BuyPackageUI.DIRECTOR_OPTION,dirOption);
        clickToElement(driver, BuyPackageUI.DIRECTOR_OPTION,dirOption);
    }

    @Step("Click On Cart")
    public void clickOnCart() {
        waitForElementClickable(driver, BuyPackageUI.CART_BUTTON);
        clickToElement(driver, BuyPackageUI.CART_BUTTON);
    }

    @Step("Is List Item Displayed")
    public boolean isListItemDisplayed(String[] cartItems) {
        sleepInSecond(5);
        ArrayList<String> actualItems = new ArrayList<String>();
        List<WebElement> Elements = getElements(driver,BuyPackageUI.CART_ITEMS);
        for (WebElement element: Elements) {
            actualItems.add(element.getText());
        }
        System.out.println("Length of actual Items is: " + Elements.size());
        System.out.println(actualItems);

        if (cartItems.length != actualItems.size()){
            return false;
        }

        for(int j = 0; j < actualItems.size(); j++){
//            System.out.println(cartItems[j] +" - " +actualItems.get(j));

            if(cartItems[j].trim().contains(actualItems.get(j).trim())== false){
                return false;
            }
        }

        return  true;

    }

    @Step("Click On Skip Option")
    public void clickOnSkipOption() {
        waitForElementClickable(driver, BuyPackageUI.SKIP_ACCOUNTING_OPTION);
        clickToElement(driver, BuyPackageUI.SKIP_ACCOUNTING_OPTION);
    }

    @Step("Click On Button By Text")
    public void clickOnButtonByText(String text) {
    sleepInSecond(3);
    waitForElementClickable(driver, BuyPackageUI.CONFIRM_BUTTON,text);
        clickToElement(driver, BuyPackageUI.CONFIRM_BUTTON,text);
    }

    @Step("Fill Out Company Admin")
    public void fillOutCompanyAdmin(String email, String firstName, String lastName, String companyName) {

        System.out.println("email company admin là:" + email);

        waitForElementVisible(driver,BuyPackageUI.EMAIL_TEXTBOX);
        sendKeysToElement(driver,BuyPackageUI.EMAIL_TEXTBOX,email);

        waitForElementVisible(driver,BuyPackageUI.FIRST_NAME_TEXTBOX);
        sendKeysToElement(driver,BuyPackageUI.FIRST_NAME_TEXTBOX,firstName);

        waitForElementVisible(driver,BuyPackageUI.LAST_NAME_TEXTBOX);
        sendKeysToElement(driver,BuyPackageUI.LAST_NAME_TEXTBOX,lastName);

        waitForElementVisible(driver,BuyPackageUI.COMPANY_NAME_TEXTBOX);
        sendKeysToElement(driver,BuyPackageUI.COMPANY_NAME_TEXTBOX,companyName);
    }




}
