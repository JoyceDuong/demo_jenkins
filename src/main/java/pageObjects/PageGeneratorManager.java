package pageObjects;

import org.openqa.selenium.WebDriver;

public class PageGeneratorManager {

    private PageGeneratorManager() {
    }

    public static BuyPackageObject getHomepage(WebDriver driver) {
        return new BuyPackageObject(driver);
    }


    public static LoginPageObject getLoginPage(WebDriver driver) {
        return new LoginPageObject(driver);
    }
}
