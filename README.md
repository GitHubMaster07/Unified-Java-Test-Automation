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

## 🏗️ Architectural Patterns
- **Fluent POM**: Optimized Page Object Model using method chaining for readable, maintainable test scripts.
- **ThreadLocal Thread Safety**: Engineered for high-parallelization in CI/CD without browser session conflicts.
- **Factory Design Pattern**: Centralized WebDriver and API Request Specification management.
- **Validation Layers**: Multi-layer verification (UI vs Database) to ensure data persistence and front-end accuracy.

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

For the high-level strategy and planning behind this framework, see my Test-Strategy-Documentation-Samples repository(https://github.com/GitHubMaster07/Test-Strategy-Documentation-Samples.git).

### 👨‍💻 Author
**Sergei Volodin**
- 🧪 Senior Software Development Engineer in Test (SDET)
- 📍 Chicago, IL
- 🏗️ Built with passion for scalable, maintainable, and enterprise-grade test automation.
