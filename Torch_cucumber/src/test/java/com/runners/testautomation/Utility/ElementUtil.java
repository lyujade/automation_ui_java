package com.runners.testautomation.Utility;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
//import java.util.concurrent.TimeUnit;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.testng.Assert;
import java.text.NumberFormat;
public class ElementUtil {

    private final WebDriver driver;
    private static final Logger logger = LogManager.getLogger(ElementUtil.class);
    private static Actions actions = null;
    private static JavascriptExecutor executor = null;
    private static int Maxtimetowait = 10;
    private static String mainWindowHandle;

    public ElementUtil(WebDriver driver){
        this.driver = driver;
        //	commonProperties = new CommonPropertyManager();
        //	wait = new WebDriverWait(driver, 120);
        actions = new Actions(driver);
        executor = ((JavascriptExecutor) driver);
        this.mainWindowHandle = driver.getWindowHandle();
    }


    private Wait<WebDriver> fluentWait(){
        return new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(200)).
                ignoring(NoSuchElementException.class);
    }

    public String getBrowser() {
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browser = cap.getBrowserName().toLowerCase();
        return StringUtils.capitalize(browser);
    }






    public void closeBrowser() {
        String name = getBrowser();
        driver.close();
        logger.info(name + " Browser Closed Successfully");
        driver.quit();
        logger.info(name + " Browser Closed Completely");
    }

    public boolean selectbyText(WebElement element, String text) {
        boolean flag=false;
        try {
            if (element.getAttribute("value")!=text) {
                Select select = new Select(element);
                select.selectByVisibleText(text);
                logger.info("Option " + text + " is selected from dropdown");
                flag = true;
            }
            else {
                logger.info("Option " + text + " is already selected from dropdown");
                flag = true;
            }
        } catch (Exception e) {
            logger.error("Unable to select by Text : " + text, e );
        }
        return flag;
    }

    //	public void implicitlywait() {
//		driver.manage().timeouts().implicitlyWait(Maxtimetowait, TimeUnit.SECONDS);
//	}
//    public void implicitlyWait(int seconds) {
//        try {
////            driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
//            logger.info("Implicit wait set to " + seconds + " seconds");
//        } catch (Exception e) {
//            logger.error("Failed to set implicit wait", e);
//        }
//    }



    public boolean selectbyValue(WebElement element, String value) {
        boolean flag=false;
        try {
            if (element.getAttribute("value")!=value) {
                Select select = new Select(element);
                select.selectByValue(value);
                logger.info("Option " + value + " is selected from dropdown");
                flag = true;
            }
            else {
                logger.info("Option " + value + " is already selected from dropdown");
                flag = true;
            }
        } catch (Exception e) {
            logger.error("Unable to select by value : " + value, e );
        }
        return flag;
    }

    public void selectOptionByIndexFromDropDown(WebElement element, final int index) {
        try {
            Select sel = new Select(element);
            sel.selectByIndex(index);
            logger.info("Index "+ index + " is selected form dropdown");
        } catch (Exception e) {
            logger.info("Unable to select by Index : " + index , e);
        }
    }

    public void verifyOptionIsSelectedByValueFromDropDown(WebElement element, final String value) throws Exception {
        Select sel = new Select(element);
        List<WebElement> allSelectedOptions = sel.getAllSelectedOptions();
        for (WebElement option : allSelectedOptions) {
            if (option.getText().equalsIgnoreCase(value)) {
                break;
            } else {
                throw new Exception("Option " + value + " is not selected");
            }
        }
        logger.info("Option " + value + " was Selected as expected");
    }

    public boolean verifyOptionByValueIsPresentInTheDropDown(WebElement element, final String value) {
        boolean flag = false;
        try {
            Select sel = new Select(element);
            List<WebElement> allOptions = sel.getOptions();
            for (int i = 0; i < allOptions.size(); i++) {
                if (allOptions.get(i).equals(value)) {
                    flag = true;
                }
            }
            if (flag) {
                logger.info("Expected options " + value + " are present in the dropdown " + element);
            }
        } catch (Exception e) {
            logger.error("Unable to verify Option By Value Is Present In The DropDown : " + value , e);
        }
        return flag;
    }

    public boolean verifyandEnterText(WebElement element, final String text) {
        boolean flag = false;
        try {
            fluentWait().until(ExpectedConditions.visibilityOf(element));
            element.sendKeys(text);
            logger.info("Entered Successfully into " + element);
            flag = true;
        } catch (Exception e) {
            logger.error("Unable to verify and EnterText : " + text , e);
        }
        return flag;
    }

    //	public void verifyandType(WebElement element, final String text) {
//
//		try {
//			fluentWait().until(ExpectedConditions.elementToBeClickable(element));
//			element.click();
//			Thread.sleep(800);
//			element.sendKeys(text);
//			Thread.sleep(800);
//			element.click();
//			logger.info(text + " Entered Successfully into " + element);
//		} catch (Exception e) {
//			logger.error("Unable to verify and Type : " + text , e);
//			try {
//				element.sendKeys(text);
//				Thread.sleep(300);
//				element.click();
//				System.out.println("Successfully direct verify and Type again: " + element);
//				logger.info("Successfully direct verify and Type again: " + element);
//			} catch (Exception lastDirectClickException) {
//				System.out.println("direct verify and Type again failed" + element);
//				logger.error("direct verify and Type again failed: " + element, lastDirectClickException);
//				JavascriptExecutor js = (JavascriptExecutor) driver;
//				try {
//					js.executeScript("arguments[0].value = arguments[1];", element, text);
//					element.sendKeys("");  // Trigger any attached events after setting value
//				} catch (Exception jsException) {
//					logger.error("JavaScript method failed to type: " + element, jsException);
//					Assert.fail("Unable to type text after all methods: " + element);
//				}
//
//			}
//
//		}
//	}
    public void verifyandType(WebElement element, final String text) {
        boolean isTyped = false;

        try {
            fluentWait().until(ExpectedConditions.elementToBeClickable(element));
            System.out.println("fluentWait successful " + text + element);
            //			clearTextForRichWebElement();
            System.out.println("element.click(); successful" + text + element);
            //			clearTextForWebElement(element);// Clear the field before typing
            System.out.println("clearTextForWebElement(element); successful" + text + element);
            element.sendKeys(text);
            System.out.println("element.sendKeys(text);" + text + element);
            Thread.sleep(1000);
            element.click();
            System.out.println(text + "element.click(); successfully and  entered successfully into " + element);
            logger.info(text + " entered successfully into " + element);
            isTyped = true;
        } catch (Exception e) {
            System.out.println("Unable to verify and type: " + text + " into element: " + element);
            logger.error("Unable to verify and type: " + text + " into element: " + element, e);
        }

        if (!isTyped) {
            try {
                clearTextForWebElement(element);// Clear the field before typing
                System.out.println("clearTextForWebElement(element); successful" + text + element);
                element.sendKeys(text);
                System.out.println("element.sendKeys(text);" + text + element);
                verifyandClick(element);
                System.out.println("Successfully direct verify and type again: " + element);
                logger.info("Successfully direct verify and type again: " + element);
                isTyped = true;
            } catch (Exception directException) {
                System.out.println("Direct verify and type again failed: " + element);
                logger.error("Direct verify and type again failed: " + element, directException);

                // Fallback to JavaScript
                JavascriptExecutor js = (JavascriptExecutor) driver;
                try {
                    clearTextForWebElement(element);// Clear the field before typing
                    System.out.println("clearTextForWebElement(element); successful" + text + element);
                    js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", element, text);
                    System.out.println("clearTextForWebElement(element); successful" + text + element);
                    verifyandClick(element);
                    System.out.println("Text entered using JavaScript into: " + element);
                    logger.info("Text entered using JavaScript into: " + element);
                } catch (Exception jsException) {
                    System.out.println("JavaScript method failed to type: " + element);
                    logger.error("JavaScript method failed to type: " + element, jsException);
                    Assert.fail("Unable to type text after all methods: " + element);
                }
            }
        }
    }
    public void switchToNewWindow() {
        // Wait until there are 2 windows, using Duration
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.numberOfWindowsToBe(2));

        // Loop through all available window handles
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                // Switch to the new window
                driver.switchTo().window(handle);
                break;
            }
        }
    }
    public String getCurrentTimestamp() {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Define the format pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the current date and time
        return now.format(formatter);
    }

    public void switchBackToMainWindow() {
        // Switch back to the main window
        driver.switchTo().window(mainWindowHandle);
    }


