@cloudGreyAppium
Feature: Demo feature Test for Cloud Grey Android application
  #Sample Test Scenario Description

  @cloudGreyAppiumLoginScreen
  Scenario: Validate Login Screen in Android Cloud Grey App
    Given I launched the app
    Then I click on Login Screen Button
    When I tried to login with username "dummyUser" and password "dummyPassword"
    Then I should see Error page