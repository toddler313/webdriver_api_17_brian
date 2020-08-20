package sele_api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driver.SeleniumWebDriver;
import support.GenerateData;

public class Topic_10_User_Interaction_part1 {
	SeleniumWebDriver swd;
	WebDriverWait expWait;
	Actions act;

	@Test
	public void tc01_Mouse_Hover() {
		swd.getDriver().get("https://myntra.com");
		act.moveToElement(
				swd.getDriver().findElement(By.xpath("//div[@class='desktop-navLink']//a[text()='Home & Living']")))
				.perform();

//		2 ways to perform a click-on-element in the hover dialog
//		swd.getDriver().findElement(By.xpath("//ul[@class='desktop-navBlock']//a[text()='Clocks']")).click();
		act.moveToElement(swd.getDriver().findElement(By.xpath("//ul[@class='desktop-navBlock']//a[text()='Clocks']")))
				.click().perform();

		expWait.until(ExpectedConditions.visibilityOf(swd.getDriver().findElement(By.cssSelector("h1.title-title"))));
//		Verify breadcrumbs
		List<WebElement> breadcrumbs = swd.getDriver().findElements(By.cssSelector("div.breadcrumbs-base span"));
		Assert.assertEquals(breadcrumbs.get(0).getText(), "Home");
		Assert.assertEquals(breadcrumbs.get(1).getText(), "Home Furnishing");
		Assert.assertEquals(breadcrumbs.get(2).getText(), "Clocks");

//		Rarely use do to varied screen resolution on devices
//		act.moveByOffset(1920, 1080);
	}

	@Test
	public void tc02_Mouse_Click_Hold_Drag_Verify() {
		int n = GenerateData.generateRandomIntegerWithinRange(0, 11);
		swd.getDriver().get("https://jqueryui.com/resources/demos/selectable/display-grid.html");
		List<WebElement> es = swd.getDriver().findElements(By.cssSelector("ol#selectable li"));
		act.clickAndHold(es.get(0)).moveToElement(es.get(n)).release().perform();
		List<WebElement> selections = swd.getDriver()
				.findElements(By.cssSelector("ol#selectable li[class*='ui-selected']"));
		System.out.println("Number of highlighting box: " + selections.size());

		for (int i = 0; i < selections.size(); i++) {
			Assert.assertEquals(selections.get(i).getText(), i + 1 + "");
			System.out.println("Actual: " + selections.get(i).getText() + " - Expected: " + (i + 1));
		}
	}
	
	@Test
	public void tc03_Keyboard_Mouse_Random_Selections() {
		swd.getDriver().get("https://jqueryui.com/resources/demos/selectable/display-grid.html");
		List<WebElement> es = swd.getDriver().findElements(By.cssSelector("ol#selectable li"));
		int n = GenerateData.generateRandomIntegerWithinRange(3, 7);
		System.out.print("Numbers will be clicked: ");
		List<WebElement> beforeSelections = new ArrayList<WebElement>();
		
		Collections.shuffle(es);
		for (int i = 0; i < n; i++) {
			beforeSelections.add(es.get(i));
			Assert.assertTrue(beforeSelections.get(i).getAttribute("class").equals("ui-state-default ui-selectee"));
			System.out.print(beforeSelections.get(i).getText() + "  ");
		}

		act.keyDown(Keys.CONTROL).perform();
		for (int j = 0; j < beforeSelections.size(); j++) {
			act.moveToElement(beforeSelections.get(j)).click().perform();
		}
		act.keyUp(Keys.CONTROL).perform();
		
		for (int k = 0; k < beforeSelections.size(); k++) {
			Assert.assertTrue(beforeSelections.get(k).getAttribute("class").contains("ui-selected"));
		}
		Assert.assertEquals(beforeSelections.size(), n);
		System.out.println("\n");
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		swd = new SeleniumWebDriver(new ChromeDriver());
		expWait = new WebDriverWait(swd.getDriver(), 15);
		act = new Actions(swd.getDriver());
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

}