//		public void verifyandType(WebElement element, final String text) {
//			try {
//				highLightElement(element);
//				fluentWait().until(ExpectedConditions.elementToBeClickable(element));
//				element.click();  // Re-focus
//				element.sendKeys(text);
//				Thread.sleep(500);  // Add a slight delay to let UI catch up
//				element.click();
//				if (!element.getAttribute("value").equals(text)) {  // Verification step
//					throw new Exception("Text not entered correctly, trying JavaScript");
//				}
//				logger.info(text + " Entered Successfully into " + element);
//			} catch (Exception e) {
//				logger.error("Primary method failed, attempting JavaScript: " + element, e);
//				setInputUsingJS(element, text);  // Fallback to JavaScript method
//			}
//		}
//
//		private void setInputUsingJS(WebElement element, String text) {
//
//			((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change'));", element, text);
//			logger.info("JavaScript used to set text on element: " + element);
//		}
//public void verifyandType(WebElement element, final String text) {
//	try {
//		fluentWait().until(ExpectedConditions.visibilityOf(element));
//		element.sendKeys(text);
//		logger.info(text + " Entered Successfully into " + element);
//	} catch (Exception e) {
//		logger.error("Standard methods failed, attempting advanced JavaScript methods: " + e.getMessage());
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		try {
//			js.executeScript(
//					"arguments[0].value = arguments[1];" +
//							"arguments[0].dispatchEvent(new Event('input', {bubbles: true}));" +
//							"arguments[0].dispatchEvent(new Event('change', {bubbles: true}));",
//					element, text
//			);
//			logger.info("Text was successfully set using JavaScript on element: " + element);
//		} catch (Exception jsException) {
//			logger.error("JavaScript method failed to type: " + element, jsException);
//			Assert.fail("Unable to type text after all methods: " + element);
//		}
//	}
//}



    public boolean verifyTheElementVisibility(WebElement element) {
        return fluentWait().until(ExpectedConditions.visibilityOf(element))!=null;
    }

    public boolean verifyElementsNonVisibility(WebElement element) {
        return fluentWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public boolean validateElementVisibilityStopOnFailure(WebElement element) {
        boolean status = false;
        try {
            status = fluentWait().until(ExpectedConditions.visibilityOf(element)).isDisplayed()
                    && fluentWait().until(ExpectedConditions.visibilityOf(element)).isEnabled();
            if (status) {
                logger.info("Element " + element + " is Visible");}
            else {
                throw new ElementNotInteractableException("Element " + element + " is not visible. Execution is stopped now, please check."); }
        } catch (Exception e) {
            logger.error("Unable to validate Element Visibility Stop On Failure : " + element , e);
        }
        return status;
    }
    private void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void verifyandClick_richText(WebElement element, WebElement grandfatherElement) {
        try {
            // First, ensure that all elements with aria-expanded="false" are set to "true"
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(
                        "var elements = arguments[0].querySelectorAll('[aria-expanded=\"false\"]');" +
                                "for (var i = 0; i < elements.length; i++) {" +
                                "  elements[i].setAttribute('aria-expanded', 'true');" +
                                "}", grandfatherElement);
                System.out.println("Set all aria-expanded='false' to 'true' within the grandfatherElement.");
                logger.info("Set all aria-expanded='false' to 'true' within the grandfatherElement.");
            } catch (Exception e) {
                System.out.println("Failed to modify aria-expanded attributes: " + e);
                logger.error("Failed to modify aria-expanded attributes", e);
            }

            // Attempt to click the element directly
            try {
                element.click();
                System.out.println("Successfully clicked on the element using direct .click(): " + element);
                logger.info("Successfully clicked on the element using direct .click(): " + element);
                return; // Exit method if direct click is successful
            } catch (Exception directClickException) {
                System.out.println("Direct .click() failed, trying wait for clickable: " + element + directClickException);
                logger.error("Direct .click() failed, trying wait for clickable: " + element, directClickException);
            }

            // If direct click fails, try waiting for the element to be clickable
            try {
                WebElement clickableElement = fluentWait().until(ExpectedConditions.elementToBeClickable(element));
                clickableElement.click();
                logger.info("Successfully clicked on the element after waiting for it to be clickable: " + element);
                System.out.println("Successfully clicked on the element after waiting for it to be clickable: " + element);
                return; // Exit method if click is successful after wait
            } catch (TimeoutException te) {
                System.out.println("Waiting for clickable element timed out, trying visibility for: " + element + te);
                logger.error("Waiting for clickable element timed out, trying visibility for: " + element, te);
            }

            // If waiting for clickable fails, try visibility check before clicking
            try {
                WebElement visibleElement = fluentWait().until(ExpectedConditions.visibilityOf(element));
                visibleElement.click(); // Attempt direct click after visibility check
                logger.info("Successfully clicked on the element after visibility check: " + element);
                System.out.println("Successfully clicked on the element after visibility check: " + element);
            } catch (Exception e) {
                // If visibility check fails, try direct .click() one more time before resorting to JavaScript
                try {
                    element.click();
                    System.out.println("Successfully clicked on the element using direct .click() again: " + element);
                    logger.info("Successfully clicked using direct .click() again: " + element);
                } catch (Exception lastDirectClickException) {
                    System.out.println("Final direct .click() attempt failed, trying JavaScript click as a last resort: " + element);
                    logger.error("Final direct .click() attempt failed: " + element, lastDirectClickException);

                    // JavaScript click as a final fallback
                    try {
                        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                        jsExecutor.executeScript("arguments[0].click();", element);
                        System.out.println("Successfully performed JavaScript click as last resort for: " + element);
                        logger.info("Successfully performed JavaScript click as last resort for: " + element);
                    } catch (Exception jsClickException) {
                        logger.error("JavaScript click also failed for: " + element, jsClickException);
                        Assert.fail("Failed to click on the element after all attempts, including final JavaScript click: " + element);
                    }
                }
            }
        } finally {
            // Print specific part of the DOM (the element and its parent)
            try {
                if (grandfatherElement.isDisplayed()) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    String elementHtml = (String) js.executeScript("return arguments[0].outerHTML;", grandfatherElement);
                    System.out.println("Element HTML:\n" + elementHtml);
                    logger.info("Element HTML:\n" + elementHtml);
                } else {
                    System.out.println("Element '" + grandfatherElement + "' is not present in the DOM.");
                    logger.warn("Element '" + grandfatherElement + "' is not present in the DOM.");
                }
            } catch (Exception ex) {
                logger.error("Failed to retrieve the specific DOM", ex);
            }
        }
    }






    public void verifyandClick(WebElement element) {
        try {
            // First, try clicking the original element directly
            try {
                element.click();
                System.out.println("Successfully clicked on the element using direct .click(): " + element);
                logger.info("Successfully clicked on the element using direct .click(): " + element);
                return; // Exit method if direct click is successful
            } catch (Exception directClickException) {
                System.out.println("Direct .click() failed, trying wait for clickable: " + element+directClickException);
                logger.error("Direct .click() failed, trying wait for clickable: " + element, directClickException);
            }

            // If direct click fails, try waiting for the element to be clickable
            try {
                WebElement clickableElement = fluentWait().until(ExpectedConditions.elementToBeClickable(element));
                clickableElement.click();
                logger.info("Successfully clicked on the element after waiting for it to be clickable: " + element);
                System.out.println("Successfully clicked on the element after waiting for it to be clickable: " + element);
                return; // Exit method if click is successful after wait
            } catch (TimeoutException te) {
                System.out.println("Waiting for clickable element timed out, trying visibility for: " + element+te);
                logger.error("Waiting for clickable element timed out, trying visibility for: " + element, te);
            }

            // If waiting for clickable fails, try visibility check before clicking
            WebElement visibleElement = fluentWait().until(ExpectedConditions.visibilityOf(element));
            visibleElement.click(); // Attempt direct click after visibility check
            logger.info("Successfully clicked on the element after visibility check: " + element);
            System.out.println("Successfully clicked on the element after visibility check: " + element);

        } catch (Exception e) {
            // Before resorting to JavaScript, try direct .click() one more time
            try {
                element.click();
                System.out.println("Successfully clicked on the element using direct .click() again: " + element);
                logger.info("Successfully clicked using direct .click() again: " + element);
            } catch (Exception lastDirectClickException) {
                System.out.println("Final direct .click() attempt failed, trying JavaScript click as a last resort: " + element);
                logger.error("Final direct .click() attempt failed: " + element, lastDirectClickException);
                // JavaScript click as a final fallback
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", element);
                    System.out.println("Successfully performed JavaScript click as last resort for: " + element);
                    logger.info("Successfully performed JavaScript click as last resort for: " + element);
                } catch (Exception jsClickException) {
                    logger.error("JavaScript click also failed for: " + element, jsClickException);
                    Assert.fail("Failed to click on the element after all attempts, including final JavaScript click: " + element);
                }
            }
        }

    }

