package com.runners.testautomation.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonPropertyManager {

    private static final Logger log = LogManager.getLogger(CommonPropertyManager.class);
    private static Properties commonProperties = new Properties();
    private static final String RUN_PROPERTIES_FILE = "run-config.properties";

    static
    {
        try(InputStream resourceStream = CommonPropertyManager.class.getClassLoader().getResourceAsStream(RUN_PROPERTIES_FILE))
        {
            commonProperties.load(resourceStream);
        }
        catch(IOException e) { //
            log.error("'Run-Config' Properties file not found in path", e.getMessage());
        }
    }

    public static DriverType getBrowser() {
        String browser = commonProperties.getProperty("browser");
        DriverType driverType;
        switch(browser.toLowerCase()) {
            case "chrome":
                driverType = DriverType.CHROME;
                break;
            case "firefox":
                driverType = DriverType.FIREFOX;
                break;
            case "edge":
                driverType = DriverType.EDGE;
                break;
            case "ie":
                driverType = DriverType.IE;
                break;
            default:
                driverType = DriverType.CHROME;
                break;
        }
        return driverType;
    }

    public static String getTestResultDir() {
        return commonProperties.getProperty("testresults.dir");
    }

    public static String getTestReportsDir() {
        return commonProperties.getProperty("reports.dir");
    }

    public static String getScreenshotsDir() {
        return commonProperties.getProperty("Screenshots.dir");
    }

    public static String getScreenshotsFormate() {
        return commonProperties.getProperty("Screenshots.filetype");
    }

    public static String getPassword() {
        return commonProperties.getProperty("password");
    }

    public static void setBroswer(String browser) {
        if (!browser.isEmpty()) {
            commonProperties.setProperty("browser", browser.toUpperCase());
            try {
                commonProperties.store(new StringWriter(), "Broswer type is stored in common config property");
                log.info("Browser type ' " + browser + " ' Saved successfully in common config property");
            } catch (Exception e) {
                log.error("Not able to store the browser type ' " + browser + " ' in common confiq properties" + e.getMessage());
            }
        }
    }

    public static Properties loadProperty() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("resources/browser-config.properties"));
            props.load(new FileInputStream("resources/testdata-config.properties"));
            props.load(new FileInputStream("resources/config.properties"));
            props.load(new FileInputStream("resources/devconnect.properties"));
            props.load(new FileInputStream("resources/limitManagement.properties"));
            props.load(new FileInputStream("resources/walletSharePage.properties"));
            props.load(new FileInputStream("resources/createNewAccountPlan.properties"));
            props.load(new FileInputStream("resources/newAccountPlan.properties"));
            props.load(new FileInputStream("resources/IDWHomepage.properties"));
            props.load(new FileInputStream("resources/contributorsAndEndorsers.properties"));
            props.load(new FileInputStream("resources/IDW360SalesCompass.properties"));
            props.load(new FileInputStream("resources/IDWAccountPlanGenerateSharedLink.properties"));
            props.load(new FileInputStream("resources/accountRevenuePage.properties"));
            props.load(new FileInputStream("resources/generativeAIAssistantPage.properties"));
            props.load(new FileInputStream("resources/buttonAndDialog.properties"));
            props.load(new FileInputStream("resources/apiRequest.properties"));
            props.load(new FileInputStream("resources/C360IDWHomePage.properties"));
            props.load(new FileInputStream("resources/C360OverviewPage.properties"));
            props.load(new FileInputStream("resources/jellyfishPage.properties"));
            props.load(new FileInputStream("resources/C360PricingPage.properties"));
        } catch (IOException e) {
            log.error("unable to load required properties" + e.getMessage());
        }
        return props;
    }




}


