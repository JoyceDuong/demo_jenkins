package testcases;

import commons.BaseTest;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.BuyPackageObject;
import pageObjects.LoginPageObject;
import pageObjects.PageGeneratorManager;
import utilities.ExcelConfig;
import utilities.FakerUtil;


public class DemoClass extends BaseTest {
    public WebDriver driver;
    String email, password, option, addType,dirOption,companyAdminEmail;
    String[] cartItems = {"ACRA Fee - Registration Fee and Name Application","Incorporation Assistance Fee","Corporate Secretary\n" +
            "(2 Shareholders)","ACRA Annual Filing Fee","Registered Address and Digital Mailroom - 1 Year","Nominee Director - 1 Year","Nominee Director Security Deposit"};

    @Parameters({"browserName", "appUrl"})
    @BeforeClass
    public void beforeClass(String browserName, String appUrl) throws Exception {

        email = "joyce.duong@sleek.com";
        password = "Minhhieu@123";
        option = "Incorporate a new company in Singapore";
        addType = "Registered Address & Digital Mailroom Service";
        dirOption = "Add Nominee Director";
        excel = new ExcelConfig();
        fake = new FakerUtil();
        companyAdminEmail = fake.getEmail();
        System.out.println("email khởi tạo là" + companyAdminEmail);
        excel.setExcelFile(getProjectpath() + "/src/test/resources/CompanyAdminInfor.xlsx","AdminInfor");


        log.info("Opening " + browserName + " Browser");
        driver = BaseTest.getBrowserDriver(browserName, appUrl) ;
        loginPage = PageGeneratorManager.getLoginPage(driver);


    }

    @Test
    public void TC_01_Login_To_Partner_App() {
        log.info("Login 01: Enter "+ email + "to email textbox");
        loginPage.enterEmailTextBox(email);

        log.info("Login 02: Enter "+ password + " to password textbox");
        loginPage.enterPasswordTextBox(password);

        log.info("Login 03: Click to Login Button");
        buyPackagePage = loginPage.clickToLoginButton();

    }
    @Test
    public void TC_02_Buy_Package() throws Exception{
        log.info("Buy_Package 01: Click to Buy Button");
        buyPackagePage.clickToBuyButton();

        log.info("Buy_Package 02: Select " +option+ "Service");
        buyPackagePage.selectServiceByServiceName("Incorporate a new company in Singapore");

        log.info("Buy_Package 03: Click to Start Now button");
        buyPackagePage.clickToStartNowButton();

        log.info("Buy_Package 04: Select company address type is" + addType);
        buyPackagePage.selectCompanyAddType(addType);


        log.info("Buy_Package 05: Click on Next button");
        buyPackagePage.clickonNextButton();

        log.info("Buy_Package 06: Select Number of shareholder");
        buyPackagePage.selectShareholderNumber("2");

        log.info("Buy_Package 07: Click on Next button");
        buyPackagePage.clickonNextButton();

        log.info("Buy_Package 08: Click on Next button");
        verifyTrue(buyPackagePage.getToastedMessage());

        log.info("Buy_Package 09: Select "+dirOption+ " as Director ");
        buyPackagePage.selectDirectorOptionByText(dirOption);

        log.info("Buy_Package 10: Click on Next button");
        buyPackagePage.clickonNextButton();

        log.info("Buy_Package 11: Click on Cart");
        buyPackagePage.clickOnCart();


        log.info("Buy_Package 12: Verify List items in cart");
        Assert.assertTrue(buyPackagePage.isListItemDisplayed(cartItems));

        log.info("Buy_Package 13: Click on Cart");
        buyPackagePage.clickOnCart();

        log.info("Buy_Package 14: Skip Accounting Step");
        buyPackagePage.clickOnSkipOption();

        log.info("Buy_Package 15: Click on Confirm button ");
        buyPackagePage.clickOnButtonByText("Confirm");



        log.info("Buy_Package 16: Fill out company admin from excel file ");
        buyPackagePage.fillOutCompanyAdmin(companyAdminEmail,excel.getCellData("firstName",1),excel.getCellData("lastName",1),"AUTOPARTNER-Joyce " + getRandomNumber());

//        log.info("Buy_Package 17: Click on Send Invitation button ");
//        buyPackagePage.clickOnButtonByText("Send Invite");
//
//        sleepInSecond(20);
//
//        String lastestID = getLastestInboxID(companyAdminEmail);
//        System.out.println(lastestID);
//        String link = getLink(lastestID,excel.getCellData("lastName",1));
//        System.out.println(link);





    }




    @AfterClass
    public void afterClass() {
        driver.quit();
    }



    private BuyPackageObject buyPackagePage;
    private LoginPageObject loginPage;
    private ExcelConfig excel;
    private FakerUtil fake;

}
