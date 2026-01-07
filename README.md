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

## 🛠️ Hybrid Layer Implementation
⚡ REST API Layer (RestAssured)
The API layer is designed for fast state preparation and backend validation.

```Java

// Example: API Contract Validation
public void validateBookingCreated() {
    given()
        .header("Content-Type", "application/json")
        .body(bookingPayload)
    .when()
        .post("/booking")
    .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/booking.json"));
}
```
## 🌐 UI Layer (Selenium POM)
Implements Page Object Model with Page Factory for robust interaction.

```Java

// Example: UI Page Element
@FindBy(id = "login-button")
private WebElement loginButton;

public void performLogin() {
    wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
}
```
## 🗄️ Data Integrity Layer (Database)
Verifies data consistency directly in the DB using JDBC.

```Java

// Example: DB Integrity Check
public boolean isBookingInDatabase(int id) {
    String query = "SELECT * FROM bookings WHERE id = " + id;
    ResultSet rs = dbExecutor.executeQuery(query);
    return rs.next();
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
Run specific layers using Cucumber tags:

## Run all tests
```
mvn test
```
## Targeted execution
```
mvn test -Dcucumber.filter.tags="@API"
```
```
mvn test -Dcucumber.filter.tags="@UI"
```
## 📊 Analytics
Results are processed via Allure Reports.

```Bash

allure serve allure-results
```

⚖️ License
Distributed under the MIT License.

👨‍💻 Author
**Sergei Volodin** Senior SDET / Automation Architect

