package pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import io.appium.java_client.android.AndroidDriver;
import utility.AppiumDriverSetup;

import static utility.PerformActions.click_action;
import static utility.PerformActions.send_action;
import static utility.PerformActions.scrollToElement_Downward;
import static utility.PerformActions.scrollToElement_Upward;
import static utility.PerformActions.isElementEnabled;
import static utility.PerformActions.isElementDisplayed;


public class Login extends AppiumDriverSetup {

	SoftAssert sa;
		
	    @FindBy (xpath = "//android.widget.Button[@content-desc=\"Skip\"]") WebElement skip_button;
		@FindBy (xpath = "//android.widget.Button[3]") WebElement continueToLogin_button;
		@FindBy (xpath = "//android.widget.EditText[@content-desc=\"test:id/Login-text_field\"]") WebElement mobileNO_inputTab;
		@FindBy(xpath = "//android.widget.EditText[@content-desc=\"test:id/otp-code-field\"]")WebElement OTP1_inputTab;
		@FindBy (xpath = "//android.widget.TextView[@content-desc=\"test:id/otp_confirm_text\"]/parent::android.view.ViewGroup") WebElement confirmOTP_button;
		@FindBy (xpath = "//android.widget.TextView[@content-desc=\"test:id/login_button_text\"]/parent::android.view.ViewGroup") WebElement login_Button;
		@FindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test:id/house-keeping-button\"]/android.view.ViewGroup/com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[2]")WebElement letsHaveLook_arrowButton;
		@FindBy (xpath = "//com.horcrux.svg.CircleView")WebElement termCondition_toggle;
		@FindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test:id/Button\"]")WebElement continue_button;
		@FindBy (xpath = "//android.widget.ImageView")WebElement info_image;
		@FindBy (xpath = "//android.widget.TextView[@content-desc=\"test:id/dash-menu\"]")WebElement menuListFooter_iconButton;
		@FindBy (xpath = "//android.widget.TextView[@content-desc=\"test:id/logout-text\"]")WebElement menuList_logout_button;
		@FindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test:id/yesLeaveTheApp-Button\"]")WebElement popUp_yesLogout_button;
		@FindBy (xpath = "//android.widget.TextView[@content-desc=\"test:id/logout-nokeepMeLoggedIn\"]") WebElement popUp_noLogout_button ;
		@FindBy (xpath = "//android.view.View[@bounds='[1236,201][1320,285]']")private WebElement refresh_button;
		@FindBy (xpath = "//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")private WebElement allow_button;
		@FindBy (xpath = "//android.widget.TextView[@text=\"English\"]") WebElement english_button;

		
		


		public Login()
		{
			PageFactory.initElements(driver, this);
			sa = new SoftAssert();
		}

		public void validate_login_pages_with_valid_credentials(String country) throws InterruptedException {
			Thread.sleep(6000);
			if(!country.equalsIgnoreCase("UK")){
				validate_language_page(country);
			}

			if(country.equalsIgnoreCase("UK")){
				send_action(mobileNO_inputTab, "7405419580");
			}else if(country.equalsIgnoreCase("AT")){
				send_action(mobileNO_inputTab, "68864328378");
			}

			click_action(login_Button);
			Thread.sleep(3000);
			send_action(OTP1_inputTab, "5678");
			click_action(confirmOTP_button);
			if(isElementDisplayed(allow_button)){
				click_action(allow_button);
			}
			click_action(letsHaveLook_arrowButton);
			click_action(termCondition_toggle);
			click_action(continue_button);
			click_action(info_image);
		}

		public void validate_logout_scenarios(String country) throws InterruptedException {
			click_action(menuListFooter_iconButton);
			scrollToElement_Downward(menuList_logout_button);
			click_action(menuList_logout_button);
			isElementEnabled(popUp_yesLogout_button);
			isElementEnabled(popUp_noLogout_button);
			click_action(popUp_yesLogout_button);

		}
		public void validate_language_page(String country) throws InterruptedException {
			if (country.equalsIgnoreCase("AT")) {
				click_action(english_button);

			}
		}


}
