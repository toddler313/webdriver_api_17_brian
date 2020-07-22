package coding_challenge;

import org.testng.annotations.Test;

import support.httpResponseCode;

import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class assignment03 {
	WebDriver driver;
	private static String reponseStatus;
	
	@Test
	public void f() throws MalformedURLException, IOException {
		List<WebElement> linkList = driver.findElements(By.tagName("a"));
		System.out.println("TOTAL LINKS: " + linkList.size());
		
		for (int i=0; i<linkList.size(); i++) {
			if (!linkList.get(i).getAttribute("href").equals("") && 
					!(linkList.get(i).getAttribute("href") == null)) {
				if (linkList.get(i).getAttribute("href").contains("http")) {
					reponseStatus = httpResponseCode.getResponseCode(linkList.get(i).getAttribute("href").trim());
					System.out.println("URL-" + (i+1) + " " + linkList.get(i).getAttribute("href")); 
					System.out.println("Status: " + reponseStatus);
					System.out.println();
				}
			}
		}
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://automationfc.com/");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
