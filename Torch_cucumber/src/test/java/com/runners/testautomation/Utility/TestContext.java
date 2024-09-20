package com.runners.testautomation.Utility;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class TestContext {

    private WebDriver driver;
    private DriverManager driverManager;
    private SoftAssert softAssert;

    private ElementUtil elementUtil;
    private CommonPropertyManager commonPropertyManager;

    public TestContext() {
        driverManager = new DriverManager();
        driver = driverManager.getDriver(
                CommonPropertyManager.getBrowser(), ApplicationConstant.URL);
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public SoftAssert getSoftAssertObject() {
        return (softAssert == null) ? softAssert = new SoftAssert() : softAssert;
    }

    public ElementUtil getElementUtil() {
        return (elementUtil == null) ? elementUtil = new ElementUtil(driver) : elementUtil;
    }

    public CommonPropertyManager loadProperty() {
        return commonPropertyManager;
    }

}