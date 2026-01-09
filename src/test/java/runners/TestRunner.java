package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        // 'hooks' must be explicitly included in glue, otherwise @Before/@After
        // annotations won't trigger, causing driver initialization to fail.
        glue = {"stepdefs", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                // Allure plugin is the source of truth for our CI/CD dashboard.
                // Missing this means no trend analysis in Jenkins/GitHub Actions.
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        // monochrome=true makes the console output readable on Windows CMD/PowerShell
        // by stripping out messy ANSI color codes that often break text formatting.
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        // Parallel execution is toggled here.
        // Note: Thread count is controlled via the TestNG XML or Maven surefire settings,
        // not by the code itself.
        return super.scenarios();
    }
}