package commons;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class BaseTest {


    private static WebDriver driver;
    protected final Logger log;
    private String urllink;

    protected BaseTest() {
        log = LogManager.getLogger(getClass());
    }

    public WebDriver getDriver() {
        return driver;
    }

    private enum BROWSER {
        CHROME, FIREFOX, IE, SAFARI, EDGE_CHROMIUM, EDGE_LEGACY, H_CHROME, H_FIREFOX
    }

    public static WebDriver getBrowserDriver() {
        return driver;
    }

    public static WebDriver getBrowserDriver(String browserName, String appUrl) {
        BROWSER browser = BROWSER.valueOf(browserName.toUpperCase());
        if (browser == BROWSER.FIREFOX) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser == BROWSER.CHROME) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser == BROWSER.EDGE_CHROMIUM) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException("Please enter correct browser name!");
        }
        driver.get(appUrl);
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);

        //Resize the current window to the given dimension
        driver.manage().window().maximize();
        return driver;
    }

    @AfterMethod
    public void takeScreenshot(ITestResult result) {

        //Kh???i t???o ?????i t?????ng result thu???c ITestResult ????? l???y tr???ng th??i v?? t??n c???a t???ng Test Case
        //??? ????y s??? so s??nh ??i???u ki???n n???u testcase passed ho???c failed
        //passed = SUCCESS v?? failed = FAILURE
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                // T???o tham chi???u c???a TakesScreenshot
                TakesScreenshot ts = (TakesScreenshot) driver;
                // G???i h??m capture screenshot - getScreenshotAs
                File source = ts.getScreenshotAs(OutputType.FILE);
                //Ki???m tra folder t???n t???i. N??u kh??ng th?? t???o m???i folder
                File theDir = new File("./Screenshots/");
                if (!theDir.exists()) {
                    theDir.mkdirs();
                }
                // result.getName() l???y t??n c???a test case xong g??n cho t??n File ch???p m??n h??nh
                FileHandler.copy(source, new File("./Screenshots/" + result.getName() + ".png"));
                System.out.println("???? ch???p m??n h??nh: " + result.getName());
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        }
    }

    private boolean checkTrue(boolean condition) {
        boolean pass = true;
        try {
            if (condition == true) {
                log.info(" -------------------------- PASSED -------------------------- ");
            } else {
                log.info(" -------------------------- FAILED -------------------------- ");
            }
            Assert.assertTrue(condition);
        } catch (Throwable e) {
            pass = false;
        }
        return pass;
    }

    protected boolean verifyTrue(boolean condition) {
        return checkTrue(condition);
    }

    private boolean checkFailed(boolean condition) {
        boolean pass = true;
        try {
            if (condition == false) {
                log.info(" -------------------------- PASSED -------------------------- ");
            } else {
                log.info(" -------------------------- FAILED -------------------------- ");
            }
            Assert.assertFalse(condition);
        } catch (Throwable e) {
            pass = false;
        }
        return pass;
    }

    protected boolean verifyFalse(boolean condition) {
        return checkFailed(condition);
    }

    private boolean checkEquals(Object actual, Object expected) {
        boolean pass = true;
        try {
            Assert.assertEquals(actual, expected);
            log.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            pass = false;
            log.info(" -------------------------- FAILED -------------------------- ");
        }
        return pass;
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        return checkEquals(actual, expected);
    }

    protected void cleanBrowserAndDriver() {
        driver.quit();
    }





    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(999);
    }

    public String getProjectpath() {
        return System.getProperty("user.dir");


    }
    public void sleepInSecond(long timeoutInSecond) {
        try {
            Thread.sleep(timeoutInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getLastestInboxID(String email) {
        System.out.println("Email l??:" + email);

        RestAssured.baseURI = "https://sleek-mailer-ts-staging.sleek.sg/api/";
        RestAssured.basePath = "inbox/";



        Response response = given().header("Authorization", "ad09f7f9-70c9-4a95-b1d1-3ef4f25a93f1").header("Content-Type", "application/json").get(email);
         ArrayList<Map<String,String>> mai = (ArrayList<Map<String, String>>) response.as(ArrayList.class);
          return mai.get(0).get("id");


//        Map<String, String> mailObj = (Map<String, String>) response.get(0);
//        String id = mailObj.get("id");
//        return id;
    }

    public String getLink(String inboxID, String lastName){
        String urllink= "";
        RestAssured.baseURI = "https://sleek-mailer-ts-staging.sleek.sg/api/";
        RestAssured.basePath = "message/";


        Object response = given().header("Authorization", "ad09f7f9-70c9-4a95-b1d1-3ef4f25a93f1").header("Content-Type", "application/json").get(inboxID).as(Object.class);
        Map<String, String> res = (Map<String, String>) response;

        String htmlBody = res.get("html_body");
        System.out.println(htmlBody);


        Pattern pattern = Pattern.compile("(((https?:\\/\\/)|(www\\.))[^\\s]+)");

        Matcher link = pattern.matcher(htmlBody);

        ArrayList<String> listLink = new ArrayList<>();
        while (link.find()) {
            listLink.add(link.group());
        }
        System.out.println("List link Size:" + listLink.size());

        for (int i = 0; i < listLink.size();i++){
            if (listLink.get(i).contains(lastName)){
                String rawLink = listLink.get(i).replace("\\\"","");
                urllink = rawLink.replace("amp;","");
                break;

            }
        }
        return urllink;

    }
}