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
### 🧱 Framework Architecture
```text
java-selenium-bdd-framework/
├── src/
│   ├── main/java/
│   │   └── core/                 # Framework Engine
│   │       ├── DriverFactory.java   # ThreadLocal Selenium management
│   │       ├── ConfigManager.java   # properties/env loader
│   │       ├── BasePage.java        # Common Selenium wrappers (waits/clicks)
│   │       └── APIClient.java       # RestAssured base specifications
│   └── test/java/
│       ├── api/                  # API Logic
│       │   ├── endpoints/           # API routes & constants
│       │   └── payloads/            # JSON POJO models
│       ├── pages/                # UI Logic (Page Object Model)
│       │   ├── LoginPage.java       # Fluent POM implementation
│       │   └── DashboardPage.java
│       ├── db/                   # Database Logic
│       │   └── DatabaseUtils.java   # JDBC connection & query methods
│       ├── stepdefs/             # BDD Glue Code
│       │   ├── Hooks.java           # Setup/Teardown (@Before/@After)
│       │   └── LoginSteps.java
│       └── runners/              # Execution Control
│           └── TestRunner.java      # TestNG/Cucumber parallel config
├── src/test/resources/
│   ├── features/                 # Gherkin Scenarios
│   │   ├── auth.feature
│   │   └── api_validation.feature
│   ├── testdata/                 # Static JSON/CSV data files
│   └── config.properties         # Global framework configurations
├── .gitattributes                # Repository language optimization
├── pom.xml                       # Maven dependencies & build lifecycle
└── README.md                     # Project documentation
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

### 👨‍💻 Author
**Sergei Volodin**
- 🧪 Senior Software Development Engineer in Test (SDET)
- 📍 Chicago, IL
- 🏗️ Built with passion for scalable, maintainable, and enterprise-grade test automation.