//	public void verifyandClick(WebElement element) {
//		boolean isClicked = false;
//
//		try {
//			// Highlight the element for visual debugging
//			highLightElement(element);
//
//			// First, try clicking the original element directly
//			try {
//				element.click();
//				System.out.println("Successfully clicked on the element using direct .click(): " + element);
//				logger.info("Successfully clicked on the element using direct .click(): " + element);
//				isClicked = true;
//			} catch (Exception directClickException) {
//				System.out.println("Direct .click() failed, trying wait for clickable: " + element);
//				logger.error("Direct .click() failed, trying wait for clickable: " + element, directClickException);
//			}
//
//			if (!isClicked) {
//				// If direct click fails, try waiting for the element to be clickable
//				try {
//					WebElement clickableElement = fluentWait().until(ExpectedConditions.elementToBeClickable(element));
//					clickableElement.click();
//					System.out.println("Successfully clicked on the element after waiting for it to be clickable: " + element);
//					logger.info("Successfully clicked on the element after waiting for it to be clickable: " + element);
//					isClicked = true;
//				} catch (TimeoutException te) {
//					System.out.println("Waiting for clickable element timed out, trying visibility for: " + element);
//					logger.error("Waiting for clickable element timed out, trying visibility for: " + element, te);
//				}
//			}
//
//			if (!isClicked) {
//				// If waiting for clickable fails, try visibility check before clicking
//				try {
//					WebElement visibleElement = fluentWait().until(ExpectedConditions.visibilityOf(element));
//					visibleElement.click(); // Attempt direct click after visibility check
//					System.out.println("Successfully clicked on the element after visibility check: " + element);
//					logger.info("Successfully clicked on the element after visibility check: " + element);
//					isClicked = true;
//				} catch (Exception visibilityException) {
//					System.out.println("Visibility check and click failed for: " + element);
//					logger.error("Visibility check and click failed for: " + element, visibilityException);
//				}
//			}
//
//			if (!isClicked) {
//				// Before resorting to JavaScript, try direct .click() one more time
//				try {
//					element.click();
//					System.out.println("Successfully clicked on the element using direct .click() again: " + element);
//					logger.info("Successfully clicked using direct .click() again: " + element);
//					isClicked = true;
//				} catch (Exception lastDirectClickException) {
//					System.out.println("Final direct .click() attempt failed, trying JavaScript click as a last resort: " + element);
//					logger.error("Final direct .click() attempt failed: " + element, lastDirectClickException);
//
//					// JavaScript click as a final fallback
//					try {
//						JavascriptExecutor js = (JavascriptExecutor) driver;
//						js.executeScript("arguments[0].click();", element);
//						System.out.println("Successfully performed JavaScript click as last resort for: " + element);
//						logger.info("Successfully performed JavaScript click as last resort for: " + element);
//						isClicked = true;
//					} catch (Exception jsClickException) {
//						System.out.println("JavaScript click also failed for: " + element);
//						logger.error("JavaScript click also failed for: " + element, jsClickException);
//						Assert.fail("Failed to click on the element after all attempts, including final JavaScript click: " + element);
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			logger.error("Unexpected error while trying to click the element: " + element, e);
//			Assert.fail("Unexpected error while trying to click the element: " + element);
//		}
//
//		if (!isClicked) {
//			System.out.println("Failed to click on the element after all attempts: " + element);
//			Assert.fail("Failed to click on the element after all attempts: " + element);
//		}
//	}




    public boolean verifyandClickradio(WebElement element) {
        boolean flag = false;
        try {
            highLightElement(element);
            fluentWait().until(ExpectedConditions.elementToBeSelected(element));
            element.click();
            logger.info(element + " Checked Successfully");
            flag=true;
        } catch (Exception e) {
            logger.error("Unable to verify and Click radio : " + element , e);
        }
        return flag;
    }

    public boolean verifyTheTitle(final String expectedTitle) {
        String actualTitle = driver.getTitle();
        logger.info("The Actual Title is : "+ actualTitle);
        return actualTitle.equalsIgnoreCase(expectedTitle);
    }
    //try waitUntilElementIsVisible

    // Validate whether element is present or not
    public boolean verifyElementPresent(WebElement element) {
        boolean status = false;
        try {
            status = element.isEnabled();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return status;
    }

    public boolean verifyElementInteractable(WebElement element) {
        boolean status = false;
        try {
            status = element.isDisplayed() && element.isEnabled();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return status;
    }

    public boolean scrollElement(WebElement element) {
        boolean flag = false;
        try {
            executor.executeScript("arguments[0].scrollIntoView();", element);
            logger.info("Successfully Scrolled to the element: " + element);
            flag = true;
        } catch (Exception e) {
            logger.error("Unable to scroll Element : " + element , e);
        }
        return flag;
    }
    public boolean verifyElementNotDisplayed(WebElement element) {
        boolean status = true;
        try {
            // If the element is displayed, set status to false and fail the test
            if (element.isDisplayed()) {
                status = false;
                String errorMessage = "Element is still displayed.";
                System.out.println(errorMessage);
                logger.error(errorMessage);
                Assert.fail(errorMessage);
            }
        } catch (NoSuchElementException e) {
            // If a NoSuchElementException is caught, it means the element is not present, so status remains true
            String infoMessage = "Element is not present: " + e.getMessage();
            System.out.println(infoMessage);
            logger.info(infoMessage);
        } catch (Exception e) {
            // Log any other exceptions and set status to false
            String errorMessage = "An error occurred while checking if element is not displayed: " + e.getMessage();
            System.out.println(errorMessage);
            logger.error(errorMessage);
            status = false;
            Assert.fail(errorMessage);
        }
        return status;
    }


    //	public void verifyElementText(WebElement element, final String expectedText) {
//		// try visibility check before verify text
//		try{
//			WebElement visibleElement = fluentWait().until(ExpectedConditions.visibilityOf(element));
//			logger.info("Successfully clicked on the element after visibility check: " + element);
//			System.out.println("Successfully clicked on the element after visibility check: " + element);
//			String actualElementText = visibleElement.getText();
//			logger.info("The actual Element Text is: " + actualElementText);
//			// Assert that the actual text contains the expected text
//			Assert.assertTrue(actualElementText.contains(expectedText),
//					"The actual text [" + actualElementText + "] does not contain the expected text [" + expectedText + "]");
//
//		}catch (Exception e){
//			String actualElementText = element.getText();
//			logger.info("The actual Element Text is: " + actualElementText);
//			// Assert that the actual text contains the expected text
//			Assert.assertTrue(actualElementText.contains(expectedText),
//					"The actual text [" + actualElementText + "] does not contain the expected text [" + expectedText + "]");
//
//		}
//	}

    public void verifyElementText(WebElement element, final String expectedText) {
        String actualElementText = "";
//		long startTime = System.nanoTime();
        try {
            // Wait for the element to be visible before getting its text
            WebElement visibleElement = fluentWait().until(ExpectedConditions.visibilityOf(element));
            actualElementText = visibleElement.getText();
            logger.info("Element after visibility check: " + element);
            System.out.println("Element after visibility check: " + element);
        } catch (Exception e) {
            System.out.println("Visibility check failed for element, error message: " + e);
            System.out.println("Visibility failed element: [" + element + "] ");
            System.out.println("Visibility failed element actualElementText: [" + actualElementText + "] ");
            logger.error("Visibility check failed for element: " + element, e);

            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                actualElementText = (String) js.executeScript("return arguments[0].textContent;", element);
                System.out.println("Text content using JavaScript: [" + actualElementText + "] ");
            } catch (Exception jsEx) {
                System.out.println("Failed to get text content using JavaScript, error: " + jsEx);
            }
        }

        // Log and perform assertion on the actual text against the expected text
        logger.info("The actual Element Text is: " + actualElementText);
        System.out.println("The actual Element Text is: " + actualElementText);


        // Assert that the actual text contains the expected text
        boolean containsExpectedText = actualElementText.contains(expectedText);
        logger.info("Text contains expected? " + containsExpectedText);

        if (!containsExpectedText) {
            // If the text doesn't contain the expected text, log the failure and assert fail
            String errorMessage = "The actual text [" + actualElementText + "] does not contain the expected text [" + expectedText + "]";
            logger.error(errorMessage);
            System.out.println(errorMessage);
            Assert.fail(errorMessage);
        }
    }
    public void verifyElementNotContainsText(WebElement element, final String expectedText) {
        String actualElementText = "";
//		long startTime = System.nanoTime();
        try {
            // Wait for the element to be visible before getting its text
            WebElement visibleElement = fluentWait().until(ExpectedConditions.visibilityOf(element));
            actualElementText = visibleElement.getText();
            logger.info("Element after visibility check: " + element);
            System.out.println("Element after visibility check: " + element);
        } catch (Exception e) {
            System.out.println("Visibility check failed for element, error message: " + e);
            System.out.println("Visibility failed element: [" + element + "] ");
            System.out.println("Visibility failed element actualElementText: [" + actualElementText + "] ");
            logger.error("Visibility check failed for element: " + element, e);

            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                actualElementText = (String) js.executeScript("return arguments[0].textContent;", element);
                System.out.println("Text content using JavaScript: [" + actualElementText + "] ");
            } catch (Exception jsEx) {
                System.out.println("Failed to get text content using JavaScript, error: " + jsEx);
            }
        }

        // Log and perform assertion on the actual text against the expected text
        logger.info("The actual Element Text is: " + actualElementText);
        System.out.println("The actual Element Text is: " + actualElementText);


        // Assert that the actual text contains the expected text
        boolean containsExpectedText = actualElementText.contains(expectedText);
        logger.info("Text contains expected? " + containsExpectedText);

        if (containsExpectedText) {
            // If the text contain the expected text, log the failure and assert fail
            String errorMessage = "The actual text [" + actualElementText + "] does contain the expected text [" + expectedText + "]";
            logger.error(errorMessage);
            System.out.println(errorMessage);
            Assert.fail(errorMessage);
        }
    }
    //	public static String extractLastNumberSequence(String input, Pattern pattern) {
