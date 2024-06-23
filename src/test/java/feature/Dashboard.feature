@Dashboard
Feature: Quick Top Up
  As a user,
  I want to see all my account details information in shortcut and understandable graph format,
  So I can effectively analysis my account details.

  Background:
    Given user has already registered person and navigate on login page
    When enter Mobile No or Username
    And enter OTP or Password
    Then login should be successfully
    And user should be navigate on the dashboard of my account page

    @UK @Regression
    Scenario: UK-Verify sections of dashboard page
      Given user should be login successfully "UK"
      And user should be navigate on the dashboard of my account page
      When verify top up section
      Then top up balance and top up button should be visible
      And redeem now functionality should be visible
      When verify buy plan section
      When verify quick view section
      When verify add data plans section
      When verify set up services section
      When verify shortcuts section
      Then shortcuts section related fields should be visible "UK"
      When verify help and support section

  @AT @Regression
  Scenario: AT-Verify sections of dashboard page
    Given user should be login successfully "AT"
    And user should be navigate on the dashboard of my account page
    When verify top up section
    Then top up balance and top up button should be visible
    And redeem now functionality should be visible
    When verify buy plan section
    When verify quick view section
    When verify add data plans section
    When verify set up services section
    When verify shortcuts section
    Then shortcuts section related fields should be visible "AT"
    When verify help and support section