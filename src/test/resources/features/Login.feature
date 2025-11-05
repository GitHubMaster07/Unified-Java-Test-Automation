Feature: User Login Functionality

  As a user of the web application,
  I want to be able to log in successfully,
  So that I can access my secure areas.

  # @ui tag for TestNG grouping, @smoke for critical path runs
  @ui @smoke
  Scenario: Successful Login with Valid Credentials
    Given the user is on the Login page
    When the user enters the username "tomsmith" and password "SuperSecretPassword!"
    And the user clicks the Login button
    Then the user should be redirected to the secure area