//		Matcher matcher = pattern.matcher(input);
//		if (matcher.find()) {
//			return matcher.group();
//		}
//		return "";
//	}
    public String extractLastNumberSequence(String input, Pattern pattern) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";

    }
    public boolean returnVerifyElementText(WebElement element, final String expectedText) {
        String actualElementText = "";
        try {
            // Wait for the element to be visible before getting its text
            WebElement visibleElement = fluentWait().until(ExpectedConditions.visibilityOf(element));
            actualElementText = visibleElement.getText();
            logger.info("Element after visibility check: " + visibleElement);
            System.out.println("Element after visibility check: " + visibleElement);
        } catch (Exception e) {
            logger.error("Visibility check failed for element: " + element, e);
            System.out.println("Visibility check failed for element: " + e.getMessage());
            // It might not be a good idea to proceed to get text if visibility wait fails,
            // but if required uncomment the next line.
            // actualElementText = element.getText();
            return false;
        }

        // Log and compare the actual text against the expected text
        logger.info("The actual Element Text is: '" + actualElementText + "'");
        System.out.println("The actual Element Text is: '" + actualElementText + "'");

        // Check if the actual text contains the expected text
        boolean containsExpectedText = actualElementText.contains(expectedText);
        logger.info("Text contains expected? " + containsExpectedText);

        if (!containsExpectedText) {
            // If the text doesn't contain the expected text, log the failure
            String errorMessage = "The actual text ['" + actualElementText + "'] does not contain the expected text ['" + expectedText + "']";
            logger.error(errorMessage);
            System.out.println(errorMessage);
        }

        return containsExpectedText;
    }



    public boolean verifyTheUrl(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        logger.info("The actual Url is : " + actualUrl);
        return actualUrl.equalsIgnoreCase(expectedUrl);
    }

    public String getCurrentUrl() {
        logger.info("Url of focused window is returned successfully");
        String currentUrl = driver.getCurrentUrl();
        System.out.println("currentUrl"+currentUrl);
        return driver.getCurrentUrl();
    }

    //	public void mouseOveronelement(WebElement element) {
