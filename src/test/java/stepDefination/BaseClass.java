package stepDefination;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.*;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;
import pom.Dashboard;
import pom.Login;
import pom.Top_Up;
import testRunner.Sanity_Run;
import utility.AppiumDriverSetup;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.cucumber.java.*;
import utility.ReadProperty;


public class BaseClass {
    public static ExtentReports report;
    public static ExtentTest test;
    public static ExtentSparkReporter spark;
    public static Login lg;
    public static Top_Up tp;
    public static Dashboard ds;
    public SoftAssert sa;
    public static AndroidDriver driver;

    public void POM_Class_objects() throws IOException {
        lg = new Login();
        tp = new Top_Up();
        ds = new Dashboard();
        sa = new SoftAssert();
    }
    @Before
    public void lunchBrowser(Scenario scenario) throws IOException {
       String [] arrayContryName= scenario.getName().split("-");
       String countryName = arrayContryName[0];
       String appPackageName = getAppPackageName(countryName);
        System.out.println("Start execution on platform: "+ ReadProperty.getPropertiesData("Platform_Execution"));
        System.out.println("Start execution for App Package: "+appPackageName);

        AppiumDriverSetup.startAppiumServer();
        AppiumDriverSetup.lunchAndroidDriver(appPackageName, countryName);
        driver = AppiumDriverSetup.driver;

        // Set name scnario name for lambda execution
        if(ReadProperty.getPropertiesData("Platform_Execution").equalsIgnoreCase("Lambda_Cloud")){
            // Set name of scenario in Lambda configuration
            driver.executeScript("lambda-name=" + scenario.getName());
        }
        POM_Class_objects();
    }

    @BeforeStep
    public void applyImplicitlyWait(Scenario scenario) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterStep
    public void attachReport(Scenario scenario) throws IOException, InterruptedException {

        // Take screenshot and attach to Extent report
        if(scenario.isFailed())
        {
            if(scenario.isFailed()) {
                File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
                scenario.attach(fileContent, "image/png", "screenshot");
            }
        }
    }
    @After
    public void closeBrowser(Scenario scenario) throws InterruptedException, IOException {
        Thread.sleep(2000);

        if(ReadProperty.getPropertiesData("Platform_Execution").equalsIgnoreCase("Lambda_Cloud")){
            // Set the status like pass or failed in lambda configuration
            driver.executeScript("lambda-status=" + (scenario.isFailed() ? "failed" : "passed"));
            System.out.println(driver.getSessionId());
        }

        driver.quit();
        System.out.println("Driver closed: ");
        AppiumDriverSetup.stopAppiumLocalServer();
        System.out.println("Appium Local Server: "+AppiumDriverSetup.stopAppiumLocalServer());

    }

    public String getAppPackageName(String country){
        String appPackageName = "";
        if(country.equalsIgnoreCase("UK")){
            appPackageName = "com.lycadigital.lyca_mobile_uk";
        }else if (country.equalsIgnoreCase("AT")){
            appPackageName = "com.lycamobile.at";
        }
        System.out.println("App package Name: "+appPackageName);
        return appPackageName;
    }



}
