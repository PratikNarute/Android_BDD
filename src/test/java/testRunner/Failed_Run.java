package testRunner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"@target/Failed.txt"},
        glue = "stepDefination",
        dryRun = false,
        monochrome = true,
        plugin = {"pretty",
//                "html:test-output/cucumber-reports/html-report.html",
//                "json:test-output/cucumber-reports/json-report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        }
)
public class Failed_Run extends AbstractTestNGCucumberTests {


}
