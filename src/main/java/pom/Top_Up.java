package pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;
import utility.AppiumDriverSetup;
import static utility.PerformActions.click_action;
import static utility.PerformActions.send_action;

public class Top_Up extends AppiumDriverSetup {
    SoftAssert sa;
     public Top_Up(){
         PageFactory.initElements(driver, this);
         sa = new SoftAssert();
     }

     @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test:id/topupNow-Button\"]") WebElement topUpNow_button;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;
//     @FindBy(xpath = "") WebElement dkfd;

     public void validate_top_up_flow_with_adding_only_top_up(String country) throws InterruptedException {
         Thread.sleep(10000);
         click_action(topUpNow_button, "'Top up now' button is missing: ");
         Thread.sleep(4000);
         driver.navigate().back();
         Thread.sleep(4000);
     }

}


