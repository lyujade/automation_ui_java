package com.runners.cucumber;

import com.runners.testautomation.Utility.TestRunUtil;
import com.runners.testautomation.Utility.CommonPropertyManager;
import com.runners.testautomation.Utility.DriverManager;
import com.runners.testautomation.Utility.FileUtil;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.DataProvider;

import java.util.Arrays;

@CucumberOptions(
        features = "src/test/resources",
        glue = {"com.testautomation.StepDef"},
        monochrome = true,
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "junit:test-results/reports/JunitReport.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class RunnerTest extends AbstractTestNGCucumberTests {

    private static final Logger log = LogManager.getLogger(RunnerTest.class);

    @BeforeSuite
    public void beforeSuite(ITestContext testContext) {
        String suiteName = testContext.getCurrentXmlTest().getSuite().getName();
        log.info(TestRunUtil.getSuiteNameState(suiteName, "BeforeSuite"));
        DriverManager.killDriver(CommonPropertyManager.getBrowser());
        FileUtil.deleteFileAndDirectories(CommonPropertyManager.getTestReportsDir(), Arrays.asList(".txt"));
    }

    @Parameters({"browsertype"})
    @BeforeTest
    public void beforeTestSetUp(ITestContext testContext, @Optional("") String browsertype) {
        log.info(TestRunUtil.getTestNameState(testContext.getName(), "BeforeTest"));
        CommonPropertyManager.setBroswer(System.getProperty("browsertype") == null ? browsertype : System.getProperty("browsertype"));
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        try {
            return super.scenarios();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load scenarios", e);
        }
    }

    @AfterTest
    public void afterTest(ITestContext context) {
        log.info(TestRunUtil.getTestNameState(context.getName(), "AfterTest"));
    }

    @AfterSuite
    public void afterSuite(ITestContext context) {
        log.info(TestRunUtil.getSuiteNameState(context.getCurrentXmlTest().getSuite().getName(), "AfterSuite"));
    }
}
