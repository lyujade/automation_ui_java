package com.runners.testautomation.Utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.IOException;
import java.util.Set;

import static org.openqa.selenium.remote.Browser.*;

public class DriverManager {

    private WebDriver driver;
    //	public static WebDriver driver;
//	public static WebDriverWait wait = null;
//	public static JavascriptExecutor executor = null;
//	public static Actions action = null;
    private static Logger log = LogManager.getLogger(DriverManager.class);
    private static boolean status;
    private static int DELAYTIME = 3;
    private static int Maxtimetowait = 10;

    /*
     * This method will create driver for respective browser
     * @param driverType - Type of the driver needs to be created
     * @return WebDriver based on type of the driver
     */

    public WebDriver getDriver(DriverType driverType, String url) {
        try {

            switch(driverType) {
                case FIREFOX:
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case CHROME:
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
                    driver = new ChromeDriver();
                    break;
                case IE:
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case EDGE:
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case HEADLESS:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("headless");
                    driver = new ChromeDriver(options);
                    break;
                default:
                    log.error("No driver found for the givn driver type: {}", driverType);
                    break;
            }
            driver.manage().window().maximize();
            driver.get(url);

        } catch (Exception e) {
            log.error("Driver is not initializes for the driver type: {}; Exception: {}", driverType, e.getMessage());
        } return driver;
    }

    //public static void navigateToUrl(String Url) {
    public void navigateToUrl(String Url) {
        driver.get(Url);

    }

    public void closeBrowser() {
        driver.close();
    }

    public void closeAllBroswerWindows() {
        Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            driver.switchTo().window(window);
            driver.close();
        }
    }

    public void quitBrowser() {
        driver.quit();
    }

    public void maximizeWindows() {
        driver.manage().window().maximize();
    }

    public static void killDriver(DriverType driverType) {
        String driverExe = null;
        switch (driverType){
            case FIREFOX:
                driverExe = "geckodriver.exe";
                break;
            case CHROME:
            case HEADLESS:
                driverExe = "chromedriver.exe";
                break;
            case EDGE:
                driverExe = "MicrosoftWebDriver.exe";
                break;
            case IE:
                driverExe = "IEDriverServer.exe";
                break;
            default:
                log.error("No driver found for drivertype ' "+ driverType + " ' ");
                break;
        }
        String commond = "taskkill /F /IM " + driverExe;
        try {
            Runtime.getRuntime().exec(commond);
        } catch(IOException e) {
            log.error("unable to kill driver " + commond, e.getMessage());
        }
    }

}

