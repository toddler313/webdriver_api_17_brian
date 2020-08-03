package sele_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driver.SeleniumWebDriver;

public class Topic_09_Alert {
	SeleniumWebDriver swd;
	WebDriverWait expWait;
	Alert alert;

	@Test
	public void tc01_alert_basic_Auth() {
		swd.getDriver().get("http://the-internet.herokuap.com/basic_auth");
		String baseLink = swd.getDriver().findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		swd.getDriver().get(convertToAuthenticatedURL(baseLink, "admin", "admin"));
		Assert.assertTrue(swd.getDriver().findElement(By.xpath("Congratulation message is displayed!")).isDisplayed());

	}

	public void tc02_alert_confirm_prompt() {
		alert = swd.getDriver().switchTo().alert();
		alert.accept();
		alert.dismiss();
		alert.sendKeys("Message to send");
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		swd = new SeleniumWebDriver(new ChromeDriver());
		expWait = new WebDriverWait(swd.getDriver(), 15);
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

	public String convertToAuthenticatedURL(String url, String username, String password) {
		String[] temp = url.split("//");
		url = temp[0] + "//" + username + ":" + password + "@" + temp[1];
		return url;
	}

}
