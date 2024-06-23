@Login
  Feature: Login
    As I user,
  I have to login successfully
  so, I can access my account

    @UK @Sanity @Regression
  Scenario: UK-Verify login scenarios with valid credentials
    Given user has already registered person and navigate on login page
    When enter valid Mobile No or Username
    And enter valid OTP
    Then login should be successfully done with valid OTP "UK"
    And user should be navigate on the dashboard of my account page
    When verify logout scenarios
    Then logout should be done and navigate on login page "UK"

    @AT @Sanity @Regression
    Scenario: AT-Verify login scenarios with valid credentials
      Given user has already registered person and navigate on login page
      When enter valid Mobile No or Username
      And enter valid OTP
      Then login should be successfully done with valid OTP "AT"
      And user should be navigate on the dashboard of my account page
      When verify logout scenarios
      Then logout should be done and navigate on login page "AT"


