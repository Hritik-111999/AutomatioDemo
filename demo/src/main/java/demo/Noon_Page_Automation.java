package demo;
import java.time.Duration;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

// public class Noon_Page_Automation {
//     public static void main(String[] args) throws InterruptedException {
//         WebDriver driver = new ChromeDriver();
//         driver.get("https://www.noon.com/uae-en/");
//         driver.manage().window().maximize();

//         // WebElement element1 = driver.findElement(By.xpath(""));
//         // WebElement element2 = driver.findElement(By.xpath(""));
//         WebElement randomElementForScrollingPage = driver.findElement(By.xpath("(//h2[@class='ProductDetailsSection-module-scss-module__Y6u1Qq__title ProductDetailsSection-module-scss-module__Y6u1Qq__isPboxRedesignEnabled'])[1]"));
//         JavascriptExecutor js = (JavascriptExecutor) driver;
//         js.executeScript("arguments[0].scrollIntoView(true);", randomElementForScrollingPage);
//         WebDriverWait waitForScrolling = new WebDriverWait(driver, Duration.ofSeconds(5));
//         waitForScrolling.until(ExpectedConditions.visibilityOf(randomElementForScrollingPage));
//         randomElementForScrollingPage.click();
//         WebElement nextButtonElement = 
//             driver.findElement(
//                 By.xpath("(//button[@class='EmblaCarouselArrowButtons-module-scss-module__YNpIRW__emblaButton EmblaCarouselArrowButtons-module-scss-module__YNpIRW__emblaNext embla__button embla__button--next ProductCarouselDesktop-module-scss-module__PbTJVG__nextButton'])[1]"));
//         int count=0;
//         while(count<11){
//              List<WebElement> allProductsElemet = driver.findElements(By.xpath("//div[@id='v-sensor-component-6-1-module-productCarousel-Homepage-lowest-prices-carousel']//div[@class='ProductDetailsSection-module-scss-module__Y6u1Qq__wrapper ProductDetailsSection-module-scss-module__Y6u1Qq__isPboxRedesignEnabled']//h2"));
//              for(WebElement ele : allProductsElemet) {
//                 // JavascriptExecutor js = (JavascriptExecutor) driver;
//                 js.executeScript("arguments[0].scrollIntoView(true);", ele);
//                 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//                 wait.until(ExpectedConditions.visibilityOf(ele));
//                 String text = ele.getText();
//                 System.out.println(text);

                
//         }
       
//         count++;
//         nextButtonElement.click();
//         }
//         driver.quit();




//     }
// }




public class Noon_Page_Automation {

    public static void main(String[] args) throws Exception {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.noon.com/uae-en/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // ✅ Step 1: Scroll until banner link appears
        WebElement lowestBannerLink = null;

        for (int i = 0; i < 12; i++) {
            try {
                lowestBannerLink = driver.findElement(By.cssSelector(
                        "a[href*='lowest-prices']"
                ));
                break;
            } catch (Exception e) {
                js.executeScript("window.scrollBy(0,900)");
                Thread.sleep(800);
            }
        }

        if (lowestBannerLink == null) {
            throw new RuntimeException("❌ LOWEST PRICES banner link not found on homepage");
        }

        // ✅ Step 2: Go upward to carousel wrapper
        WebElement carouselSection = lowestBannerLink.findElement(By.xpath(
                "./ancestor::div[contains(@class,'ModuleHeader')]//following-sibling::section | " +
                "./ancestor::div[contains(@class,'ModuleHeader')]//parent::div//following-sibling::section"
        ));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", carouselSection);
        Thread.sleep(1000);

        // ✅ Step 3: Locate NEXT button inside this carousel
        WebElement nextBtn = carouselSection.findElement(By.cssSelector(
                "button[class*='embla__button--next'], button[class*='nextButton']"
        ));

        // ✅ Step 4: Product title locator inside this carousel
        By productTitle = By.cssSelector(
                "h2[data-qa='product-box-name']"
        );

        Set<String> products = new HashSet<>();
        int stableCounter = 0;

        while (true) {

            List<WebElement> visibleProducts = carouselSection.findElements(productTitle);

            for (WebElement p : visibleProducts) {
                products.add(p.getText().trim());
            }

            try {
                wait.until(ExpectedConditions.elementToBeClickable(nextBtn));
                nextBtn.click();
                Thread.sleep(900);
            } catch (Exception e) {
                break;
            }

            int currentSize = products.size();
            Thread.sleep(700);

            if (products.size() == currentSize) {
                stableCounter++;
                if (stableCounter >= 3) {
                    break;
                }
            } else {
                stableCounter = 0;
            }
        }

        // ✅ Final Output
        System.out.println("\n=========== LOWEST PRICES – CAROUSEL DATA ===========");
        products.forEach(System.out::println);
        System.out.println("\n✅ Total unique products captured = " + products.size());
        System.out.println("====================================================");

        Thread.sleep(3000);
        driver.quit();
    }
}
