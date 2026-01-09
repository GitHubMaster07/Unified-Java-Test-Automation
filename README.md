# 🏗️ Unified Java Test Automation Engine
### **Enterprise-Level Hybrid Orchestration Framework**

[![Test Automation Pipeline](https://github.com/GitHubMaster07/Unified-Java-Test-Automation/actions/workflows/ci.yml/badge.svg)](https://github.com/GitHubMaster07/Unified-Java-Test-Automation/actions/workflows/ci.yml)
![Build: Passing](https://img.shields.io/badge/build-passing-brightgreen.svg)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)
![Java: 11](https://img.shields.io/badge/Java-11-blue.svg)

---

## 💎 Overview & Vision
**Unified-Java-Test-Automation** is a high-performance engine implementing the **Unified Orchestration** concept. Designed for high-scalability testing, it provides a seamless integration layer for **UI (Selenium)**, **Backend (REST API)**, and **Data Integrity (Database)** within a BDD-driven ecosystem.

This engine handles complex E2E business flows where a single scenario requires API state injection, UI interaction, and subsequent DB validation.

---

## 📂 Project Structure
The architecture follows SOLID principles and strict separation of concerns:

```text
Unified-Java-Test-Automation/
├── .github/workflows/       # CI/CD Pipeline (GitHub Actions)
├── src/
│   ├── main/java/core/      # Framework Core: Drivers, Builders, Helpers
│   └── test/java/
│       ├── api/             # REST API Layer (RestAssured)
│       ├── db/              # Data Integrity Layer (JDBC)
│       ├── pages/           # UI Layer (Page Object Model)
│       ├── runners/         # Cucumber Test Runners
│       └── stepdefs/        # Step Definitions & Glue Code
├── src/test/resources/
│   ├── features/            # BDD Business Scenarios (Gherkin)
│   └── config/              # Environment & App configurations
├── allure-results/          # Test Execution Results (Ignored)
├── pom.xml                  # Project Object Model & Dependencies
└── README.md                # Project Documentation
```

---

## 🔄 Unified Orchestration Flow
⚡ The engine excels at complex E2E scenarios by synchronizing API, UI, and DB layers in a single execution thread.

```Gherkin

Scenario: Create booking via API and verify across UI and Database
Given I create a new booking via API with name "Sergei" and price 150
And I navigate to the booking management dashboard
Then I should see the booking for "Sergei" in the list
And The database should contain a record for "Sergei" with price 150
```
How it works under the hood:

API Layer: Injects the state directly into the system, bypassing slow UI forms.

Database Layer: Instantly verifies that the persistence layer (H2) has stored the correct values.

UI Layer: Uses Selenium to ensure the end-user can actually interact with the data created via API.

## 🌐 UI Layer (Selenium POM)
The UI layer implements the **Page Object Model (POM)** enhanced with **Selenium PageFactory**. This ensures a clean separation between page elements and test logic, while providing lazy initialization for optimized performance.

```java
// Example: UI Page Object with PageFactory and Explicit Waits
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this); // Mandatory for @FindBy initialization
    }

    public void performLogin() {
        // Using ExpectedConditions to mitigate flaky UI behavior on slow Windows environments
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
}
```
## 🗄️ Data Integrity Layer (H2 Database)
The framework features a Self-Healing DB Manager that automatically synchronizes the In-Memory H2 schema with the test requirements. It ensures zero "Table Not Found" errors by auto-initializing the database upon the first connection.

```Java

// Example: Self-Healing DB Integrity Check
public void verifyDatabaseRecord(int id) {
    // The DBManager automatically ensures 'bookings' table exists
    String query = "SELECT * FROM bookings WHERE booking_id = " + id;

    // Results are returned as a List of Maps with normalized lowercase keys
    List<Map<String, Object>> results = dbManager.executeQuery(query);

    Assert.assertFalse(results.isEmpty(), "Data Integrity Error: Record not found.");
    Assert.assertEquals(results.get(0).get("firstname"), "Sergei");
}
```
## 🚀 Getting Started
Installation
Clone the repository:

```Bash

git clone https://github.com/GitHubMaster07/Unified-Java-Test-Automation.git
```

Build the project:
```Bash
mvn clean compile
```

Execution
Run specific layers or full orchestration using Cucumber tags. Use clean to ensure fresh In-Memory DB state:

## Run all tests
```Bash
mvn clean test
```
## Targeted execution
```Bash
# Run only E2E Orchestration (API + UI + DB)
mvn clean test -Dcucumber.filter.tags="@regression"
```
```Bash
# Run only Smoke tests
mvn clean test -Dcucumber.filter.tags="@smoke"
```
## 📊 Analytics
Test execution data is captured and visualized via Allure Reports.

```Bash

allure serve target/allure-results
```

⚖️ License
Distributed under the MIT License.

👨‍💻 Author
**Sergei Volodin** Senior SDET / Automation Architect

