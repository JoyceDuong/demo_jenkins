package commons;

import io.restassured.RestAssured;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class BasePage {




    public BasePage getBasePage() {
        return new BasePage();
    }



    public void openPageUrl(WebDriver driver, String pageUrl) {
        driver.get(pageUrl);
    }

    public String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public Alert waitForAlertPresent(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, GlobalConstant.longTimeout);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(WebDriver driver) {
        alert = waitForAlertPresent(driver);
        alert.accept();
    }

    public void cancelAlert(WebDriver driver) {
        alert = waitForAlertPresent(driver);
        alert.dismiss();
    }

    public void sendKeyToAlert(WebDriver driver, String value) {
        alert = waitForAlertPresent(driver);
        alert.sendKeys(value);
    }

    public String getAlertTitle(WebDriver driver) {
        alert = waitForAlertPresent(driver);
        return alert.getText();
    }

    //     2 windows
    public void switchToWinDowByID(WebDriver driver, String parentID) {
        Set<String> windowIDs = driver.getWindowHandles();
        for (String windowID : windowIDs) {
            if (!windowID.equals(parentID)) {
                driver.switchTo().window(windowID);
                break;
            }
        }

    }

    public void closeAllWindowExceptParent(WebDriver driver, String parentID) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String windowID : allWindowIDs) {
            if (!windowID.equals(parentID)) {
                driver.switchTo().window(windowID);
                driver.close();
                sleepInSecond(1);
            }
            if (driver.getWindowHandles().size() == 1) {
                driver.switchTo().window(parentID);
                break;
            }
        }
    }

    public void switchToWindowByTitle(WebDriver driver, String expectedTitle) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String windowID : allWindowIDs) {
            String actualTitle = driver.getTitle();
            if (actualTitle.equals(expectedTitle)) {
                driver.switchTo().window(windowID);
                break;
            }
        }
    }

    public By getByXpath(String locator) {
        return By.xpath(locator);
    }

    public WebElement getElement(WebDriver driver, String locator) {
        return driver.findElement(By.xpath(locator));
    }

    public List<WebElement> getElements(WebDriver driver, String locator) {
        return driver.findElements(By.xpath(locator));
    }

    public void clickToElement(WebDriver driver, String locator) {
        getElement(driver, locator).click();
    }

    public void clickToElement(WebDriver driver, String locator, String... params) {
        getElement(driver, getDynamicLocator(locator, params)).click();
    }

    public void sendKeysToElement(WebDriver driver, String locator,String value) {
        getElement(driver, locator).clear();
        getElement(driver, locator).sendKeys(value);
    }

    public void sendKeysToElement(WebDriver driver, String locator, String value, String... params) {
        locator = getDynamicLocator(locator, params);
        getElement(driver, locator).clear();
        getElement(driver, locator).sendKeys(value);
    }

    public int getElementSize(WebDriver driver, String locator) {
        return getElements(driver, locator).size();
    }

    public int getElementSize(WebDriver driver, String locator, String... params) {
        return getElements(driver, getDynamicLocator(locator, params)).size();
    }

    public void selectItemInDropdownByText(WebDriver driver, String locator, String itemText) {
        select = new Select(getElement(driver, locator));
        select.selectByVisibleText(itemText);
    }

    public String getSelectedItemTextInDropdown(WebDriver driver, String locator) {
        select = new Select(getElement(driver, locator));
        return select.getFirstSelectedOption().getText();
    }

    public boolean isDropdownMultiple(WebDriver driver, String locator) {
        select = new Select(getElement(driver, locator));
        return select.isMultiple();
    }

    // custom dropdown list
    public void selectItemInDropdownList(WebDriver driver, String parentLocator, String childLocator, String expectedItem) {
        getElement(driver, parentLocator).click();
        sleepInSecond(1);
        explicitWait = new WebDriverWait(driver, GlobalConstant.longTimeout);
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childLocator)));

        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedItem)) {
                if (item.isDisplayed()) {
                    item.click();

                } else {
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                    item.click();
                    sleepInSecond(1);
                }
                break;
            }

        }
    }

    public String getElementAttribute(WebDriver driver, String locator, String attributeName) {
        return getElement(driver, locator).getAttribute(attributeName);
    }

    public String getElementAttribute(WebDriver driver, String locator, String attributeName, String... params) {
        locator = getDynamicLocator(locator, params);
        return getElement(driver, locator).getAttribute(attributeName);
    }

    public String getElementText(WebDriver driver, String locator) {
        return getElement(driver, locator).getText();
    }

    public String getElementText(WebDriver driver, String locator, String... params) {
        locator = getDynamicLocator(locator, params);
        return getElement(driver, locator).getText();
    }

    public void checkToCheckBoxOrRadio(WebDriver driver, String locator) {
        if (!isElementSelected(driver, locator)) {
            getElement(driver, locator).click();
        }
    }

    public void uncheckToCheckBox(WebDriver driver, String locator) {
        if (isElementSelected(driver, locator)) {
            getElement(driver, locator).click();
        }
    }

    public boolean isElementSelected (WebDriver driver, String locator){
        return getElement(driver,locator).isSelected();
    }

    public boolean isElementSelected (WebDriver driver, String locator,String...params){
        locator = getDynamicLocator(locator,params);
        return getElement(driver,locator).isSelected();
    }


    public boolean isElementDisplayed(WebDriver driver, String locator) {

        return getElement(driver, locator).isDisplayed();
    }

    public boolean isElementDisplayed(WebDriver driver, String locator, String... params) {
        locator = getDynamicLocator(locator, params);
        return getElement(driver, locator).isDisplayed();
    }

    public boolean isElementEnabled(WebDriver driver, String locator) {
        return getElement(driver, locator).isEnabled();
    }
    public boolean isElementEnabled(WebDriver driver, String locator,String...params) {
        locator= getDynamicLocator(locator,params);
        return getElement(driver, locator).isEnabled();
    }

    public boolean isElementUndisplayed(WebDriver driver, String locator) {
        overrideGlobalTimeout(driver, GlobalConstant.shortTimeout);
        List<WebElement> elements = getElements(driver, locator);
        overrideGlobalTimeout(driver, GlobalConstant.longTimeout);
        if (elements.size() == 0) {
            System.out.println("Element not in DOM");
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            System.out.println("Element in DOM but not visible/ displayed");
            return true;
        } else {
            System.out.println("Element in DOM and visible");
            return false;
        }
    }

    public boolean isElementUndisplayed(WebDriver driver, String locator, String...params) {
        overrideGlobalTimeout(driver, GlobalConstant.shortTimeout);
        locator = getDynamicLocator(locator, params);
        List<WebElement> elements = getElements(driver, locator);
        overrideGlobalTimeout(driver, GlobalConstant.longTimeout);
        if (elements.size() == 0) {
            System.out.println("Element not in DOM");
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            System.out.println("Element in DOM but not visible/ displayed");
            return true;
        } else {
            System.out.println("Element in DOM and visible");
            return false;
        }
    }

    public WebDriver switchToIframe(WebDriver driver, String locator) {
        return driver.switchTo().frame(getElement(driver, locator));
    }

    public WebDriver switchToDefaultContent(WebDriver driver) {
        return driver.switchTo().defaultContent();
    }

    public void hoverToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.moveToElement(getElement(driver, locator)).perform();
    }

    public void doubleClickToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.doubleClick(getElement(driver, locator)).perform();
    }

    public void rightClickToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.contextClick(getElement(driver, locator)).perform();
    }

    public void dragAndDrop(WebDriver driver, String sourceLocator, String targetLocator) {
        action = new Actions(driver);
        action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
    }

    public void pressKeyToElement(WebDriver driver, String locator, Keys key) {
        action = new Actions(driver);
        action.sendKeys(getElement(driver, locator), key).perform();
    }

    public Object executeForBrowser(WebDriver driver, String javaScript) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript(javaScript);
    }

    public String getInnerText(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
        jsExecutor = (JavascriptExecutor) driver;
        String textActual = (String) jsExecutor
                .executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(WebDriver driver, String url) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.location = '" + url + "'");
    }

    public void highlightElement(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getElement(driver, locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                originalStyle);
    }

    public void clickToElementByJS(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getElement(driver, locator));
    }
    public String getElementPropertybyJS(WebDriver driver, String locator){
        return (String) jsExecutor.executeScript("return arguments[0].value;", driver.findElement(By.xpath(locator)));
    }

    public void scrollToElement(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locator));
    }

    public void sendKeyToElementByJS(WebDriver driver, String locator, String value) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(driver, locator));
    }

    public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
                getElement(driver, locator));
    }


    public boolean isJqueryAJAXLoadingSuccess(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, GlobalConstant.longTimeout);
        jsExecutor = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) jsExecutor.executeScript("return (window.jQuery !=null) && (jQuery.active === 0);");
            }
        };
        return explicitWait.until(jQueryLoad);
    }

    public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, GlobalConstant.longTimeout);
        jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }


    public void waitForElementVisible(WebDriver driver,String locator){
        explicitWait = new WebDriverWait(driver,GlobalConstant.longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
    }

    public void waitForElementVisible(WebDriver driver,String locator,String...params){
        explicitWait = new WebDriverWait(driver,GlobalConstant.longTimeout);
        locator = getDynamicLocator(locator,params) ;
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
    }

    public void waitForAllElementsVisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, GlobalConstant.longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(locator)));
    }

    public void waitForAllElementsVisible(WebDriver driver, String locator, String... params) {
        explicitWait = new WebDriverWait(driver, GlobalConstant.longTimeout);
        explicitWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(getDynamicLocator(locator, params))));
    }

    public void waitForElementClickable(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, GlobalConstant.longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
    }

    public void waitForElementClickable(WebDriver driver, String locator, String... params) {
        explicitWait = new WebDriverWait(driver, GlobalConstant.longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, params))));
    }

    public void waitForElementInvisibale(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, GlobalConstant.longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
    }

    public void waitForElementInvisibale(WebDriver driver, String locator, String... params) {
        explicitWait = new WebDriverWait(driver, GlobalConstant.longTimeout);
        explicitWait
                .until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(locator, params))));
    }

    public void overrideGlobalTimeout(WebDriver driver, int timeOut) {
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }

    public String getDynamicLocator(String locator, String... params) {
        return String.format(locator, (Object[]) params);
    }


    public void sleepInSecond(long timeoutInSecond) {
        try {
            Thread.sleep(timeoutInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private Alert alert;
    private Select select;
    private WebDriverWait explicitWait;
    private JavascriptExecutor jsExecutor;
    private Actions action;

}
