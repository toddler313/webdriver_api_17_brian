package sele_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driver.SeleniumWebDriver;

public class Topic_08_Button_Radio_Checkbox {
	SeleniumWebDriver swd;
	WebDriverWait expWait;
	JavascriptExecutor jsex;

	public void tc01_button_removeAttribute() {
		swd.getDriver().get("https://www.fahasa.com/customer/account/create");
		expWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#popup-login-tab_list")));
		swd.getDriver().findElement(By.cssSelector("#popup-login-tab_list .popup-login-tab-login a")).click();
		WebElement loginBtn = swd.getDriver().findElement(By.cssSelector(".fhs-btn-login"));
		Assert.assertFalse(loginBtn.isEnabled());
		
//		test removing attribute "disabled" of a button at the beginning to trigger hidden validations
		removeElementAttributeByJS(loginBtn, "disabled");
		Assert.assertTrue(loginBtn.isEnabled());
		loginBtn.click();
		Assert.assertEquals(swd.getDriver()
				.findElement(By.xpath(
						"//input[@id='login_username']/parent::div//following-sibling::div[@class='fhs-input-alert']"))
				.getText(), "Thông tin này không thể để trống");
		Assert.assertEquals(swd.getDriver()
				.findElement(By.xpath(
						"//input[@id='login_password']/parent::div//following-sibling::div[@class='fhs-input-alert']"))
				.getText(), "Thông tin này không thể để trống");

		swd.getDriver().findElement(By.id("login_username")).sendKeys("automation@test.org" + Keys.TAB);
		sleepInSeconds(1);
		Assert.assertFalse(loginBtn.isEnabled());
		swd.getDriver().findElement(By.id("login_password")).sendKeys("123123");
		sleepInSeconds(1);
		Assert.assertTrue(loginBtn.isEnabled());
		loginBtn.click();
		sleepInSeconds(1);
		Assert.assertTrue(swd.getDriver().findElement(By.cssSelector(".fhs-popup-msg.fhs-login-msg")).isDisplayed());
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector(".fhs-popup-msg.fhs-login-msg")).getText(),
				"Số điện thoại/Email hoặc Mật khẩu sai!");
	}

	@Test
	public void tc02_JS_Click_Checkbox_Radio() {
		swd.getDriver().get("https://material.angular.io/components/checkbox/examples");
		
		//Note: The input tag is not visible in the WebUI => could not be clicked using sele click();
		//The div covering it, however, is clickable but again will fail the sele isSelected();
		// => using JS click
		
		WebElement cb1 = swd.getDriver().findElement(By.id("mat-checkbox-1-input"));
		clickElementByJS(cb1);
		sleepInSeconds(0.5);
		Assert.assertTrue(cb1.isSelected());
		
		WebElement radBefore = swd.getDriver().findElement(By.id("mat-radio-3-input"));
		clickElementByJS(radBefore);
		sleepInSeconds(0.5);
		Assert.assertTrue(cb1.isSelected());
		Assert.assertFalse(swd.getDriver().findElement(By.id("mat-radio-2-input")).isSelected());
	}
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		swd = new SeleniumWebDriver(new ChromeDriver());
		expWait = new WebDriverWait(swd.getDriver(), 15);
		jsex = (JavascriptExecutor) swd.getDriver();
		swd.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@AfterClass
	public void afterClass() {
		swd.getDriver().quit();
	}

	public void sleepInSeconds(double n) {
		try {
			Thread.sleep((long) (n * 1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeElementAttributeByJS(WebElement e, String attribute) {
		jsex.executeScript("arguments[0].removeAttribute('" + attribute + "')", e);
	}
	
	public void clickElementByJS(WebElement e) {
		jsex.executeScript("arguments[0].click()", e);
	}
	
}
