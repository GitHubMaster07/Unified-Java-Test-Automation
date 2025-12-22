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

src/test/java/
    │── core/ → DriverFactory, ConfigManager
    
    │── pages/ → Page Objects (POM)
    
    │── stepdefs/ → Step Definitions (BDD)
    
    │── runners/ → TestNG/Cucumber runners
    
    │── api/ → API automation using RestAssured
    
    │── data/ → DB validation & utilities
    │
    src/test/resources/
    │── features/ → Feature files
    │── config/ → Config & environment files

---

🧰 Key Features

✅ Hybrid BDD + Page Object Model

✅ Parallel Execution (ThreadLocal WebDriver)

✅ Unified UI + API + DB Testing

✅ Centralized Config & Environment Management

✅ Detailed Reporting (Allure & Cucumber HTML)

✅ Easy Jenkins/GitHub Actions Integration

✅ Screenshot Capture on Failures

---

🧱 Production-Ready CI/CD
- This framework is optimized for Stateless Execution in containers.
- Automatic Failure Recovery: Integrated TestNG Listeners for screenshots and retries.
- Artifact Preservation: GitHub Actions uploads allure-report as a permanent deployment artifact for audit trails.

👨‍💻 Author
Sergei Volodin
- 🧪 Senior Software Development Engineer in Test (SDET)
- 📍 Chicago, IL
- 🏗️ Built with passion for scalable, maintainable, and enterprise-grade test automation.
