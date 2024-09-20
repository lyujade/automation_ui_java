package com.runners.testautomation.StepDef;

import com.runners.testautomation.PageObjects.HomeLandingPage;
import com.runners.testautomation.StepDef.BaseSteps;
import com.runners.testautomation.Utility.ExcelHandler;
import com.runners.testautomation.Utility.TestContext;
import com.runners.testautomation.Utility.TestDataProvider;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;


public class CommonStepDefinitions extends BaseSteps {


    private HomeLandingPage homeLandingPage;

    public CommonStepDefinitions(TestContext context) {
        super(context);

        homeLandingPage = new HomeLandingPage(context);

    }
    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {
        // Load data from Excel using ExcelHandler
        return ExcelHandler.getTestData("path/to/LoginData.xlsx", "Sheet1");
    }
    @Given("^The user opens the Login page$")
    public void user_opens_login_page() {
        homeLandingPage.verifyIDWLoginPage(); // Navigate to login page
    }
//    @When("^User enters valid username \"(.*)\", password \"(.*)\", and confirm password \"(.*)\"$")
//    public void user_login_with_data(String username, String pwd, String confirmPWD) {
//        homeLandingPage.enterUsername(username); // Enter username
//        homeLandingPage.enterPassword(pwd);      // Enter password
//        homeLandingPage.confirmPassword(confirmPWD);  // Confirm password
//    }

    @When("^User enters valid username \"(.*)\", password \"(.*)\", and confirm password \"(.*)\"$")
    public void user_login_with_data(String username, String pwd, String confirmPWD) throws InterruptedException {
        homeLandingPage.enterUsername(username); // Enter username from feature file
        homeLandingPage.enterPassword(pwd); // Enter password from feature file
        homeLandingPage.confirmPassword(confirmPWD); // Confirm password from feature file
    }
    @When("User enters valid username \"<username>\", password \"<pwd>\", and confirm password \"<confirmpwd>\"")
    @org.testng.annotations.Test(dataProvider = "loginData")
    public void user_enters_credentials(String username, String password, String confirmPassword) {
        homeLandingPage.enterUsername(username); // Enter username from feature file
        homeLandingPage.enterPassword(password); // Enter password from feature file
        homeLandingPage.confirmPassword(confirmPassword); // Confirm password from feature file
        homeLandingPage.clickSignIn();

        // Simulate entering credentials here
    }
    @Then("^I should be logged in successfully$")
    public void login_page()  {
        homeLandingPage.verifyIDWLoginPage();
    }

}
