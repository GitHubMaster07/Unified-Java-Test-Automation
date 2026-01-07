# Unified Java Test Automation Engine (Senior SDET Level)

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)
![Java: 11](https://img.shields.io/badge/Java-11-blue.svg)
![Framework: Hybrid](https://img.shields.io/badge/Framework-Hybrid--Orchestration-orange.svg)

## 📌 Overview
**Unified-Java-Test-Automation** is an Enterprise-level Hybrid Framework designed for high-scalability testing. It provides a unified orchestration layer for **UI** (Selenium), **Backend** (REST API), and **Data Integrity** (Database) within a **BDD-driven ecosystem**.

This engine is built to handle complex E2E business flows where a single scenario might require API state preparation, UI interaction, and subsequent DB validation.

---

## 🏗 Architectural Layers

### 1. UI Layer (Selenium POM)
- **Design Pattern**: Page Object Model (POM) with Page Factory.
- **Features**: Automatic element waiting, cross-browser support, and screenshot capturing on failure.
- **Location**: `src/test/java/pages/`

### 2. API Layer (RestAssured)
- **Capabilities**: Full CRUD operations validation, JSON Schema matching, and POJO serialization.
- **Purpose**: Fast backend verification and test data setup.
- **Location**: `src/test/java/api/`

### 3. Data Integrity Layer (JDBC/Database)
- **Function**: Direct SQL execution to verify data persistence and backend business logic.
- **Integration**: Validates that UI/API actions reflected correctly in the DB.
- **Location**: `src/test/java/db/`

### 4. BDD & Orchestration (Cucumber)
- **Language**: Gherkin (Feature files).
- **Hooks**: Sophisticated `@Before` and `@After` hooks for environment setup and teardown.
- **Location**: `src/test/java/stepdefs/` & `src/test/java/runners/`

---

## 🛠 Tech Stack
- **Core**: Java 11
- **Build Tool**: Maven 3.x
- **UI Engine**: Selenium WebDriver
- **API Engine**: RestAssured
- **Database**: JDBC / SQL
- **Test Runner**: JUnit 4/5 + Cucumber 7.x
- **Reporting**: Allure Framework

---

## 🚀 Getting Started

### Prerequisites
- JDK 11
- Maven installed and configured in system PATH
- Chrome/Edge Browser (for UI tests)

### Installation
```bash
git clone [https://github.com/GitHubMaster07/Unified-Java-Test-Automation.git](https://github.com/GitHubMaster07/Unified-Java-Test-Automation.git)
cd Unified-Java-Test-Automation
mvn clean compile
```
---

## Execution
Run all tests (Hybrid Mode):

```Bash
mvn clean test
```
Run specific layer by Tags:
```
```Bash
mvn test -Dcucumber.filter.tags="@API"
```
```
mvn test -Dcucumber.filter.tags="@UI"
```
```
mvn test -Dcucumber.filter.tags="@Database"
```
---

## 📊 Reporting & Analytics
The framework generates detailed Allure reports. To view the latest results:

Generate and open report:

```Bash
allure serve allure-results
```
## ⚖️ License
Distributed under the MIT License.

## 👨‍💻 Author
**Sergei Volodin** Senior SDET / Automation Architect
