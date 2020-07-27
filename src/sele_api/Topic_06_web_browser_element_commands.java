package sele_api;

import java.util.List;
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

import driver.SeleniumWebDriver;
import support.GenerateData;

public class Topic_06_web_browser_element_commands {
	SeleniumWebDriver swd;
	WebDriverWait wait;
	GenerateData genData;
	
	//Declaration of "By" => No need to wait for "driver" initiation
//	By emailTextbox = By.id("mail");
//	By passwordTextbox = By.id("password");
//	By ageUnder18Radio = By.id("under_18");
//	By firstImage = By.xpath("//h5[text()='Name: User 1']");
	
	@Test
	public void tc01_Mailchimp_Account_Registration() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
		swd.getDriver().findElement(By.id("email")).clear();
		swd.getDriver().findElement(By.id("email")).sendKeys(genData.generateEmail(15));
		swd.getDriver().findElement(By.id("new_username")).clear();
		swd.getDriver().findElement(By.id("new_username")).sendKeys(genData.generateRandomAlphaNumeric(12));
		
		WebElement password = swd.getDriver().findElement(By.id("new_password"));
		WebElement submitBtn = swd.getDriver().findElement(By.id("create-account"));
		
		password.clear();
		password.sendKeys("a");
		Assert.assertTrue(swd.getDriver().findElement(By.className("lowercase-char")).getAttribute("class").contains("completed"));
		Assert.assertFalse(submitBtn.isEnabled());
		
		password.sendKeys("A");
		Assert.assertTrue(swd.getDriver().findElement(By.className("uppercase-char")).getAttribute("class").contains("completed"));
		Assert.assertFalse(submitBtn.isEnabled());
		
		password.sendKeys("1");
		Assert.assertTrue(swd.getDriver().findElement(By.className("number-char")).getAttribute("class").contains("completed"));
		Assert.assertFalse(submitBtn.isEnabled());
		
		password.sendKeys("!");
		Assert.assertTrue(swd.getDriver().findElement(By.className("special-char")).getAttribute("class").contains("completed"));
		Assert.assertFalse(submitBtn.isEnabled());
		
		password.sendKeys("8888");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='password-ok']")));
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("div[class*='password-ok'] h4")).getText(),
				"Your password is secure and you're all set!");
		
		Assert.assertFalse(swd.getDriver().findElement(By.className("lowercase-char")).isDisplayed());
		Assert.assertFalse(swd.getDriver().findElement(By.className("uppercase-char")).isDisplayed());
		Assert.assertFalse(swd.getDriver().findElement(By.className("special-char")).isDisplayed());
		Assert.assertFalse(swd.getDriver().findElement(By.className("number-char")).isDisplayed());
		Assert.assertFalse(swd.getDriver().findElement(By.className("8-char")).isDisplayed());
		
		Assert.assertTrue(submitBtn.isEnabled());
		WebElement newsletterCheckbox = swd.getDriver().findElement(By.id("marketing_newsletter"));
		if (!newsletterCheckbox.isSelected()) {
			newsletterCheckbox.click();
			Assert.assertTrue(newsletterCheckbox.isSelected());
		}
	}
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		swd = new SeleniumWebDriver(new ChromeDriver());
//		System.setProperty("webdriver.gecko.driver", ".\\drivers\\geckodriver.exe");
//		driver = new FirefoxDriver();
		wait = new WebDriverWait(swd.getDriver(), 15);
		genData = new GenerateData();
		swd.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
		swd.getDriver().get("https://login.mailchimp.com/signup/");
	}

	@AfterClass
	public void afterClass() {
		swd.getDriver().quit();
	}
	
//	public void case01() {
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header")));
//		swd.getDriver().findElement(By.cssSelector(".account-cart-wrapper a[href$='customer/account/']")).click();
//		List<WebElement> accountMainMenu = swd.getDriver().findElements(By.cssSelector("#header-account a"));
//		for (WebElement e : accountMainMenu) {
//			if (e.getAttribute("title").equals("My Cart")) {
//				e.click();
//				break;
//			} else {
//				System.out.println("This is: " + e.getAttribute("title") + ", Not 'My Cart'");
//			}
//		}
//	}
	
//	public void case02_check_CssValue_Color_FontSize_UIElement() {
//		//facebook login page
//		WebElement ele = swd.getDriver().findElement(By.xpath("//label[@id='loginbutton']"));
//		String bgColorValue = ele.getCssValue("background-color");
//		String fontSize = ele.getCssValue("font-size");
//		// Location X:Y of the element from within the browser
//		ele.getLocation();
//		// Actual width:height of an element
//		ele.getSize();
//		// Location X:Y of the browser bases on the screen resolution
//		swd.getDriver().manage().window().getPosition();
//		//Actual width:height of the browser
//		swd.getDriver().manage().window().getSize();
//		
//		//submit() a login form when being in a textbox == clicking on the submit button.
//		ele = swd.getDriver().findElement(By.xpath("//input[@id='email']"));
//		ele.submit();
//	}

}
