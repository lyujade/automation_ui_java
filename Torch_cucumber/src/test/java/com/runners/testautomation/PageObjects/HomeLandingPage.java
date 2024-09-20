package com.runners.testautomation.PageObjects;


import com.runners.testautomation.Utility.ApplicationConstant;
import com.runners.testautomation.Utility.TestContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class HomeLandingPage extends BasePage {
    public HomeLandingPage(TestContext context) {
        super(context);
    }
    @FindBy(how = How.XPATH, using = "(//tts-input/div/div/div/input)[1]")
    public WebElement UsernameField;
    @FindBy(how = How.XPATH, using = "(//tts-input/div/div/div/input)[2]")
    public WebElement PasswordField;
    @FindBy(how = How.XPATH, using = "(//tts-input/div/div/div/input)[3]")
    public WebElement ConfirmPasswordField;
    @FindBy(how = How.XPATH, using = "//login/div/div[1]/div[1]/div[2]/form/div[4]/button")
    public WebElement SignInButton;
    public void verifyIDWLoginPage() {
        String currentURL = driver.getCurrentUrl();
        System.out.println(currentURL);
        elementUtil.waitUntilElementIsVisible(UsernameField);
        // Assert that the current URL contains the constant URL
        System.out.println("currentURL"+currentURL);
        System.out.println("ApplicationConstant.URL"+ ApplicationConstant.URL);
        Assert.assertTrue(currentURL.contains(ApplicationConstant.URL), "The current URL does not contain the expected URL.");

    }
    public void enterUsername(String value) {
        UsernameField.isDisplayed();
        elementUtil.verifyandType(UsernameField, value);
    }
    public void enterPassword(String value) {
        UsernameField.isDisplayed();
        elementUtil.verifyandType(PasswordField, value);
    }
    public void confirmPassword(String value) {
        UsernameField.isDisplayed();
        elementUtil.verifyandType(ConfirmPasswordField, value);
    }
    public void clickSignIn(){
        elementUtil.waitUntilElementIsVisible(SignInButton);
        elementUtil.verifyandClick(SignInButton);

    }
}
