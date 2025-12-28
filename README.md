# 🚀 Unified-Java-Test-Automation

![Java](https://img.shields.io/badge/Language-Java_11-blue?logo=java)
![Selenium](https://img.shields.io/badge/UI%20Testing-Selenium-green?logo=selenium)
![RestAssured](https://img.shields.io/badge/API%20Testing-RestAssured-yellowgreen)
![Cucumber](https://img.shields.io/badge/BDD-Cucumber-brightgreen?logo=cucumber)
![Maven](https://img.shields.io/badge/Build-Maven-orange?logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-lightgrey)
![Allure](https://img.shields.io/badge/Reports-Allure-blueviolet)

---

### 🧠 Strategic Overview
This is an **Enterprise-level Hybrid Framework** designed for high-scalability testing. It provides a unified orchestration layer for **UI (Selenium)**, **Backend (REST API)**, and **Data Integrity (Database)** within a BDD-driven ecosystem.

---

## 🚀 Advanced Architectural Patterns

This framework is built using industry-standard design patterns to ensure enterprise-level scalability and maintainability.

* **Dual-Core Orchestration**: Separates the **Framework Engine** (`src/main/java/core`) for browser and DB management from the **Test Orchestrator** (`src/test/java/core`), ensuring a decoupled, stateless architecture.
* **Factory Design Pattern**: Implements a `DriverFactory` utilizing `ThreadLocal<WebDriver>` to guarantee thread safety during high-parallelization in CI/CD environments.
* **Data Builder Pattern**: Utilizes `BookingDataBuilder` to dynamically generate complex, type-safe test data for both API payloads and UI inputs, eliminating hard-coded data flakiness.
* **Fluent Page Object Model (POM)**: Page objects use method chaining to create a "human-readable" DSL for test scripts, improving developer experience and script readability.
* **Multi-Layer Validation**: Integrated `db/` and `api/` controllers allow for full-stack integrity checks, validating that front-end actions correctly persist in the backend database.

---

## ⚙️ Advanced Tech Stack
| Layer | Technology | Implementation Detail |
|-------|------------|-----------------------|
| **Core** | Java 11 / Maven | Type-safe, dependency-managed core. |
| **UI Engine** | Selenium 4 | W3C compliant grid-ready drivers. |
| **API Engine** | RestAssured | Gherkin-style API validation. |
| **Validation** | JDBC / TestNG | Robust assertions and DB connectivity. |
| **Observability** | Allure / Log4j2 | High-fidelity logs and stakeholder reports. |

---

## 🧩 Framework Structure

**Factory Design Pattern:** The core/DriverFactory manages ThreadLocal<WebDriver> instances, ensuring thread safety during parallel execution in CI/CD pipelines.

**Data Builder Pattern:** The builders/ package allows for the creation of complex API payloads and UI test data dynamically, reducing hard-coded values and script maintenance.

**Hybrid Validation:** By combining the api/ and db/ layers, the framework can perform "behind-the-scenes" data verification immediately after a UI action occurs.

### 🧱 Framework Architecture
```text
Unified-Java-Test-Automation/
├── src/
│   ├── main/java/
│   │   ├── builders/           # Test Data Builders (e.g., BookingDataBuilder)
│   │   └── core/               # Framework Engine (Config, DriverFactory, DBManager)
│   └── test/java/
│       ├── api/                # API Test Logic & Controllers
│       ├── db/                 # Database Assertions & Validation
│       ├── pages/              # Page Object Model (POM) Implementations
│       ├── runners/            # TestNG/Cucumber Parallel Runners
│       ├── stepdefs/           # BDD Step Definitions (Glue Code)
│       └── ui/                 # UI-specific Utilities/Hooks
├── src/test/resources/
│   ├── config/                 # Environment-specific properties
│   ├── features/               # BDD Gherkin Feature Files
│   └── application.properties   # Global Test Settings
└── pom.xml                     # Maven Dependency Management
```
---

### 📖 Sample Hybrid Test Showcase
This framework allows for cross-layer validation within a single scenario.
```Gherkin
Scenario: User updates profile and verifies via Backend API
  Given the user is logged into the web portal
  When the user updates their nickname to "SeniorSDET_01"
  Then the Backend API should return "SeniorSDET_01" for the updated user record
```

```Java
@Then("the Backend API should return {string} for the updated user record")
public void verifyViaAPI(String expectedName) {
    Response response = userApiController.getUser(userId);
    Assert.assertEquals(response.jsonPath().getString("nickname"), expectedName);
}
```
### ⚙️ Getting Started
**1. Prerequisites**
JDK 11+ (Amazon Corretto or Temurin)

Maven 3.8+

IDE: IntelliJ IDEA (Recommended)

**2. Execution**
```Bash
# Run all tests via Maven
mvn test

# Run a specific TestNG suite
mvn test -DsuiteXmlFile=src/test/resources/runners/smoke.xml

# Generate & Open Allure Report
mvn allure:report
mvn allure:serve
```

### 🔍 Failure Analysis & Observability
- **Automatic Screenshots:** Integrated TestNG Listeners capture full-page screenshots at the exact moment of failure, attached directly to reports.

- **Thread-Safe Logging:** Log4j2 captures every UI action, API request, and SQL query for deep-dive root cause analysis.

- **Artifact Preservation:** GitHub Actions automatically uploads Allure results as permanent deployment artifacts for audit trails.

### 🧰 Key Features

✅ Hybrid BDD + Page Object Model

✅ Parallel Execution (ThreadLocal WebDriver)

✅ Unified UI + API + DB Testing

✅ Centralized Config & Environment Management

✅ Detailed Reporting (Allure & Cucumber HTML)

✅ Easy Jenkins/GitHub Actions Integration

✅ Screenshot Capture on Failures

👨‍💻 **Developer Experience (Fluent API)**

The framework utilizes method chaining to create highly readable test scripts:
```java
@Test
public void searchAndVerifyProduct() {
    homePage
        .load()
        .searchFor("MacBook")
        .selectFirstProduct()
        .addToCart()
        .verifySuccessMessage("Added to cart");
}
```
---

> [!TIP]
> ### 🎯 Engineering Standards (Definition of Done)
> To maintain enterprise-grade reliability, all automation in this repository adheres to the following "Quality Gates":
> * **Zero-Flakiness Policy**: New scripts must pass 5 consecutive local runs and a CI-pipeline check before being merged to `main`.
> * **Separation of Concerns**: Strict architectural boundaries—No test assertions in **Page Objects**; no Selenium locators in **Step Definitions**.
> * **Stateless Execution**: Tests are designed to be independent; each scenario handles its own data setup and teardown via API or DB hooks to prevent "domino effect" failures.
> * **Atomic Scenarios**: Each BDD scenario focuses on a single business outcome to ensure fast debugging and clear reporting.

---

### 🧱 Production-Ready CI/CD
- This framework is optimized for Stateless Execution in containers.
- Automatic Failure Recovery: Integrated TestNG Listeners for screenshots and retries.
- Artifact Preservation: GitHub Actions uploads allure-report as a permanent deployment artifact for audit trails.

For the high-level strategy and planning behind this framework, see my [Test-Strategy-Blueprint](https://github.com/GitHubMaster07/Test-Strategy-Documentation-Samples.git).

### 👨‍💻 Author
**Sergei Volodin**
- 🧪 Senior Software Development Engineer in Test (SDET)
- 📍 Chicago, IL
- 🏗️ Built with passion for scalable, maintainable, and enterprise-grade test automation.
