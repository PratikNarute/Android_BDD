package pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utility.AppiumDriverSetup;
import utility.ReadProperty;

import java.io.IOException;
import java.util.Arrays;

import static utility.PerformActions.click_action;
import static utility.PerformActions.send_action;
import static utility.PerformActions.isElementDisplayed;
import static utility.PerformActions.isElementEnabled;
import static utility.PerformActions.scrollToElement_Downward;
import static utility.PerformActions.scrollToElement_Upward;


public class Dashboard extends AppiumDriverSetup {

    SoftAssert sa;
    public Dashboard() throws IOException {
        if(ReadProperty.getPropertiesData("OS").contains("IOS")){
            PageFactory.initElements(IOSdriver,this);
        }else{
            PageFactory.initElements(androidDriver,this);
        }
        sa = new SoftAssert();

    }

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test:id/topupNow-Button\"]")
    WebElement topUpNow_button;
    @FindBy(xpath = "//android.widget.ScrollView[@content-desc=\"test:id/dashboard-scrollview\"]/android.view.ViewGroup/android.view.ViewGroup[1]") WebElement topUpSectionDashboard_box;
    @FindBy(xpath = "//android.widget.TextView[@text=\"Redeem now\"]") WebElement redeemNow_textButton;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"test:id/call&dataHistory-text\"]") WebElement shortcuts_callDataHistory_textButton;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"test:id/statement-text\"]") WebElement shortcuts_Statements_textButton;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"test:id/savedCards-text\"]") WebElement shortcuts_SavedCards_textButton;


    Top_Up tp;
    public void validate_top_up_section_on_dashboard() throws InterruptedException, IOException {
        Thread.sleep(10000);
        sa.assertTrue(isElementEnabled(topUpNow_button), "'Top Up now' button is not enabled to clickable: ");
        sa.assertTrue(isElementDisplayed(topUpSectionDashboard_box), "'Top up section box' is not displayed: ");
        sa.assertTrue(isElementEnabled(redeemNow_textButton), "'Redeemm now' text button is not enable to clickable: ");
        sa.assertAll();

    }
    public void validate_shortcuts_section_on_dashboard() throws InterruptedException, IOException {
        scrollToElement_Downward(shortcuts_callDataHistory_textButton, "Scrolling failed to 'shortcuts_callDataHistory' button: ");
        sa.assertTrue(isElementEnabled(shortcuts_callDataHistory_textButton), "'Call and History' text button is not enable to clickable: ");
        sa.assertTrue(isElementEnabled(shortcuts_Statements_textButton), "'Statement' text button is not enable to clickable: ");
        sa.assertTrue(isElementEnabled(shortcuts_SavedCards_textButton), "'Saved Cards' text button is not enable to clickable: ");
        scrollToElement_Upward(topUpNow_button, "Scrolling failed to 'Top Up Now' button: ");

        sa.assertAll();
    }


}
