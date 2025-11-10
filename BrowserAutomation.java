package demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BrowserAutomation {
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main(String[] args) {
        try {
            initializeDriver(args);
            fillForm();
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private static void initializeDriver(String[] args) {
        try {
            String url = (args != null && args.length > 0) ? args[0] : "https://testautomationpractice.blogspot.com/";
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            driver.get(url);
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
            
            System.out.println("Opened: " + url);
            System.out.println("Page title: " + driver.getTitle() + "--------------------------------");
        } catch (TimeoutException te) {
            throw new RuntimeException("Page load timeout: " + te.getMessage());
        } catch (WebDriverException we) {
            throw new RuntimeException("Failed to initialize browser: " + we.getMessage());
        }
    }

    private static void fillForm() {
        try {
            // Find and fill name
            WebElement nameTextBox = waitAndLocate("//input[@id='name']", "name field");
            sendKeysWithVerification(nameTextBox, "KingOOstring", "name");
            nameTextBox.sendKeys(Keys.TAB);
                Thread.sleep(300); 

            // Find and fill email
            WebElement emailTextBox = waitAndLocate("//input[@id='email']", "email field");
            sendKeysWithVerification(emailTextBox, "abcd@gamail.con", "email");
            // Thread.sleep(3000); 

            // Find and fill phone
            WebElement phoneTextBox = waitAndLocate("//input[@id='phone']", "phone field");
            sendKeysWithVerification(phoneTextBox, "1234567890", "phone");
            // Thread.sleep(3000); 

            // Find and fill address
            WebElement addressTextBox = waitAndLocate("//textarea[@id='textarea']", "address field");
            sendKeysWithVerification(addressTextBox, "123, ABCD Street, XYZ-987654", "address");
            // Thread.sleep(3000); 

            // Find and click gender radio button
            WebElement genderRadioButton = waitAndLocate(
                "//div[@class='form-check form-check-inline']//label[text()='Male']", 
                "gender radio button"
            );
            clickWithVerification(genderRadioButton, "gender selection");
            // Thread.sleep(9000); 

            System.out.println("Form filled successfully");

        } catch (TimeoutException te) {
            throw new RuntimeException("Timeout while filling form: " + te.getMessage());
        } catch (ElementNotInteractableException ei) {
            throw new RuntimeException("Element not interactable: " + ei.getMessage());
        } catch (StaleElementReferenceException se) {
            throw new RuntimeException("Element became stale: " + se.getMessage());
        }   catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting for focus shift", ie);
            }
    }

    private static WebElement waitAndLocate(String xpath, String elementName) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        } catch (TimeoutException te) {
            throw new RuntimeException("Timeout waiting for " + elementName + ": " + te.getMessage());
        }
    }

    private static void sendKeysWithVerification(WebElement element, String text, String fieldName) {
        try {
            element.clear();
            element.sendKeys(text);
            wait.until(driver -> element.getAttribute("value").equals(text));
            System.out.println("Successfully entered text in " + fieldName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter text in " + fieldName + ": " + e.getMessage());
        }
    }

    private static void clickWithVerification(WebElement element, String elementName) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            System.out.println("Successfully clicked " + elementName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click " + elementName + ": " + e.getMessage());
        }
    }

    private static void cleanup() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Browser closed successfully---------------------------");
            } catch (WebDriverException we) {
                System.err.println("Error closing browser: " + we.getMessage());
            }
        }
    }
}