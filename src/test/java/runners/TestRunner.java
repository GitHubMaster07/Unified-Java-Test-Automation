package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Centralized Test Orchestrator.
 * Configures the BDD execution engine, mapping Gherkin features to their respective 
 * step implementations and managing global reporting plugins.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        
        /**
         * Component Scanning Configuration.
         * Defines the package boundaries for glue code and hooks to ensure 
         * all test components are correctly injected into the runtime context.
         */
        glue = {"stepdefs", "runners"},
        
        /**
         * Reporting & Traceability Layer.
         * 'pretty' - Human-readable console output.
         * 'json' - Structured data for external parsing.
         * 'Allure' - Rich visual analytics for stakeholders.
         */
        plugin = {
                "pretty",
                "json:target/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        
        /**
         * Execution Strategy (Tagging).
         * Filters the test suite based on business priority and environment constraints.
         * Excludes scenarios marked with @skip to maintain pipeline stability.
         */
        tags = "@smoke or @ui and not @skip",
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * High-Concurrency Execution Engine.
     * Overriding the default provider to enable parallel scenario execution. 
     * This significantly reduces the feedback loop in the CI/CD pipeline 
     * by distributing load across multiple CPU cores.
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
