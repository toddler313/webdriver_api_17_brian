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

public class topic_01_2_login_validation {
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
//		System.setProperty("webdriver.gecko.driver", ".\\drivers\\geckodriver.exe");
//		driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
		driver.get("http://live.demoguru99.com/index.php/");
	}
	
	@Test
	public void tc_01_checkURL() {
		String loginPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginPageUrl, "http://live.demoguru99.com/index.php/");
		System.out.println(driver.findElement(By.xpath("//a[contains(@href,'term/popular/')]")).getAttribute("href"));
	}
	
//	@Test
	public void TC_02_checkPageTitle() {
		String homePageTitle = driver.getTitle();
		Assert.assertEquals(homePageTitle, "Guru99 Bank Home Page");
	}

//	@Test
	public void TC_03_verifyLoginFormIsDisplayed() {
		Assert.assertTrue(driver.findElement(By.xpath("//form[@name='frmLogin']")).isDisplayed());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
