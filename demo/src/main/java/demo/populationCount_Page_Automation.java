package demo;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class populationCount_Page_Automation {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.worldometers.info/");

        // WebElement element1 = driver.findElement(By.xpath(""));
        // WebElement element2 = driver.findElement(By.xpath(""));
        // WebElement element3 = driver.findElement(By.xpath(""));
        // WebElement element4 = driver.findElement(By.xpath(""));
        int count=0;
        while(count<11){
             List<WebElement> elements = driver.findElements(By.xpath("//div[@class='flex items-center text-lg font-semibold lg:w-[145px] lg:shrink-0 lg:justify-end lg:text-base']//span[@rel='current_population']"));
             for(WebElement ele : elements) {
             String text = ele.getText();
             Thread.sleep(1000);
             System.out.println(text);
                
        }
        count++;
        
        }
        driver.quit();




    }
}
