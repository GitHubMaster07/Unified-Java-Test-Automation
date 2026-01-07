Senior-SDET-Automation-FrameworkA robust, scalable, and reliable automation framework demonstrating Senior SDET expertise and best practices for Shift-Left quality assurance.

🎯 Project Overview & Core ValueThis framework utilizes a Test Pyramid approach to prioritize fast, stable backend validation. It is designed to be highly maintainable and easily integrated into any modern CI/CD pipeline.Key Value Proposition: This architecture reduces regression cycle time by enabling parallel execution and focusing test effort on high-risk, high-ROI areas (API and Data integrity).

🛠️ Technical Stack & Framework Architecture
Component              Technology                Rationale
Language:              Java 17+                  Industry standard for enterprise stability and performance.
Build Tool:            Maven                     Standardized project and dependency management.
API Testing:           Rest Assured              High-performance, fluent API for integration and contract testing.
UI Testing:            Selenium / Playwright     Used for end-to-end (E2E) customer journey validation (only on critical paths).
Data Validation:       JDBC / Advanced SQL       Custom utility for validating data integrity during ETL/Data Migration checks.
Test Runner:           TestNG / Cucumber(BDD)    Supports data-driven testing, grouping, and parallel execution.
Reporting:             Allure Reporting          Generates rich, interactive, and shareable reports for complete traceability.

🏗️ Architectural Design PrinciplesThe framework adheres to strict software development principles for maximum maintainability:
- Page Object Model (POM): Ensures reusable, readable UI code and separates test logic from page locators.
- Builder Pattern: Used for constructing complex API request payloads and consistent Test Data Management (TDM).
- Layered Structure: Clear separation of tests, utilities, resource files, and core framework components.

🔗 Continuous Testing & ExecutionThe framework is fully configured for automated execution:
- Containerization: Includes a Dockerfile for seamless, repeatable execution in any environment.
- CI/CD: Uses GitHub Actions (.github/workflows/ci.yml) to automatically build and run tests on every push to the main branch.

  Execution Commands:
- Run All Tests (Smoke/API): mvn clean test -Dgroups=smoke,api
- Run Parallel Regression: mvn clean test -DsuiteXmlFile=testng-parallel.xml
- Generate Allure Report: allure generate --clean && allure open
