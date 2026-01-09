# Integration Level: UI-Driven Acceptance Testing
# Business Domain: User Access Management
Feature: User Login Functionality

  As a user of the web application,
  I want to be able to log in successfully,
  So that I can access my secure areas.

  # @ui: Targets the Browser Automation layer
  # @smoke: Critical path validation for CI/CD gated deployments
  @ui @smoke
  Scenario: Successful Login with Valid Credentials
    # Prerequisite: Environment is initialized via Hooks to the 'base.url'
    Given the user is on the Login page
    
    # Action: Identity and Access Management (IAM) transaction
    When the user enters the username "tomsmith" and password "SuperSecretPassword!"
    And the user clicks the Login button
    
    # Validation: Confirming authorization and state transition to the Secure Zone
    Then the user should be redirected to the secure area
