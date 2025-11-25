package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class populationCount_Page_Automation {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.worldometers.info/");
    }
}
