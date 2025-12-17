package demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class AmazonBestSeller_TitlePrice {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // ✅ Direct stable page
        driver.get("https://www.amazon.in/gp/bestsellers");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            // ✅ Wait till at least 1 product card appears
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div[data-asin]")
            ));

            List<WebElement> products = driver.findElements(By.cssSelector("div[data-asin]"));

            System.out.println("✅ TOTAL PRODUCTS FOUND = " + products.size());
            System.out.println("============================================");

            int successCount = 0;

            for (WebElement product : products) {
                try {
                    String asin = product.getAttribute("data-asin");

                    if (asin == null || asin.trim().isEmpty()) {
                        continue;
                    }

                    // ✅ TITLE
                    String title = product.findElement(
                            By.cssSelector("h2 span")
                    ).getText().trim();

                    // ✅ PRICE
                    String price;
                    try {
                        price = product.findElement(
                                By.cssSelector("span.a-price span.a-offscreen")
                        ).getText().trim();
                    } catch (Exception e) {
                        System.out.println("⚠️ Skipped broken product safely");
                        continue;
                    }

                    if (!title.isEmpty() && !price.isEmpty()) {
                        successCount++;
                        System.out.println("-----------------------------------------");
                        System.out.println("TITLE : " + title);
                        System.out.println("PRICE : " + price);
                    }

                } catch (Exception e) {
                    System.out.println("⚠️ Skipped broken product safely");
                }
            }

            System.out.println("============================================");
            System.out.println("✅ SUCCESSFULLY EXTRACTED = " + successCount);
            System.out.println("✅ BEST SELLER TITLE + PRICE EXTRACTION COMPLETED");

        } catch (Exception e) {
            System.out.println("❌ FATAL FAILURE: Best Seller page not loaded");
            e.printStackTrace();
        }

        driver.quit();
    }
}

