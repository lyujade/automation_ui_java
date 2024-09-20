package com.runners.testautomation.StepDef;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
//import com.aventstack.extentreports.gherkin.model.Scenario;
import com.runners.testautomation.Utility.ApplicationConstant;
import com.runners.testautomation.Utility.ScreenshotUtil;
import com.runners.testautomation.Utility.TestContext;
import com.runners.testautomation.Utility.TestRunUtil;
import io.cucumber.java.AfterStep;
import org.junit.After;
import org.junit.Before;
import io.cucumber.java.Scenario;
public class Hooks extends BaseSteps {



    public Hooks(TestContext context) {
        super(context);

    }

    @Before
    public void beforeScenario(Scenario scenario) {
        // Add the URL to the Extent report
        ExtentCucumberAdapter.addTestStepLog("Test Env: " + ApplicationConstant.URL);
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        ScreenshotUtil.captureScreenshot(driver, scenario);

    }

    @After
    public void afterScenario(Scenario scenario) {
        if(scenario.isFailed() || scenario.getStatus().toString().equalsIgnoreCase("UNDEFINED")) {
            ScreenshotUtil.captureScreenshot(driver, scenario);
        }
        log.info(TestRunUtil.getScenarioExecutionEndState(scenario));
        driverManager.closeAllBroswerWindows();
        driverManager.quitBrowser();
    }

}
