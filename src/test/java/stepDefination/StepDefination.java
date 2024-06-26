package stepDefination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;

import static stepDefination.BaseClass.*;

public class StepDefination {

    @Given("user has already registered person and navigate on login page")
    public void user_has_already_registered_person_and_navigate_on_login_page() {
    }
    @When("enter Mobile No or Username")
    public void enter_mobile_no_or_username() {
    }
    @When("enter OTP or Password")
    public void enter_otp_or_password() {
    }
    @Then("user should be navigate on the dashboard of my account page")
    public void user_should_be_navigate_on_the_dashboard_of_my_account_page() {
    }
    @Then("login should be successfully")
    public void login_should_be_successfully() throws InterruptedException {
    }
    @When("click on top up for you menu list")
    public void click_on_top_up_for_you_menu_list() {
    }
    @Given("user should be login already")
    public void user_should_be_login_already() {
    }
    @When("click on Top Up button")
    public void click_on_top_up_button() {
    }
    @Then("user should be navigate on Top Up page")
    public void user_should_be_navigate_on_top_up_page() {
    }
    @When("select randomly Top Up")
    public void select_randomly_top_up() {
    }
    @When("select saved card and enter cvv details")
    public void select_saved_card_and_enter_cvv_details() {
    }
    @When("if saved card is not available then add new card and enter card details")
    public void if_saved_card_is_not_available_then_add_new_card_and_enter_card_details() {
    }
    @When("ON toggle of term condition")
    public void on_toggle_of_term_condition() {
    }
    @When("click on on confirm payment method")
    public void click_on_on_confirm_payment_method() {
    }
    @Then("navigate on 3D page")
    public void navigate_on_3d_page() {
    }
    @And("only Top Up should be added successfuly {string}")
    public void onlyTopUpShouldBeAddedSuccessfuly(String country) throws InterruptedException {
        tp.validate_top_up_flow_with_adding_only_top_up(country);
    }
    @When("navigate on the dashboard of my account page")
    public void navigate_on_the_dashboard_of_my_account_page() {
    }
    @When("select randomly Plan")
    public void select_randomly_plan() {
    }
    @Then("only plan should be added successfuly")
    public void only_plan_should_be_added_successfuly() {
    }
    @Then("Top Up and plan should be added successfuly")
    public void top_up_and_plan_should_be_added_successfuly() {
    }
    @Given("user should be login successfully {string}")
    public void userShouldBeLoginSuccessfully(String country) throws Exception {
        lg.validate_login_pages_with_valid_credentials(country);

    }

    @And("click on redeem no button")
    public void clickOnRedeemNoButton() {
    }

    @And("verify user able to do top up with invalid voucher code")
    public void verifyUserAbleToDoTopUpWithInvalidVoucherCode() {
    }

    @Then("user should not be able to do top up with invalid voucher code {string}")
    public void userShouldNotBeAbleToDoTopUpWithInvalidVoucherCode(String country) {
    }

    @When("verify top up section")
    public void verify_top_up_section() {
    }
    @Then("top up balance and top up button should be visible")
    public void top_up_balance_and_top_up_button_should_be_visible() throws InterruptedException {
       ds.validate_top_up_section_on_dashboard();
    }
    @Then("redeem now functionality should be visible")
    public void redeem_now_functionality_should_be_visible() {
    }
    @When("verify buy plan section")
    public void verify_buy_plan_section() {
    }
    @When("verify quick view secion")
    public void verify_quick_view_secion() {
    }
    @When("verify add data plans section")
    public void verify_add_data_plans_section() {
    }
    @When("verify set up services section")
    public void verify_set_up_services_section() {
    }
    @When("verify shortcuts section")
    public void verify_shortcuts_section() {
    }
    @When("verify help and support section")
    public void verify_help_and_support_section() {
    }
    @When("verify quick view section")
    public void verifyQuickViewSection() {
    }

    @Then("shortcuts section related fields should be visible {string}")
    public void shortcutsSectionRelatedFieldsShouldBeVisible(String country) throws InterruptedException, IOException {
        ds.validate_shortcuts_section_on_dashboard();
    }
    @When("enter valid Mobile No or Username")
    public void enter_valid_mobile_no_or_username() {
    }
    @When("enter valid OTP")
    public void enter_valid_otp() {

    }
    @Then("login should be successfully done with valid OTP {string}")
    public void login_should_be_successfully_done_with_valid_otp(String country) throws Exception {
        lg.validate_login_pages_with_valid_credentials(country);
    }
    @When("verify logout scenarios")
    public void verify_logout_scenarios() {

    }
    @Then("logout should be done and navigate on login page {string}")
    public void logout_should_be_done_and_navigate_on_login_page(String country) throws InterruptedException, IOException {
        lg.validate_logout_scenarios(country);
    }

}
