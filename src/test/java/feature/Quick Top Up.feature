@QuickTop
Feature: Quick Top Up
  As a user,
  I want to be able to top up my account balance,
  So that I can have sufficient funds to use the service.

  Out of Scope:
  - Integration with third-party payment processors.
  - Handling of failed payment transactions.


  Background:
    Given user has already registered person and navigate on login page
    When enter Mobile No or Username
    And enter OTP or Password
    Then login should be successfully
    And user should be navigate on the dashboard of my account page

  @UK @Sanity @Regression
  Scenario: UK-Verify top up flow
    Given user should be login successfully "UK"
    And user should be navigate on the dashboard of my account page
    When click on Top Up button
    Then user should be navigate on Top Up page
    When select randomly Top Up
    And select saved card and enter cvv details
    And if saved card is not available then add new card and enter card details
    And ON toggle of term condition
    And click on on confirm payment method
    Then navigate on 3D page
    And only Top Up should be added successfuly "UK"

    When navigate on the dashboard of my account page
    And click on Top Up button
    Then user should be navigate on Top Up page
    When select randomly Plan
    And select saved card and enter cvv details
    And if saved card is not available then add new card and enter card details
    And ON toggle of term condition
    And click on on confirm payment method
    Then navigate on 3D page
    And only plan should be added successfuly

    When navigate on the dashboard of my account page
    And click on Top Up button
    Then user should be navigate on Top Up page
    When select randomly Top Up
    And select randomly Plan
    And select saved card and enter cvv details
    And if saved card is not available then add new card and enter card details
    And ON toggle of term condition
    And click on on confirm payment method
    Then navigate on 3D page
    And Top Up and plan should be added successfuly

    When navigate on the dashboard of my account page
    And click on redeem no button
    And verify user able to do top up with invalid voucher code
    Then user should not be able to do top up with invalid voucher code "UK"


  @AT @Sanity @Regression
  Scenario: AT-Verify top up flow
    Given user should be login successfully "AT"
    And user should be navigate on the dashboard of my account page
    When click on Top Up button
    Then user should be navigate on Top Up page
    When select randomly Top Up
    And select saved card and enter cvv details
    And if saved card is not available then add new card and enter card details
    And ON toggle of term condition
    And click on on confirm payment method
    Then navigate on 3D page
    And only Top Up should be added successfuly "AT"

    When navigate on the dashboard of my account page
    And click on Top Up button
    Then user should be navigate on Top Up page
    When select randomly Plan
    And select saved card and enter cvv details
    And if saved card is not available then add new card and enter card details
    And ON toggle of term condition
    And click on on confirm payment method
    Then navigate on 3D page
    And only plan should be added successfuly

    When navigate on the dashboard of my account page
    And click on Top Up button
    Then user should be navigate on Top Up page
    When select randomly Top Up
    And select randomly Plan
    And select saved card and enter cvv details
    And if saved card is not available then add new card and enter card details
    And ON toggle of term condition
    And click on on confirm payment method
    Then navigate on 3D page
    And Top Up and plan should be added successfuly

    When navigate on the dashboard of my account page
    And click on redeem no button
    And verify user able to do top up with invalid voucher code
    Then user should not be able to do top up with invalid voucher code "AT"
















