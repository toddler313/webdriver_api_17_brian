package sele_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class topic_01_environment {
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.demoguru99.com/");
	}
	
	@Test
	public void tc_01() {
		driver.findElement(By.xpath("//header[@id='header']//a[@class='skip-link skip-account']")).click();
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
