package pageObjects;

import commons.BasePage;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageUIs.LoginPageUI;

public class LoginPageObject extends BasePage {
    private WebDriver driver;

    public LoginPageObject(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Enter Email Text Box")
    public void enterEmailTextBox(String value) {
        waitForElementVisible(driver, LoginPageUI.EMAIL_TEXT_BOX);
        sendKeysToElement(driver,LoginPageUI.EMAIL_TEXT_BOX,value);
    }
    @Step("Enter Password Text Box")
    public void enterPasswordTextBox(String password) {
        waitForElementVisible(driver, LoginPageUI.PASSWORD_TEXT_BOX);
        sendKeysToElement(driver,LoginPageUI.PASSWORD_TEXT_BOX,password);
    }
    @Step("Click To Login Button")
    public BuyPackageObject clickToLoginButton() {
        waitForElementClickable(driver,LoginPageUI.LOGIN_BUTTON);
        clickToElement(driver,LoginPageUI.LOGIN_BUTTON);
        return PageGeneratorManager.getHomepage(driver);
    }
}
