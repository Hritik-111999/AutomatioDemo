package demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            WebElement nameTextBox = waitAndLocate("//input[@id='name']", "name field");
            sendKeysWithVerification(nameTextBox, "KingOOstring", "name");
            nameTextBox.sendKeys(Keys.TAB);
            Thread.sleep(300);

            WebElement emailTextBox = waitAndLocate("//input[@id='email']", "email field");
            sendKeysWithVerification(emailTextBox, "abcd@gamail.con", "email");
      
            WebElement phoneTextBox = waitAndLocate("//input[@id='phone']", "phone field");
            sendKeysWithVerification(phoneTextBox, "1234567890", "phone");
        
            WebElement addressTextBox = waitAndLocate("//textarea[@id='textarea']", "address field");
            sendKeysWithVerification(addressTextBox, "123, ABCD Street, XYZ-987654", "address");

            WebElement genderRadioButton = waitAndLocate(
                    "//div[@class='form-check form-check-inline']//label[text()='Male']",
                    "gender radio button");
            clickWithVerification(genderRadioButton, "gender selection");
            System.out.println("Form filled successfully");

            WebElement countryDropDown = waitAndLocate("//select[@id='country']", "country dropdown");
            clickWithVerification(countryDropDown, "country dropdown");

            Select dropdown = new Select(countryDropDown);
            dropdown.selectByVisibleText("France");
            System.out.println("Selected country: France");

            WebElement colorElement = waitAndLocate("//select[@id='colors']", "colors list");
            Select colorSelect = new Select(colorElement);
            colorSelect.selectByVisibleText("Blue");
            System.out.println("Selected color: Blue");
            Thread.sleep(3000);
            colorElement.sendKeys(Keys.RETURN);

            WebElement sortedList = driver.findElement(By.xpath("//select[@id=\"animals\"]"));
            Select animalSelect = new Select(sortedList);
            animalSelect.selectByVisibleText("Dog");

            // Date picker 1
            WebElement date1 = driver.findElement(By.xpath("//input[@id=\"datepicker\"]"));
            date1.click();
            Thread.sleep(3000);
            String targetYear = "2024";
            String targetMonth = "October";
            String targetDay = "15";

            while (true) {
                String displayedMonth = driver.findElement(By.className("ui-datepicker-month")).getText();
                String displayedYear = driver.findElement(By.className("ui-datepicker-year")).getText();

                if (displayedMonth.equals(targetMonth) && displayedYear.equals(targetYear)) {
                    break;
                }
                int displayedY = Integer.parseInt(displayedYear);
                int targetY = Integer.parseInt(targetYear);

                if (displayedY > targetY ||
                        (displayedY == targetY && monthToNumber(displayedMonth) > monthToNumber(targetMonth))) {
                    driver.findElement(By.xpath("//a[@data-handler='prev']")).click();
                } else {
                    driver.findElement(By.xpath("//a[@data-handler='next']")).click();
                }
            }
            driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//a[text()='" + targetDay + "']"))
                    .click();

            String selectedDate = date1.getAttribute("value");
            System.out.println("Selected Date: " + selectedDate);
            Thread.sleep(1000);

            // Date picker 2
            String date = "11/11/2017";
            String[] parts = date.split("/");
            String expDay = parts[0];
            String expMonth = parts[1];
            String expYear = parts[2];

            WebElement dateInput = driver.findElement(By.id("txtDate"));
            dateInput.click();
            Thread.sleep(1000);

            By monthDropdown = By.className("ui-datepicker-month");
            By yearDropdown = By.className("ui-datepicker-year");

            Select yearSelect = new Select(driver.findElement(yearDropdown));
            yearSelect.selectByVisibleText(expYear);

            String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
            String monthName = months[Integer.parseInt(expMonth) - 1];

            Select monthSelect = new Select(driver.findElement(monthDropdown));
            monthSelect.selectByVisibleText(monthName);
            Thread.sleep(1000);
            List<WebElement> days = driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']//a"));
            for (WebElement d : days) {
                if (d.getText().equals(expDay)) {
                    d.click();
                    Thread.sleep(1000);
                    break;
                }
            }
            // Date picker 3
            WebElement dateInput1 = driver
                    .findElement(By.xpath("//div[@class=\"date-picker-box\"]//input[@id='start-date']"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", dateInput1);
            String dateValue1 = "2020-11-11";
            js.executeScript("arguments[0].value = arguments[1];", dateInput1, dateValue1);
            Thread.sleep(3000);
            WebElement dateInput2 = driver
                    .findElement(By.xpath("//div[@class=\"date-picker-box\"]//input[@id='end-date']"));
            js.executeScript("arguments[0].click();", dateInput2);
            String dateValue2 = "2020-11-12";
            js.executeScript("arguments[0].value = arguments[1];", dateInput2, dateValue2);
            WebElement submitBtn = driver
                    .findElement(By.xpath("//div[@class=\"date-picker-box\"]//button[@class='submit-btn']"));
            submitBtn.click();
            Thread.sleep(3000);

            // Static Web Table
            String targetTextInTable = "Animesh";

            WebElement table1 = waitAndLocate("//table[@name='BookTable']", "bool table");
            List<WebElement> headers = table1.findElements(By.tagName("th"));
            for (WebElement header : headers) {
                System.out.println("header: " + header.getText());
            }
            List<WebElement> allrows = table1.findElements(By.tagName("tr"));
            for (WebElement row : allrows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                for (WebElement cell : cells) {
                    System.out.print(cell.getText() + " || ");
                    if (cell.getText().equals(targetTextInTable)) {
                        System.out.println("\n -- Found the target text: " + targetTextInTable);
                    }
                }
                System.out.println();
            }
            // Dynamic Web Table
            String targetTextInDynamicTable = "Chrome";
            WebElement dynamicTable = waitAndLocate("//table[@id='taskTable']", "dynamic table");
            List<WebElement> dynamicRows = dynamicTable.findElements(By.tagName("tr"));
            for (WebElement row : dynamicRows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                for (WebElement cell : cells) {
                    System.out.print(cell.getText() + " || ");
                    if (cell.getText().contains(targetTextInDynamicTable)) {
                        // System.out.println("\n -- Found the target text in dynamic table: " +
                        // targetTextInDynamicTable);

                    }
                }
                System.out.println();
            }

            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");

            List<WebElement> headersDynamicTable = driver.findElements(
                    By.xpath("//table[@id='taskTable']/thead/tr/th"));

            Map<String, Integer> headerIndex = new HashMap<>();

            for (int i = 0; i < headersDynamicTable.size(); i++) {
                String headerText = headersDynamicTable.get(i).getText().trim();
                headerIndex.put(headerText, i + 1);
            }

            String chromeCpu = driver.findElement(
                    By.xpath("//table[@id='taskTable']/tbody/tr[td[1]='Chrome']/td["
                            + headerIndex.get("CPU (%)") + "]"))
                    .getText();

            String firefoxMemory = driver.findElement(
                    By.xpath("//table[@id='taskTable']/tbody/tr[td[1]='Firefox']/td["
                            + headerIndex.get("Memory (MB)") + "]"))
                    .getText();

            String chromeNetwork = driver.findElement(
                    By.xpath("//table[@id='taskTable']/tbody/tr[td[1]='Chrome']/td["
                            + headerIndex.get("Network (Mbps)") + "]"))
                    .getText();

            String firefoxDisk = driver.findElement(
                    By.xpath("//table[@id='taskTable']/tbody/tr[td[1]='Firefox']/td["
                            + headerIndex.get("Disk (MB/s)") + "]"))
                    .getText();

            System.out.println("CPU load of Chrome process: " + chromeCpu);
            System.out.println("Memory Size of Firefox process: " + firefoxMemory);
            System.out.println("Network speed of Chrome process: " + chromeNetwork);
            System.out.println("Disk space of Firefox process: " + firefoxDisk);

            // Paginated Web Table
            String targetName = "Fitness Tracker";

            boolean found = false;

            for (int page = 1; page <= 4; page++) {

                System.out.println("Checking Page: " + page);

                driver.findElement(By.xpath("//a[text()='" + page + "']")).click();
                Thread.sleep(800);

                List<WebElement> rows = driver.findElements(By.xpath("//table[@id='productTable']/tbody/tr"));

                for (WebElement row : rows) {

                    String name = row.findElement(By.xpath("./td[2]")).getText().trim();

                    if (name.equalsIgnoreCase(targetName)) {
                        System.out.println("Found item: " + name);

                        WebElement checkbox = row.findElement(By.xpath("./td[4]/input"));
                        checkbox.click();

                        System.out.println("Checkbox clicked for " + name);

                        found = true;
                        break;
                    }
                }
                if (found) {
                    break; // break page loop
                }
            }
            if (!found) {
                System.out.println("Item NOT found in any page.");
            }

        } catch (TimeoutException te) {
            throw new RuntimeException("Timeout while filling form: " + te.getMessage());
        } catch (ElementNotInteractableException ei) {
            throw new RuntimeException("Element not interactable: " + ei.getMessage());
        } catch (StaleElementReferenceException se) {
            throw new RuntimeException("Element became stale: " + se.getMessage());
        } catch (InterruptedException ie) {
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

    private static int monthToNumber(String monthName) {
        switch (monthName) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return 0;
        }
    }
}