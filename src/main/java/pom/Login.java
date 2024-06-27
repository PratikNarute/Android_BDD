package pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utility.AppiumDriverSetup;
import utility.ReadProperty;

import java.io.IOException;

import static org.testng.Assert.assertTrue;
import static utility.PerformActions.*;
import static utility.ReadProperty.readCountryDataFromExcel;


public class Login extends AppiumDriverSetup {

    SoftAssert sa;

    @FindBy(xpath = "//android.widget.Button[@content-desc=\"Skip\"]")
    WebElement skip_button;
    @FindBy(xpath = "//android.widget.Button[3]")
    WebElement continueToLogin_button;
    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"test:id/Login-text_field\"] | //XCUIElementTypeTextField[@name=\"test:id/Login-text_field\"]")
    WebElement mobileNO_inputTab;
    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"test:id/otp-code-field\"]")
    WebElement OTP1_inputTab;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"test:id/otp_confirm_text\"]/parent::android.view.ViewGroup")
    WebElement confirmOTP_button;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"test:id/login_button_text\"]/parent::android.view.ViewGroup")
    WebElement login_Button;
    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test:id/house-keeping-button\"]/android.view.ViewGroup/com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[2]")
    WebElement letsHaveLook_arrowButton;
    @FindBy(xpath = "//com.horcrux.svg.CircleView")
    WebElement termCondition_toggle;
    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test:id/Button\"]")
    WebElement continue_button;
    @FindBy(xpath = "//android.widget.ImageView")
    WebElement info_image;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"test:id/dash-menu\"]")
    WebElement menuListFooter_iconButton;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"test:id/logout-text\"]")
    WebElement menuList_logout_button;
    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test:id/yesLeaveTheApp-Button\"]")
    WebElement popUp_yesLogout_button;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"test:id/logout-nokeepMeLoggedIn\"]")
    WebElement popUp_noLogout_button;
    @FindBy(xpath = "//android.view.View[@bounds='[1236,201][1320,285]']")
    private WebElement refresh_button;
    @FindBy(xpath = "//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")
    private WebElement allow_button;
    @FindBy(xpath = "//android.widget.TextView[@text=\"English\"] | //XCUIElementTypeOther[@name=\"English\"] | //XCUIElementTypeOther[@name=\"English\"]/following-sibling::XCUIElementTypeOther")
    WebElement english_button;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"test:id/errorMessage-text\"]")WebElement invalidMobileNo_errorMessage;


    public Login() throws IOException {
        if(ReadProperty.getPropertiesData("OS").contains("IOS")){
            PageFactory.initElements(IOSdriver,this);
        }else{
            PageFactory.initElements(androidDriver,this);
        }
        sa = new SoftAssert();
    }

    public void validate_login_pages_with_valid_credentials(String country) throws Exception {
        Thread.sleep(6000);
        String mobileNO = readCountryDataFromExcel(country, "MOBILE_NO_1");
        String OTP = readCountryDataFromExcel(country, "OTP");

        validate_language_page(country);
        send_action(mobileNO_inputTab, mobileNO, "Mobile No input tab is not displayed: ");
        click_action(login_Button, "Login button is not enable to clickable: ");
        Assert.assertFalse(isElementDisplayed(invalidMobileNo_errorMessage), "Invalid Mobile Number for"+country+"-"+mobileNO);
        send_action(OTP1_inputTab, OTP, "'OTP' input tab is not displayed: ");
        click_action(confirmOTP_button, "'Confirm' button is not enable to clickable");
        click_action(letsHaveLook_arrowButton, "'Lets have look' page is not opened: ");
        click_action(termCondition_toggle, "'Term and Condition' toggle is not displayed: ");
        click_action(continue_button, "'Continue' button is missing");
        click_action(info_image, "'Information or Guidance' page is not opened: ");
    }

    public void validate_logout_scenarios(String country) throws InterruptedException, IOException {
        click_action(menuListFooter_iconButton, "'Menu List Footer' button is missing: ");
        scrollToElement_Downward(menuList_logout_button, "Does not scroll to 'Menu List-Logout' button: ");
        click_action(menuList_logout_button, "'MenuList_Logout' button is missing: ");
        sa.assertTrue(isElementEnabled(popUp_noLogout_button), "'popUp_noLogout_button' is not enable to clickable: ");
        click_action(popUp_yesLogout_button, "'popUp_yesLogout_button' is missing: ");
        sa.assertAll();
    }

    public void validate_language_page(String country) throws InterruptedException, IOException {
        if (!country.equalsIgnoreCase("UK")) {
            Assert.assertTrue(isElementDisplayed(english_button), "App is not Lunched/Installed");
            click_action(english_button, "'English' button is missing: ");
        }
    }


}
