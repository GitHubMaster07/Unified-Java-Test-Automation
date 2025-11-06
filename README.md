# 🚀 Senior-SDET-Automation-Framework

![Java](https://img.shields.io/badge/Language-Java_11-blue?logo=java)
![Selenium](https://img.shields.io/badge/UI%20Testing-Selenium-green?logo=selenium)
![RestAssured](https://img.shields.io/badge/API%20Testing-RestAssured-yellowgreen)
![Cucumber](https://img.shields.io/badge/BDD-Cucumber-brightgreen?logo=cucumber)
![Maven](https://img.shields.io/badge/Build-Maven-orange?logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-lightgrey)
![Allure](https://img.shields.io/badge/Reports-Allure-blueviolet)

---

### 🧠 Overview
A **hybrid test automation framework** that combines **Selenium WebDriver**, **Cucumber (BDD)**, **RestAssured**, **TestNG**, and **JDBC**.  
It provides a **unified structure** for UI, API, and Database testing with built-in CI/CD and reporting support.

---

## ⚙️ Tech Stack
| Layer | Tool / Library | Purpose |
|-------|----------------|----------|
| **UI** | Selenium WebDriver | Web UI testing |
| **API** | RestAssured | REST API validation |
| **BDD** | Cucumber + TestNG | Behavior-Driven testing |
| **Database** | JDBC + SQL | Backend validation |
| **Build & CI/CD** | Maven, Jenkins, GitHub Actions | Continuous integration |
| **Reports** | Allure, Cucumber HTML | Detailed execution results |

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

## 🧪 Execution Commands
### Run all tests
```bash
mvn clean test

---

🧠 Sample BDD Feature
Feature: User Login Functionality
  @ui @smoke
  Scenario: Successful Login with Valid Credentials
    Given the user is on the Login page
    When the user enters the username "tomsmith" and password "SuperSecretPassword!"
    And the user clicks the Login button
    Then the user should be redirected to the secure area

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

🧱 Continuous Integration Example
# .github/workflows/ci.yml
name: CI
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          java-version: '11'
          distribution: 'temurin'
      - name: Build and test with Maven
        run: mvn clean test
      - name: Generate Allure Report
        run: |
          npm install -g allure-commandline --save-dev
          allure generate target/allure-results --clean -o target/allure-report

---

👨‍💻 Author
Sergei Volodin
🧪 Senior Software Development Engineer in Test (SDET)
📍 Chicago, IL
https://github.com/GitHubMaster07/-Sergei-Volodin-_portfolio
🏗️ Built with passion for scalable, maintainable, and enterprise-grade test automation.
