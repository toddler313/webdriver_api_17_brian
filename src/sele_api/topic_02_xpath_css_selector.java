package sele_api;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class topic_02_xpath_css_selector {
	WebDriver driver;
	WebDriverWait wait;
	
	//Exercises
	@Test
	public void tc01_Login_with_Empty_Email_andPassword() {
		driver.findElement(By.xpath("//div[@class='footer-container']//a[text()='My Account']")).click(); //xpath: footer My Account link
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='                Login or Create an Account            ']")));
		driver.findElement(By.id("send2")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advice-required-entry-email")));
		if (driver.findElement(By.id("advice-required-entry-email")).isDisplayed() &&
				driver.findElement(By.id("advice-required-entry-pass")).isDisplayed()) {
			Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
			Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
		} else {
			//Do something...
		}
	}
	
	@Test
	public void tc02_Login_with_Invalid_Email() {
		driver.get("http://live.demoguru99.com/");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header")));
		driver.findElement(By.cssSelector(".account-cart-wrapper a[href$='customer/account/']")).click();
//		List<WebElement> accountMainMenu = driver.findElements(By.cssSelector("#header-account a"));
//		for (WebElement e : accountMainMenu) {
//			if (e.getText().contentEquals("My Account")) {
//				e.click();
				//Do something else later...
//			} else {
				//Do something...
//			}
//		}
		
		driver.findElement(By.cssSelector("#header-account a[title='My Account']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("AnInvalidEmailAddress@123.321");
		driver.findElement(By.id("pass")).clear();
		driver.findElement(By.id("pass")).sendKeys("123456");
		driver.findElement(By.id("send2")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advice-validate-email-email")));
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}
	
	@Test
	public void tc03_Login_with_Password_Lower_6Chars() {
		//keep moving from tc02
		driver.findElement(By.xpath("//input[@id='email']")).clear();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("automation@gmail.com");
		driver.findElement(By.cssSelector("#pass")).clear();
		driver.findElement(By.cssSelector("#pass")).sendKeys("123");
		driver.findElement(By.id("send2")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advice-validate-password-pass")));
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
	}
	
	@Test
	public void tc04_Login_with_Incorrect_Password() {
		//keep moving from tc03
		driver.findElement(By.cssSelector("#pass")).clear();
		driver.findElement(By.cssSelector("#pass")).sendKeys("1234567");
		driver.findElement(By.id("send2")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='messages']")));
		Assert.assertEquals(driver.findElement(By.xpath("//ul[@class='messages']//li[@class='error-msg']//span")).getText(), "Invalid login or password.");
	}
	
	@Test
	public void tc05_Login_with_Valid_Email_Password_Verify_UserInfo() {
		driver.navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
		driver.findElement(By.xpath("//input[@id='email']")).clear();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("automation@gmail.com");
		driver.findElement(By.cssSelector("#pass")).clear();
		driver.findElement(By.cssSelector("#pass")).sendKeys("123123");
		driver.findElement(By.id("send2")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-left.sidebar.col-left-first")));
		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='My Dashboard']")).isDisplayed(), "My Dashboard has been displayed.");
		Assert.assertTrue(driver.findElement(By.xpath("//strong[text()='Hello, Automation Testing!']")).isDisplayed(), "Welcome paragraph has been displayed.");
		
		//Verify data...
		System.out.println(driver.findElement(By.xpath("//a[contains(@href,'edit/changepass/1/')]/parent::p")).getText());
		
		driver.findElement(By.cssSelector(".account-cart-wrapper a[href$='customer/account/']")).click();
		driver.findElement(By.xpath("//div[@id='header-account']//li[last()]")).click();
	}
	
	@Test
	public void tc06_Create_New_Account() {
		driver.findElement(By.xpath("//div[@class='footer-container']//a[text()='My Account']")).click(); //xpath: footer My Account link
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#login-form a[href*='customer/account/create']")));
		driver.findElement(By.cssSelector("#login-form a[href*='customer/account/create']")).click();
		
		//Updating...
	}
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 15);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
		driver.get("http://live.demoguru99.com/");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void case_01_findElementsByContainText_or_AttributeValue() {
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		Assert.assertEquals(driver.getTitle(), "Customer Login");
		
		driver.findElement(By.xpath("//form[@id='login-form']//span[contains(text(),'Create an Account')]")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}
	
	public void case_02_precedingSibling_parent_xpath() throws InterruptedException {
		driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//button")).click();
	}
}
