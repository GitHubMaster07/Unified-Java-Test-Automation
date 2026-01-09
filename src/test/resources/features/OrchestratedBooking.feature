@regression @e2e
Feature: Unified Booking Orchestration

  Scenario: Create booking via API and verify across UI and Database
    # Step 1: API Layer (State Injection)
    Given I create a new booking via API with name "Sergei" and price 150

    # Step 2: UI Layer (Frontend Validation)
    And I navigate to the booking management dashboard
    Then I should see the booking for "Sergei" in the list

    # Step 3: DB Layer (Data Integrity Validation)
    And The database should contain a record for "Sergei" with price 150