//
//		try {
//			JavascriptExecutor js = (JavascriptExecutor) driver;
//			js.executeScript("arguments[0].scrollIntoView();", element);
//			actions.moveToElement(element).build().perform();
//			logger.info("Mouse over on the Element: " + element + " is Successfully");
//		} catch (Exception e) {
//			logger.error("Unable to mouse Over on element : " + element , e);
//			Assert.fail("Unable to scroll To Specific Point: " + element , e);
//		}
//	}
    public void mouseOveronelement(WebElement element) {
        boolean hoverSuccessful = false;

        // First attempt: Scroll the element into view and then hover via JavaScript
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(2000); // Short pause to ensure the element is in view
            actions.moveToElement(element).build().perform();
            System.out.println("Hover effect via JavaScript after scrolling: Success");
            logger.info("Hover effect via JavaScript after scrolling: Success");
            hoverSuccessful = true;
        } catch (Exception e) {
            System.out.println("Hover effect via JavaScript after scrolling failed");
            logger.error("Hover effect via JavaScript after scrolling failed", e);
        }

        // If the first attempt fails, try to hover via JavaScript directly without scrolling
        if (!hoverSuccessful) {
            try {
                hoverUsingJavaScript(element);
                System.out.println("Direct hover effect via JavaScript without scrolling: Success");
                logger.info("Direct hover effect via JavaScript without scrolling: Success");
            } catch (Exception e) {
                System.out.println("Unable to perform mouse over on element directly without scrolling");
                logger.error("Unable to perform mouse over on element directly without scrolling", e);
                Assert.fail("Failed to mouse over on element both with and without scrolling");
            }
        }
    }

    private void hoverUsingJavaScript(WebElement element) throws Exception {
        System.out.println("Attempting JavaScript hover: " + element);
        String script = "var event = new MouseEvent('mouseover', { 'view': window, 'bubbles': true, 'cancelable': true });"
                + "arguments[0].dispatchEvent(event);";
        ((JavascriptExecutor) driver).executeScript(script, element);
    }


    public void mouseOverOnelementWithoutJavascript(WebElement element) {
        try {
//			JavascriptExecutor js = (JavascriptExecutor) driver;
//			js.executeScript("arguments[0].scrollIntoView();", element);
            actions.moveToElement(element).build().perform();
            logger.info("Mouse over on the Element: " + element + " is Successfully");
        } catch (Exception e) {
            logger.error("Unable to mouse Over on element : " + element , e);
            Assert.fail("Failed to mouse over on element both with and without scrolling");
        }
    }

    public void mouseOverOnElementAndClickChild(WebElement parentElement, WebElement childElement) {
        try {
            actions.moveToElement(parentElement).moveToElement(childElement).click().build().perform();
            logger.info("Mouse over on the Element: " + parentElement + " ,and clicked on " + childElement + " Successfully");
        } catch (Exception e) {
            Assert.fail("Unable to mouse Over On Element And Click Child ::::::: ParentElement: " + parentElement +" and ChildElement: " + childElement);
            logger.error("Unable to mouse Over On Element And Click Child ::::::: ParentElement: " + parentElement +" and ChildElement: " + childElement , e);
        }

    }
    //Clarify
    public void doubleclick(WebElement element) {
        actions.doubleClick(element).build().perform();
        logger.info("Double clicked on the Element: " + element + " is Successfully");
    }

    public WebElement modifyingWebElement(String property, String value) {
        WebElement element = null;
        switch (property.toLowerCase()) {
            case "xpath":
                element = driver.findElement(By.xpath(value));
                break;
            case "css":
                element = driver.findElement(By.cssSelector(value));
                break;
            case "classname":
                element = driver.findElement(By.className(value));
                break;
            case "id":
                element = driver.findElement(By.id(value));
                break;
            case "name":
                element = driver.findElement(By.name(value));
                break;
            case "linktext":
                element = driver.findElement(By.linkText(value));
                break;
            case "partiallinktext":
                element = driver.findElement(By.partialLinkText(value));
                break;
            default:
                element = driver.findElement(By.xpath(value));
                break;
        }
        return element;
    }

    public List<WebElement> ReturningListOfElementsUsingXpathLocator(String locator) {
        return driver.findElements(By.xpath(locator));
    }

    public String getTextFromWebElement(WebElement element) {
        return element.getText();
    }

    public void setTextToAWebElement(WebElement element, String value) {
        try {
            fluentWait().until(ExpectedConditions.visibilityOf(element)).sendKeys(value);
            logger.info(value + " set to the Element: " + element + " Successfully");
        } catch (Exception e) {
            logger.error("Unable to set Text To A WebElement : " + element + "Value : " + value, e);
        }

    }

    public void sendKeysToElement(WebElement element, final String keys) {
        try {
            // highLightElement(element);
            fluentWait().until(ExpectedConditions.visibilityOf(element)).sendKeys(keys);
            logger.info(keys + " Sent to the Element: " + element + " Successfully");
        } catch (Exception e) {
            logger.error("Unable to sendKeys To Element : " + element , e);
        }
    }

    public boolean verifyTwoValues(Object expected, Object actual) {
        Assert.assertEquals(expected, actual);
        return true;
    }

    public int getWindowSize() {
        return driver.getWindowHandles().size();
    }

    public void waitForElementVisibilty(WebElement element) {
        try {
            fluentWait().until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.error("Unable to wait For Element Visibilty : " + element , e);
        }
    }

    public void scrollToSpecificPoint(final int x, final int y) {
        try {
            executor.executeScript("window.scrollBy(" + x + "," + y + ")");
            logger.info("Scrolled to the position: " + x + ", " + y);
        } catch (Exception e) {
            logger.error("Unable to scroll To Specific Point" , e);
            Assert.fail("Unable to scroll To Specific Point: " + e );
        }
    }
    public void scrollToBottom() {
        try {
            // Scrolls to the bottom of the page
            executor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            logger.info("Scrolled to the bottom of the page");
        } catch (Exception e) {
            logger.error("Unable to scroll to the bottom of the page", e);
            Assert.fail("Unable to scroll to the bottom of the page: " + e);
        }
    }
    public void scrollToTop() {
        try {
            // Scrolls to the bottom of the page
            executor.executeScript("window.scrollTo(0, 0);");
            logger.info("Scrolled to the top of the page");
        } catch (Exception e) {
            logger.error("Unable to scroll to the top of the page", e);
            Assert.fail("Unable to scroll to the top of the page: " + e);
        }
    }

    public void verifyElementNotPresent(WebElement element) {
        try {
            // Attempt to interact with the element to check its presence
            // For instance, you might try getting its tag name as a lightweight operation
            String tagName = element.getTagName();

            // If no exception is thrown, the element is still present
            Assert.fail("Element is still present on the page when it should not be.");
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            // If a StaleElementReferenceException or NoSuchElementException is caught,
            // this means the element is no longer present in the DOM as expected
            logger.info("Element not present on the page, as expected.");
        }
    }

    public void verifyElementList(WebElement element, String tagName, int expectedNumber) {
        try {
            List<WebElement> rows = element.findElements(By.tagName(tagName));
            int actualCount = rows.size();
            String logMessage = "Verification passed: Found " + actualCount + " <" + tagName + "> element(s) in the parent element as expected.";
            System.out.println(logMessage);
            logger.info(logMessage);
            Assert.assertEquals(actualCount, expectedNumber, "Expected " + expectedNumber + " <" + tagName + "> element(s) in the parent element, but found: " + actualCount);
        } catch (StaleElementReferenceException e) {
            String errorMessage = "StaleElementReferenceException encountered while verifying <" + tagName + "> elements: " + e.getMessage();
            System.out.println(errorMessage);
            logger.error("StaleElementReferenceException encountered while verifying <" + tagName + "> elements: ", e);
            Assert.fail("StaleElementReferenceException: Element is no longer present in the DOM.");
        } catch (NoSuchElementException e) {
            String errorMessage = "NoSuchElementException encountered while verifying <" + tagName + "> elements: " + e.getMessage();
            System.out.println(errorMessage);
            logger.error("NoSuchElementException encountered while verifying <" + tagName + "> elements: ", e);
            Assert.fail("NoSuchElementException: Unable to locate the element.");
        } catch (Exception e) {
            String errorMessage = "Unexpected exception encountered while verifying <" + tagName + "> elements: " + e.getMessage();
            System.out.println(errorMessage);
            logger.error("Unexpected exception encountered while verifying <" + tagName + "> elements: ", e);
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }



    public boolean waitUntilElementIsNotVisible(WebElement element) throws InterruptedException {
        return fluentWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public String getAttributeFromWebElement(WebElement element, final String attribute) {
        return element.getAttribute(attribute);
    }

    public boolean waitUntilElementIsVisible(WebElement element) {
        boolean flag = false;
        fluentWait().until(ExpectedConditions.visibilityOf(element));
        flag = true;
        try {
        } catch (Exception e) {
            logger.error("Unable to wait Until Element Is Visible : " + element , e);
            Assert.fail("Unable to wait Until Element Is Visible : " + element , e );
        }
        return flag;
    }
    public boolean waitUntilElementIsVisibleByWebElement(WebElement element) {
        boolean flag = false;
        try {
            fluentWait().until(ExpectedConditions.visibilityOf(element));
            flag = true;
        } catch (Exception e) {
            String errorMessage = "Unable to wait until element is visible: " + element;
            System.out.println(errorMessage);
            logger.error(errorMessage, e);
            Assert.fail(errorMessage);
        }
        return flag;
    }

    public boolean waitUntilElementIsNotVisibleBy(By element) throws InterruptedException {
        boolean flag = false;
        try {
            fluentWait().until(ExpectedConditions.invisibilityOfElementLocated(element));
            flag = true;
        } catch (Exception e) {
            logger.error("Unable to wait Until Element Is Not VisibleBy : " + element , e);
        } return flag;
    }

    public boolean waitUntilElementIsVisibleBy(By element) throws InterruptedException {
        boolean flag = false;
        try {
            fluentWait().until(ExpectedConditions.visibilityOfElementLocated(element));
            flag = true;
        } catch (Exception e) {
            logger.error("Unable to wait Until Element Is VisibleBy : " + element , e);
        } return flag;
    }

    public boolean clickElementBy(By element) throws InterruptedException {
        boolean flag = false;
        try {
            fluentWait().until(ExpectedConditions.visibilityOfElementLocated(element));
            flag = true;
        } catch (Exception e) {
            logger.error("Unable to click Element By : " + element , e);
        } return flag;
    }

    public void clickElement(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            logger.error("Unable to click Element : " + element , e);
        }
    }

    public void staticwait(int Maxtimetowait) {
        try {
            Thread.sleep(Maxtimetowait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyIfElementIsClickable(WebElement element) {
        boolean flag = false;
        try {
            fluentWait().until(ExpectedConditions.elementToBeClickable(element));
            logger.info("Element: " + element + " is clickable");
            flag = true;
        } catch (Exception e) {
            logger.error("Unable to click Element By : " + element , e);
        } return flag;
    }

    public void waitForPageLoad(int pageLoadTime) {
        /*
         * try { fluentWait().until(webDriver -> ((JavascriptExecutor) webDriver)
         * .executeScript("return document.readyState").equals("complete")); } catch
         * (Exception e) { logger.error("Unable to wait For Page Load for : " +
         * pageLoadTime , e); }
         */
    }

    public void waitforpageload() {
        try {
            executor.executeScript("return window.stop");
        } catch (Exception e) {
            logger.error("Unable to wait for pageload" , e);
        }
    }

    public boolean verifyImagePresent(WebElement element) {
        boolean status = false;
        try {
            status = (Boolean) executor.executeScript(
                    "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                    element);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return status;
    }

    public void clearTextForWebElement(WebElement element) {
        boolean isCleared = false;

        try {
            // First, try waiting for the element to be clickable
            WebElement clickableElement = fluentWait().until(ExpectedConditions.elementToBeClickable(element));
            clickableElement.clear();
            System.out.println("Text is cleared from the WebElement after waiting for it to be clickable.");
            logger.info("Text is cleared from the WebElement after waiting for it to be clickable.");

            isCleared = true;
        } catch (TimeoutException te) {
            System.out.println("Element was not clickable, trying visibility next: " + element + te);
            logger.error("Element was not clickable, trying visibility next: " + element, te);
            try {
                // If the element was not clickable, try waiting for it to be visible
                WebElement visibleElement = fluentWait().until(ExpectedConditions.visibilityOf(element));
                visibleElement.clear();
                System.out.println("Text is cleared from the WebElement after waiting for it to be visible.");
                logger.info("Text is cleared from the WebElement after waiting for it to be visible.");
                isCleared = true;
            } catch (Exception e) {
                System.out.println("Unable to clear text from WebElement after trying clickable and visible waits: " + element + e);
                logger.error("Unable to clear text from WebElement after trying clickable and visible waits: " + element, e);
            }
        } catch (Exception e) {
            System.out.println("Unexpected error while waiting for element to be clickable: " + element + e);
            logger.error("Unexpected error while waiting for element to be clickable: " + element, e);
        }

        if (!isCleared) {
            try {
                element.clear(); // Attempt to clear directly without any explicit wait.
                System.out.println("Text is directly cleared from the WebElement.");
                logger.info("Text is directly cleared from the WebElement.");
                isCleared = true;
            } catch (Exception directClearException) {
                System.out.println("Failed to clear text from WebElement even without explicit wait: " + element + directClearException);
                logger.error("Failed to clear text from WebElement even without explicit wait: " + element, directClearException);
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].value = '';", element);
                    System.out.println("Text is cleared from the WebElement using JavaScript.");
                    logger.info("Text is cleared from the WebElement using JavaScript.");
                    isCleared = true;
                } catch (Exception jsException) {
                    System.out.println("Failed to clear text from WebElement using JavaScript: " + element + jsException);;
                    logger.error("Failed to clear text from WebElement using JavaScript: " + element, jsException);
                    Assert.fail("Failed to clear text from WebElement on all attempts: " + element);
                }
            }
        }
    }

    public void clearTextForRichWebElement() {
        // Use JavaScript to clear the content
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("tinymce.activeEditor.setContent('<p><br data-mce-bogus=\"1\"></p>');");
    }





    public String generateRandomAscii(int n) {
        logger.info("Random Ascii value generated successfully");
        return RandomStringUtils.randomAscii(n);
    }

    public String generateOnlyNumbers(int n) {
        logger.info("Random numbers generated successfully");
        return RandomStringUtils.random(n, false, true);

    }

    public String generateRandomAplphabetic(int n) {
        logger.info("Random Alphbets generated ");
        return RandomStringUtils.randomAlphabetic(n);

    }

    public String generateRandomAlphanumeric(int count) {
        logger.info("Random Alphanumeric value generated successfully");
        return RandomStringUtils.randomAlphanumeric(count);
    }
    public String generateRandomFloatNumber(double lowerBound, double upperBound, String format) {
        Random random = new Random();
        // Generate a random float between lowerBound and upperBound
        double marginNumber = lowerBound + random.nextDouble() * (upperBound - lowerBound);
        // Round to the specified format
        String marginStr = String.format(format, marginNumber);
        return marginStr;
    }
    /*
     * public void closeWindowBasedOnTitle(String title) { boolean currentHandle =
     * switchToWindowByTitle(driver, title); driver.close();
     * driver.switchTo().window(currentHandle); }
     */

    public void switchToWindowTitle(String title) {
        try {
            driver.switchTo().window(title);
        } catch (Exception e) {
            logger.error("Unable to switch To Window Title : " + title , e);
        }
    }

    public void switchToFrameById(String id) {
        try {
            driver.switchTo().frame(id);
        } catch (Exception e) {
            logger.error("Unable to switch To Frame ById : " + id , e);
        }
    }
    public void switchToFrameByXPath(WebElement frameElement) {
        try {
            driver.switchTo().frame(frameElement);
        } catch (Exception e) {
            logger.error("Unable to switch To Frame ById : " + frameElement , e);
        }
    }
    public void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            logger.error("Unable to switch To defaultContent : "  , e);
        }
    }

//	public void switchToFrameById(WebElement id) {
//		getDriver().switchTo().frame(id);
//	}

    public boolean switchToWindowByTitle(WebDriver driver, String windowTitle) {
        boolean flag = false;
        try {
            Set<String> handles = driver.getWindowHandles();
            String currentHandle = driver.getWindowHandle();
            for (String handle : handles) {
                driver.switchTo().window(handle);
                if (windowTitle.equalsIgnoreCase(driver.getTitle())) {
                    break;
                }
            }
            flag = true;
            logger.info("The current title of the window is : "+ currentHandle);
        } catch (Exception e) {
            logger.error("Unable to switch To Window By Title : " + windowTitle , e);
        }
        return flag;
    }

    // Switch the window by index
    public void switchWindowByIndex(final int index) {
        try {
            int windowsCount = 1;
            Set<String> handles = driver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                String handle = iterator.next();
                windowsCount++;
                if (windowsCount == index) {
                    driver.switchTo().window(handle);
                    break;
                }
            }
            logger.info("Window switched Successfully to index " + index);
        }
        catch (Exception e) {
            logger.error("Unable to switch Window By Index : " + index , e);
        }
    }
    public void switchWindowByIndexNew(final int index) {
        try {
            int windowsCount = 0; // Start counting from 0
            Set<String> handles = driver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                String handle = iterator.next();
                if (windowsCount == index) {
                    driver.switchTo().window(handle);
                    logger.info("Window switched Successfully to index " + index);
                    return; // Exit the method after switching
                }
                windowsCount++;
            }
            logger.warn("Window with index " + index + " not found. Available windows: " + windowsCount);
            Assert.fail("Window with index " + index + " not found. Available windows: " + windowsCount); // Fail the test if the window is not found
        } catch (Exception e) {
            logger.error("Unable to switch Window By Index: " + index, e);
            Assert.fail("Unable to switch Window By Index: " + index + ". Error: " + e.getMessage()); // Fail the test upon catching an exception
        }
    }

    public void uploadFileusingSendkeys(WebElement element, String filepath) {
        try {
            element.sendKeys(filepath);
        } catch (Exception e) {
            logger.error("Unable to upload File using Sendkeys in : " + element , e);
        }
    }

    public int getElementHeight(WebElement element) {
        return element.getSize().getHeight();
    }

    public int getElementWidth(WebElement element) {
        return element.getSize().getWidth();
    }

    public String addingcalanderDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime().toString();
    }

    public boolean closeWindowByIndex(int index) {
        boolean flag = false;
        try {
            int windowsCount = 1;
            Set<String> handles = driver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                String handle = iterator.next();
                windowsCount++;
                if (windowsCount == index) {
                    driver.switchTo().window(handle);
                    driver.close();
                    break;
                }
            }
            logger.info("Index " + index + " Window closed Successfully");
            flag = true;
        } catch (Exception e) {
            logger.error("Unable to close Window By Index : " + index ,e);
        }
        return flag;
    }

    public void Presskey(WebElement ele, Keys key) {
        try {
            fluentWait().until(ExpectedConditions.visibilityOf(ele)).sendKeys(key);
        } catch (Exception e) {
            logger.error("Unable to Presskey : " + ele ,e);
        }
    }

    public boolean javascriptclick(WebElement element) {
        boolean flag = false;
        try {
            JavascriptExecutor exe = (JavascriptExecutor) driver;
            Thread.sleep(2500);
            exe.executeScript("arguments[0].click()", element);
            logger.info("Clicked on the element : " + element + " Successfully");
            flag = true;
        } catch (Exception e) {
            logger.error("Unable to click using java script : " + element, e);
        }
        return flag;
    }

    public void javascriptSendkeys(WebElement ele, String val) {
        try {
            executor.executeScript("arguments[0].value='" + val + "';", ele);
        } catch (Exception e) {
            logger.error("Unable to java script Sendkeys : " + ele, e);
        }
    }

    public void highLightElement(WebElement element) {
        try {
            for (int i = 0; i < 2; i++) {

                executor.executeScript(
                        "arguments[0].setAttribute('style','background: white; border: 2px solid red;');", element);
                Thread.sleep(1000);
                executor.executeScript("arguments[0].setAttribute('style','background: clear;');",
                        element);
            }
        } catch (Exception e) {
            logger.error("Unable to highLight Element : " + element, e);
//			Assert.fail("Unable to highLight Element :  " + element , e );

        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setZoom(int zoomPercentage) {
        JavascriptExecutor exe = (JavascriptExecutor) driver;
        exe.executeScript("document.body.style.zoom = '" + zoomPercentage + "%'");
    }
    public boolean verifyElementContainsText(WebElement element, final String expectedText) {
        String actulElementText = element.getText();
        logger.info("The actual Element Text should be : " + actulElementText);
        return actulElementText.contains(expectedText);
    }
    public void getURL(String currentUrl){
        driver.get(currentUrl);
    }
    public void moveDragHandle(WebElement fatherElement,WebElement childElement,WebElement targetElement) {
        try {
            fluentWait().until(ExpectedConditions.visibilityOf(fatherElement));
            fluentWait().until(ExpectedConditions.visibilityOf(childElement));
            fluentWait().until(ExpectedConditions.visibilityOf(targetElement));
            actions.moveToElement(fatherElement).moveToElement(childElement).clickAndHold(childElement).moveToElement(targetElement).release(targetElement).perform();
            logger.info("Mouse over on the Element: " + fatherElement +"clickAndHold"+childElement + " ,and clicked on " + targetElement + " Successfully");

        } catch (Exception e) {
            logger.error("Unable to mouse Over On Element And clickAndHold ::::::: ParentElement: : " + fatherElement +" and ChildElement: " + childElement+" and targetElement: "+targetElement, e);
            Assert.fail("Unable to mouse Over On Element And clickAndHold ::::::: ParentElement: : " + fatherElement +" and ChildElement: " + childElement+" and targetElement: "+targetElement, e);

        }
    }
    public void moveDragHandle_forRegressionChrome(WebElement fatherElement, WebElement childElement, WebElement targetElement) {
        try {
            // Step 1: Move to fatherElement to make childElement visible
            System.out.println("Moving to the father element to make the child element visible.");
            fluentWait().until(ExpectedConditions.visibilityOf(fatherElement));
            actions.moveToElement(fatherElement).perform();
            System.out.println("Moved to the father element.");

            // Step 2: Log childElement visibility and location
            fluentWait().until(ExpectedConditions.visibilityOf(childElement));
            System.out.println("Child Element Location (Visible): " + childElement.getLocation());
            System.out.println("Child Element Size (Visible): " + childElement.getSize());

            // Step 3: Click and hold childElement
            System.out.println("Clicking and holding the child element.");
            actions.moveToElement(childElement).clickAndHold().perform();
            System.out.println("Clicked and holding the child element.");

            // Step 4: Move to targetElement
            fluentWait().until(ExpectedConditions.visibilityOf(targetElement));
            System.out.println("Moving to the target element.");
            actions.moveToElement(targetElement).perform();
            System.out.println("Moved to the target element.");

            // Step 5: Release on targetElement
            System.out.println("Releasing the child element on the target element.");
            actions.release().perform();
            System.out.println("Released the child element on the target element.");

        } catch (TimeoutException e) {
            logger.error("Element not visible in time ::::::: ParentElement: " + fatherElement + " and ChildElement: " + childElement + " and targetElement: " + targetElement, e);
            System.out.println("Timeout occurred: " + e.getMessage());
            Assert.fail("Element not visible in time ::::::: ParentElement: " + fatherElement + " and ChildElement: " + childElement + " and targetElement: " + targetElement, e);
        } catch (Exception e) {
            logger.error("Unable to mouse over on element and clickAndHold ::::::: ParentElement: " + fatherElement + " and ChildElement: " + childElement + " and targetElement: " + targetElement, e);
            System.out.println("Error occurred: " + e.getMessage());
            Assert.fail("Unable to mouse over on element and clickAndHold ::::::: ParentElement: " + fatherElement + " and ChildElement: " + childElement + " and targetElement: " + targetElement, e);
        }
    }



    public boolean verifyListOfText(WebElement element) {
        boolean flag = false;
        try {
            highLightElement(element);
            fluentWait().until(ExpectedConditions.elementToBeClickable(element));
//			JavascriptExecutor exe = (JavascriptExecutor) driver;
//			Thread.sleep(1500);
            System.out.println("fluentWait().until(ExpectedConditions.elementToBeClickable(element))"+element+fluentWait().until(ExpectedConditions.elementToBeClickable(element)));
            fluentWait().until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            Thread.sleep(1500);
            logger.info("Clicked on the element : " + element + " Successfully");
            flag = true;
        } catch (Exception e) {
            logger.error("Unable to verify and Click : " + element , e);
            Assert.fail("Failed to click on the element: " + element);
        }
        return flag;
    }
    public boolean verifyOptionsIsPresentInTheDropDown( final String[] expectedTexts,List<WebElement> listItems ) {
        boolean flag = false;
        try{
            int expectedSize = expectedTexts.length;

            // Verify the size of the list
            Assert.assertEquals(listItems.size(), expectedSize, "The dropdown does not contain the expected number of items.");

            // Verify each list item's text
            for (int i = 0; i < expectedSize; i++) {
                WebElement divElement = listItems.get(i).findElement(By.cssSelector("div.dropdownval > div"));
                String actualText = divElement.getText().trim();

                Assert.assertEquals(actualText, expectedTexts[i], "The item text is not as expected.");

            }}catch (Exception e) {
            logger.error("Unable to verify Option In The DropDown : " + Arrays.toString(expectedTexts), e);
        }return flag;

    }
    public boolean getCurrentDate(String specificDate) {
        boolean flag = false;
        try {
            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Define the formatter in the desired format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");


            // Format the current date
            String formattedCurrentDate = currentDate.format(formatter);

            // Define the specific date string to compare wit

            // Compare the formatted current date with the specific date string
            flag = formattedCurrentDate.equals(specificDate);

            // Output the results
            System.out.println("Current Date (Formatted): " + formattedCurrentDate);


        }catch (Exception e) {
            logger.error("Unable to verify Option In The DropDown : " + specificDate, e);
        }
        return flag;


    }
    public boolean checkButtonDisabled(WebElement element) {

        try {
            fluentWait().until(ExpectedConditions.visibilityOf(element));
            String isDisabled = element.getAttribute("disabled");
            // Directly return true if the element is disabled, implying 'flag' is true
            Assert.assertEquals("true", isDisabled, "The save button is not disabled.");
            return true; // This line will only execute if the above assertion passes
        } catch (AssertionError e) {
            logger.error("The save button is not disabled: " + element, e);
            throw e; // Re-throwing the exception to indicate test failure
        }
    }
    public void checkButtonEnabled(WebElement element) {
        try {
            // Fetch the disabled attribute from the element
            String isDisabled = element.getAttribute("disabled");

            // Assert that the button is enabled
            boolean isEnabled = isDisabled == null || !isDisabled.equals("true");
            Assert.assertTrue(isEnabled, "The button is expected to be enabled but it is not.");

            // Log that the button is enabled
            logger.info("The button is enabled: " + element);
        } catch (AssertionError e) {
            // Log the failure before rethrowing the assertion error
            logger.error("The button is not enabled as expected: " + element, e);
            throw e; // Rethrow the AssertionError to ensure the test fails as expected
        }
    }


    public String getCopiedURL() {
        String copiedLink = null;
        try {
            // Wait for the link to be copied to the clipboard
            Thread.sleep(5000); // Adjust the sleep time as needed

            // Access the clipboard content
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            copiedLink = (String) clipboard.getData(DataFlavor.stringFlavor);

            // Optionally output the copied link
            System.out.println("Copied Link: " + copiedLink);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle thread interruption properly
            System.err.println("Interrupted Exception: " + e.getMessage());
        } catch (UnsupportedFlavorException e) {
            System.err.println("Unsupported Flavor: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
        return copiedLink;
    }
    //	public String returnSharedLink() throws InterruptedException {
//
//		DevTools devTools = driver.getDevTools();
//		devTools.createSession();
//
//		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//		BlockingQueue<String> shareLinkQueue = new ArrayBlockingQueue<>(1);
//
//		devTools.addListener(Network.responseReceived(), response -> {
//			RequestId requestId = response.getRequestId();
//
//			String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
//			if (response.getResponse().getUrl().contains("/sharelink/generate")) {
//				JSONObject jsonResponse = new JSONObject(responseBody);
//				if (jsonResponse.getJSONObject("response").has("data")) {
//					JSONObject data = jsonResponse.getJSONObject("response").getJSONObject("data");
//					if (data.has("shareLinkUrl")) {
//						String shareLinkUrl = data.getString("shareLinkUrl");
//						System.out.println("Extracted Share Link URL: " + shareLinkUrl);
//						shareLinkQueue.add(shareLinkUrl); // Store the URL in the queue
//					}
//				}
//			}
//		});
//
//		// Trigger the network request that generates the share link
//		driver.findElement(By.id("generate-link-button")).click();
//
//		// Wait for the share link to be captured or timeout
//		return shareLinkQueue.poll(30, TimeUnit.SECONDS); // Adjust the timeout as necessary
//	}
    public String getAttributeTitle(WebElement element) {
        boolean flag = false;
        logger.info("Url of focused window is returned successfully");
        String title = element.getAttribute("title");
        System.out.println("title"+title);
        return title;
    }
    public static String addFormattedNumbers(String number1, String formattedNumber2) {
        try {
            // Parse the first string directly as it is not formatted with commas.
            double num1 = Double.parseDouble(number1);

            // Create a NumberFormat object to parse the formatted number.
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            double num2 = format.parse(formattedNumber2).doubleValue();

            // Add the two numbers.
            double result = num1 + num2;

            // Format the result back to a string with commas and two decimal places.
            return format.format(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}