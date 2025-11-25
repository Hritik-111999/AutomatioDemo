package demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                    break; 
                }
            }
            if (!found) {
                System.out.println("Item NOT found in any page.");
            }

            // Checkboxes for Days
            testCheckboxes();
            
            // File Upload
            testFileUpload();
            
            // Drag and Drop
            testDragAndDrop();
            
            // Slider
            testSlider();
            
            // SVG Elements
            testSVGElements();
            
            // Scrolling Dropdown
            testScrollingDropdown();
            
            // Alerts & Popups
            testAlertsAndPopups();
            
            // Mouse Hover
            testMouseHover();
            
            // Double Click
            testDoubleClick();
            
            // Dynamic Button
            testDynamicButton();
            
            // New Tab
            testNewTab();
            
            // Popup Windows
            testPopupWindows();
            
            // Labels and Links
            testLabelsAndLinks();
            
            // ShadowDOM
            testShadowDOM();

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

    private static void testCheckboxes() {
        try {
            System.out.println("\n=== Testing Checkboxes ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
            Thread.sleep(1000);
            
            // Select Sunday and Monday checkboxes
            WebElement sundayCheckbox = waitAndLocate("//input[@id='sunday']", "Sunday checkbox");
            if (!sundayCheckbox.isSelected()) {
                clickWithVerification(sundayCheckbox, "Sunday checkbox");
            }
            
            WebElement mondayCheckbox = waitAndLocate("//input[@id='monday']", "Monday checkbox");
            if (!mondayCheckbox.isSelected()) {
                clickWithVerification(mondayCheckbox, "Monday checkbox");
            }
            
            System.out.println("Checkboxes selected successfully");
        } catch (Exception e) {
            System.err.println("Error testing checkboxes: " + e.getMessage());
        }
    }

    private static void testFileUpload() {
        try {
            System.out.println("\n=== Testing File Upload ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");
            Thread.sleep(1000);
            
            // Single file upload
            WebElement singleFileInput = waitAndLocate("//input[@id='singleFileInput']", "single file input");
            String filePath = System.getProperty("user.home") + "/test-file.txt";
            // Create a dummy file for testing
            java.io.File testFile = new java.io.File(filePath);
            if (!testFile.exists()) {
                java.io.FileWriter writer = new java.io.FileWriter(testFile);
                writer.write("Test file content for automation testing");
                writer.close();
            }
             singleFileInput.sendKeys(filePath);
            Thread.sleep(500);
            
            WebElement singleFileForm = waitAndLocate("//form[@id='singleFileForm']//button", "single file upload button");
            clickWithVerification(singleFileForm, "single file upload button");
            Thread.sleep(1000);
            
            WebElement status = driver.findElement(By.id("singleFileStatus"));
            System.out.println("File upload status: " + status.getText());
            
            // Clean up test file
            if (testFile.exists()) {
                testFile.delete();
            }
            
            System.out.println("File upload test completed");
        } catch (Exception e) {
            System.err.println("Error testing file upload: " + e.getMessage());
        }
    }

    private static void testDragAndDrop() {
        try {
            System.out.println("\n=== Testing Drag and Drop ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 800);");
            Thread.sleep(1000);
            
            WebElement draggable = waitAndLocate("//div[@id='draggable']", "draggable element");
            WebElement droppable = waitAndLocate("//div[@id='droppable']", "droppable element");
            
            Actions actions = new Actions(driver);
            actions.dragAndDrop(draggable, droppable).perform();
            Thread.sleep(2000);
            
            String dropText = droppable.findElement(By.tagName("p")).getText();
            System.out.println("Droppable text after drop: " + dropText);
            
            if (dropText.contains("Dropped")) {
                System.out.println("Drag and drop successful");
            }
        } catch (Exception e) {
            System.err.println("Error testing drag and drop: " + e.getMessage());
        }
    }

    private static void testSlider() {
        try {
            System.out.println("\n=== Testing Slider ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000);");
            Thread.sleep(1000);
            
            WebElement slider = waitAndLocate("//div[@id='slider-range']", "slider");
            WebElement amountInput = driver.findElement(By.id("amount"));
            
            String initialValue = amountInput.getAttribute("value");
            System.out.println("Initial slider value: " + initialValue);
            
            // Get slider handles
            List<WebElement> handles = slider.findElements(By.xpath(".//span[contains(@class, 'ui-slider-handle')]"));
            
            if (handles.size() >= 2) {
                Actions actions = new Actions(driver);
                // Move first handle
                actions.clickAndHold(handles.get(0))
                       .moveByOffset(50, 0)
                       .release()
                       .perform();
                Thread.sleep(1000);
                
                // Move second handle
                actions.clickAndHold(handles.get(1))
                       .moveByOffset(-30, 0)
                       .release()
                       .perform();
                Thread.sleep(1000);
                
                String newValue = amountInput.getAttribute("value");
                System.out.println("New slider value: " + newValue);
                System.out.println("Slider test completed");
            }
        } catch (Exception e) {
            System.err.println("Error testing slider: " + e.getMessage());
        }
    }

    private static void testSVGElements() {
        try {
            System.out.println("\n=== Testing SVG Elements ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1200);");
            Thread.sleep(1000);
            
            // Find SVG elements
            List<WebElement> svgElements = driver.findElements(By.xpath("//div[@class='svg-container']//svg"));
            System.out.println("Found " + svgElements.size() + " SVG elements");
            
            for (int i = 0; i < svgElements.size(); i++) {
                WebElement svg = svgElements.get(i);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", svg);
                Thread.sleep(500);
                
                // Click on SVG element
                Actions actions = new Actions(driver);
                actions.moveToElement(svg).click().perform();
                Thread.sleep(500);
                
                System.out.println("Clicked on SVG element " + (i + 1));
            }
            
            System.out.println("SVG elements test completed");
        } catch (Exception e) {
            System.err.println("Error testing SVG elements: " + e.getMessage());
        }
    }

    private static void testScrollingDropdown() {
        try {
            System.out.println("\n=== Testing Scrolling Dropdown ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1400);");
            Thread.sleep(1000);
            
            WebElement comboBox = waitAndLocate("//input[@id='comboBox']", "combo box");
            clickWithVerification(comboBox, "combo box");
            Thread.sleep(1000);
            
            // Wait for dropdown to be visible
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown")));
            
            // Scroll through dropdown
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollTop = 500;", dropdown);
            Thread.sleep(1000);
            
            // Select an item from the middle of the list
            List<WebElement> options = dropdown.findElements(By.xpath(".//div[@class='option']"));
            if (options.size() > 50) {
                WebElement option = options.get(50);
                // Scroll option into view
                js.executeScript("arguments[0].scrollIntoView(true);", option);
                Thread.sleep(500);
                clickWithVerification(option, "dropdown option");
                Thread.sleep(1000);
                System.out.println("Selected option: " + comboBox.getAttribute("value"));
            } else {
                // If not enough options, select the last one
                if (options.size() > 0) {
                    WebElement option = options.get(options.size() - 1);
                    js.executeScript("arguments[0].scrollIntoView(true);", option);
                    Thread.sleep(500);
                    clickWithVerification(option, "dropdown option");
                    Thread.sleep(1000);
                    System.out.println("Selected option: " + comboBox.getAttribute("value"));
                }
            }
            
            System.out.println("Scrolling dropdown test completed");
        } catch (Exception e) {
            System.err.println("Error testing scrolling dropdown: " + e.getMessage());
        }
    }

    private static void testAlertsAndPopups() {
        try {
            System.out.println("\n=== Testing Alerts & Popups ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600);");
            Thread.sleep(1000);
            
            // Simple Alert
            WebElement alertBtn = waitAndLocate("//button[@id='alertBtn']", "alert button");
            clickWithVerification(alertBtn, "alert button");
            Thread.sleep(1000);
            
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
            Thread.sleep(1000);
            
            // Confirmation Alert
            WebElement confirmBtn = waitAndLocate("//button[@id='confirmBtn']", "confirm button");
            clickWithVerification(confirmBtn, "confirm button");
            Thread.sleep(1000);
            
            Alert confirmAlert = driver.switchTo().alert();
            System.out.println("Confirm alert text: " + confirmAlert.getText());
            confirmAlert.accept();
            Thread.sleep(1000);
            
            WebElement demo = driver.findElement(By.id("demo"));
            System.out.println("Demo text after confirm: " + demo.getText());
            
            // Prompt Alert
            WebElement promptBtn = waitAndLocate("//button[@id='promptBtn']", "prompt button");
            clickWithVerification(promptBtn, "prompt button");
            Thread.sleep(1000);
            
            Alert promptAlert = driver.switchTo().alert();
            System.out.println("Prompt alert text: " + promptAlert.getText());
            promptAlert.sendKeys("Test User");
            promptAlert.accept();
            Thread.sleep(1000);
            
            System.out.println("Demo text after prompt: " + demo.getText());
            System.out.println("Alerts & Popups test completed");
        } catch (Exception e) {
            System.err.println("Error testing alerts and popups: " + e.getMessage());
        }
    }

    private static void testMouseHover() {
        try {
            System.out.println("\n=== Testing Mouse Hover ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 800);");
            Thread.sleep(1000);
            
            WebElement hoverButton = waitAndLocate("//button[@class='dropbtn' and text()='Point Me']", "hover button");
            WebElement dropdownContent = driver.findElement(By.xpath("//div[@class='dropdown-content']"));
            
            // Check if dropdown is initially hidden
            String initialDisplay = dropdownContent.getCssValue("display");
            System.out.println("Initial dropdown display: " + initialDisplay);
            
            Actions actions = new Actions(driver);
            actions.moveToElement(hoverButton).perform();
            Thread.sleep(1000);
            
            // Verify dropdown is now visible
            String afterHoverDisplay = dropdownContent.getCssValue("display");
            System.out.println("After hover dropdown display: " + afterHoverDisplay);
            
            // Click on a dropdown item
            WebElement mobilesLink = waitAndLocate("//div[@class='dropdown-content']//a[text()='Mobiles']", "Mobiles link");
            clickWithVerification(mobilesLink, "Mobiles link");
            Thread.sleep(1000);
            
            System.out.println("Mouse hover test completed");
        } catch (Exception e) {
            System.err.println("Error testing mouse hover: " + e.getMessage());
        }
    }

    private static void testDoubleClick() {
        try {
            System.out.println("\n=== Testing Double Click ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000);");
            Thread.sleep(1000);
            
            WebElement field1 = waitAndLocate("//input[@id='field1']", "field1");
            WebElement field2 = waitAndLocate("//input[@id='field2']", "field2");
            WebElement copyButton = waitAndLocate("//button[text()='Copy Text']", "copy button");
            
            String originalText = field1.getAttribute("value");
            System.out.println("Original text in field1: " + originalText);
            
            Actions actions = new Actions(driver);
            actions.doubleClick(copyButton).perform();
            Thread.sleep(1000);
            
            String copiedText = field2.getAttribute("value");
            System.out.println("Copied text in field2: " + copiedText);
            
            if (originalText.equals(copiedText)) {
                System.out.println("Double click test successful - text copied correctly");
            }
        } catch (Exception e) {
            System.err.println("Error testing double click: " + e.getMessage());
        }
    }

    private static void testDynamicButton() {
        try {
            System.out.println("\n=== Testing Dynamic Button ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 400);");
            Thread.sleep(1000);
            
            WebElement dynamicButton = waitAndLocate("//button[@name='start' and contains(@class, 'start')]", "dynamic button");
            
            String initialText = dynamicButton.getText();
            String initialName = dynamicButton.getAttribute("name");
            System.out.println("Initial button state - Text: " + initialText + ", Name: " + initialName);
            
            clickWithVerification(dynamicButton, "dynamic button");
            Thread.sleep(1000);
            
            String afterClickText = dynamicButton.getText();
            String afterClickName = dynamicButton.getAttribute("name");
            System.out.println("After click button state - Text: " + afterClickText + ", Name: " + afterClickName);
            
            // Click again to toggle back
            clickWithVerification(dynamicButton, "dynamic button");
            Thread.sleep(1000);
            
            String finalText = dynamicButton.getText();
            System.out.println("Final button state - Text: " + finalText);
            
            System.out.println("Dynamic button test completed");
        } catch (Exception e) {
            System.err.println("Error testing dynamic button: " + e.getMessage());
        }
    }

    private static void testNewTab() {
        try {
            System.out.println("\n=== Testing New Tab ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600);");
            Thread.sleep(1000);
            
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);
            
            WebElement newTabButton = waitAndLocate("//button[text()='New Tab']", "new tab button");
            clickWithVerification(newTabButton, "new tab button");
            Thread.sleep(2000);
            
            Set<String> windows = driver.getWindowHandles();
            System.out.println("Total windows open: " + windows.size());
            
            // Switch to new tab
            for (String window : windows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    Thread.sleep(1000);
                    System.out.println("Switched to new tab - Title: " + driver.getTitle());
                    driver.close();
                    break;
                }
            }
            
            // Switch back to original window
            driver.switchTo().window(originalWindow);
            System.out.println("New tab test completed");
        } catch (Exception e) {
            System.err.println("Error testing new tab: " + e.getMessage());
        }
    }

    private static void testPopupWindows() {
        try {
            System.out.println("\n=== Testing Popup Windows ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 800);");
            Thread.sleep(1000);
            
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);
            
            WebElement popupButton = waitAndLocate("//button[@id='PopUp' and text()='Popup Windows']", "popup button");
            clickWithVerification(popupButton, "popup button");
            Thread.sleep(3000);
            
            Set<String> windows = driver.getWindowHandles();
            System.out.println("Total windows open: " + windows.size());
            
            // Close popup windows
            for (String window : windows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    Thread.sleep(1000);
                    System.out.println("Popup window - Title: " + driver.getTitle());
                    driver.close();
                }
            }
            
            // Switch back to original window
            driver.switchTo().window(originalWindow);
            System.out.println("Popup windows test completed");
        } catch (Exception e) {
            System.err.println("Error testing popup windows: " + e.getMessage());
        }
    }

    private static void testLabelsAndLinks() {
        try {
            System.out.println("\n=== Testing Labels and Links ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1600);");
            Thread.sleep(1000);
            
            // Test Labels
            WebElement samsungLabel = waitAndLocate("//label[@id='samsung']", "Samsung label");
            System.out.println("Samsung label text: " + samsungLabel.getText());
            clickWithVerification(samsungLabel, "Samsung label");
            Thread.sleep(500);
            
            WebElement realmeLabel = waitAndLocate("//label[@id='realme']", "Real Me label");
            System.out.println("Real Me label text: " + realmeLabel.getText());
            clickWithVerification(realmeLabel, "Real Me label");
            Thread.sleep(500);
            
            // Test Links
            WebElement appleLink = waitAndLocate("//a[@id='apple']", "Apple link");
            String appleHref = appleLink.getAttribute("href");
            System.out.println("Apple link href: " + appleHref);
            
            WebElement lenovoLink = waitAndLocate("//a[@id='lenovo']", "Lenovo link");
            String lenovoHref = lenovoLink.getAttribute("href");
            System.out.println("Lenovo link href: " + lenovoHref);
            
            // Test broken links (just verify they exist, don't click)
            List<WebElement> brokenLinks = driver.findElements(By.xpath("//div[@id='broken-links']//a"));
            System.out.println("Found " + brokenLinks.size() + " broken links");
            
            for (WebElement link : brokenLinks) {
                String href = link.getAttribute("href");
                System.out.println("Broken link: " + link.getText() + " - " + href);
            }
            
            System.out.println("Labels and Links test completed");
        } catch (Exception e) {
            System.err.println("Error testing labels and links: " + e.getMessage());
        }
    }

    private static void testShadowDOM() {
        try {
            System.out.println("\n=== Testing ShadowDOM ===");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(2000);
            
            // Access ShadowDOM elements using JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // Get shadow root
            String shadowContent = (String) js.executeScript(
                "return document.getElementById('shadow_host').shadowRoot.querySelector('#shadow_content').textContent;"
            );
            System.out.println("Shadow content: " + shadowContent);
            
            // Get nested shadow content
            String nestedShadowContent = (String) js.executeScript(
                "return document.getElementById('shadow_host').shadowRoot.getElementById('nested_shadow_host').shadowRoot.querySelector('#nested_shadow_content').textContent;"
            );
            System.out.println("Nested shadow content: " + nestedShadowContent);
            
            // Click on shadow DOM link
            js.executeScript(
                "document.getElementById('shadow_host').shadowRoot.querySelector('a').click();"
            );
            Thread.sleep(2000);
            
            // Switch back if new tab opened
            Set<String> windows = driver.getWindowHandles();
            if (windows.size() > 1) {
                String currentWindow = driver.getWindowHandle();
                for (String window : windows) {
                    if (!window.equals(currentWindow)) {
                        driver.switchTo().window(window);
                        driver.close();
                    }
                }
                driver.switchTo().window((String) windows.toArray()[0]);
            }
            
            System.out.println("ShadowDOM test completed");
        } catch (Exception e) {
            System.err.println("Error testing ShadowDOM: " + e.getMessage());
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