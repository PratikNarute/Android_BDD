package pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;
import utility.AppiumDriverSetup;
import utility.ReadProperty;

import java.io.IOException;

import static utility.PerformActions.*;

public class Top_Up extends AppiumDriverSetup {
    SoftAssert sa;
     public Top_Up() throws IOException {
         if(ReadProperty.getPropertiesData("OS").contains("IOS")){
             PageFactory.initElements(IOSdriver,this);
         }else{
             PageFactory.initElements(androidDriver,this);
         }
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

     public void validate_top_up_flow_with_adding_only_top_up(String country) throws InterruptedException, IOException {
         Thread.sleep(10000);
         click_action(topUpNow_button, "'Top up now' button is missing: ");
         Thread.sleep(3000);
         navigateBack();
     }

}


