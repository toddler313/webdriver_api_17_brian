package sele_api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driver.SeleniumWebDriver;
import support.GenerateData;

public class Topic_07_08_dropdown {
	SeleniumWebDriver swd;
	WebDriverWait wait;
	Select select;
	JavascriptExecutor jsex;

	public void tc01_standard_single_select() {
		swd.getDriver().get("https://www.facebook.com");
		select = new Select(swd.getDriver().findElement(By.id("month")));
		List<WebElement> monthList = select.getOptions();

		select.isMultiple(); // check if multi-select list
		select.selectByIndex(3); // select item by its index
		select.selectByValue("3"); // select item by its value
		select.selectByVisibleText("item text()"); // more common use

		Assert.assertEquals(select.getFirstSelectedOption().getText(), "item text() here"); // compare current
																							// selected item
//		for (int i=0; i<monthList.size(); i++) {
//			System.out.println(monthList.get(i).getText());
//		}

		// foreach: always browse the whole list, no stop
		for (WebElement month : monthList) {
			if (month.getText().equals("Tháng 3")) {
				System.out.println(month.getText());
				break;
			}
		}
	}
	
	@Test
	public void tc02_standard_multi_select() {
		swd.getDriver().get("https://automationfc.github.io/basic-form/index.html");
		select = new Select(swd.getDriver().findElement(By.id("job2")));
		Assert.assertTrue(select.isMultiple());
		List<WebElement> list = new ArrayList<WebElement>();
		list = select.getOptions();
		System.out.println("Total List Items: " + list.size());
		select.selectByVisibleText("Adhoc");
		select.selectByVisibleText("Mobile");
		select.selectByVisibleText("Desktop");

		List<WebElement> selections = new ArrayList<WebElement>();
		selections = select.getAllSelectedOptions();
		for (WebElement e : selections) {
			System.out.println(e.getText());
		}
	}

	@Test
	public void tc03_jquery_custom_dropdown() {
		swd.getDriver().get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		selectSingleItemFromCustomDropdown("//span[@id='number-button']",
				"//ul[@id='number-menu']/li[@class='ui-menu-item']/div[starts-with(@id,'ui-id')]", "14");
		Assert.assertEquals(swd.getDriver()
				.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")).getText(),
				"14");
		selectSingleItemFromCustomDropdown("//span[@id='number-button']",
				"//ul[@id='number-menu']/li[@class='ui-menu-item']/div[starts-with(@id,'ui-id')]", "3");
		Assert.assertEquals(swd.getDriver()
				.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")).getText(),
				"3");
		selectSingleItemFromCustomDropdown("//span[@id='number-button']",
				"//ul[@id='number-menu']/li[@class='ui-menu-item']/div[starts-with(@id,'ui-id')]", "7");
		Assert.assertEquals(swd.getDriver()
				.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")).getText(),
				"7");
		selectSingleItemFromCustomDropdown("//span[@id='number-button']",
				"//ul[@id='number-menu']/li[@class='ui-menu-item']/div[starts-with(@id,'ui-id')]", "11");
		Assert.assertEquals(swd.getDriver()
				.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")).getText(),
				"11");
	}

	@Test
	public void tc04_angola_custom_dropdown() {
		// video topic 15
		// This type of dropdown only works with old FF version 47.x

		swd.getDriver().get("https://bit.ly/2UV2vYi");

		// Use JavascriptExecutor to select item in dropdown
		selectSingleItemFromCustomDropdown(
				"//ejs-dropdownlist[@id='games']//span[contains(@class,'e-control-wrapper')]",
				"//ul[@id='games_options']/li", "Football");

		// Use a separate func() to verify retrieval data equals with current selected
		Assert.assertEquals(getHiddenTextFromDropdown("ejs-dropdownlist[id=games] option"), "Football");

		selectSingleItemFromCustomDropdown(
				"//ejs-dropdownlist[@id='games']//span[contains(@class,'e-control-wrapper')]",
				"//ul[@id='games_options']/li", "Golf");
		Assert.assertEquals(getHiddenTextFromDropdown("ejs-dropdownlist[id=games] option"), "Golf");
		selectSingleItemFromCustomDropdown(
				"//ejs-dropdownlist[@id='games']//span[contains(@class,'e-control-wrapper')]",
				"//ul[@id='games_options']/li", "Tennis");
		Assert.assertEquals(getHiddenTextFromDropdown("ejs-dropdownlist[id=games] option"), "Tennis");
		selectSingleItemFromCustomDropdown(
				"//ejs-dropdownlist[@id='games']//span[contains(@class,'e-control-wrapper')]",
				"//ul[@id='games_options']/li", "Football");
		Assert.assertEquals(getHiddenTextFromDropdown("ejs-dropdownlist[id=games] option"), "Football");
	}

	@Test
	public void tc05_react_custom_dropdown() {
		swd.getDriver().get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
//		selectSingleItemFromCustomDropdown("//div[@id='root']",
//				"//div[@id='root']//div[contains(@class,'item')]", "Christian");
//		Assert.assertEquals(swd.getDriver().findElement(By.xpath("//div[@id='root']//div[@class='text']")).getText(), "Christian");
//		selectSingleItemFromCustomDropdown("//div[@id='root']",
//				"//div[@id='root']//div[contains(@class,'item')]", "Elliot Fu");
//		Assert.assertEquals(swd.getDriver().findElement(By.xpath("//div[@id='root']//div[@class='text']")).getText(), "Elliot Fu");
//		selectSingleItemFromCustomDropdown("//div[@id='root']",
//				"//div[@id='root']//div[contains(@class,'item')]", "Justen Kitsune");
//		Assert.assertEquals(swd.getDriver().findElement(By.xpath("//div[@id='root']//div[@class='text']")).getText(), "Justen Kitsune");

		selectSingleItemFromCustomDropdown("//div[@role='listbox']", "//span[@class='text']", "Christian");
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("div[role='alert']")).getText(), "Christian");
		selectSingleItemFromCustomDropdown("//div[@role='listbox']", "//span[@class='text']", "Elliot Fu");
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("div[role='alert']")).getText(), "Elliot Fu");
		selectSingleItemFromCustomDropdown("//div[@role='listbox']", "//span[@class='text']", "Justen Kitsune");
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("div[role='alert']")).getText(),
				"Justen Kitsune");
	}

	@Test
	public void tc06_custom_searchable_dropdown() {
		swd.getDriver().get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");

		selectSingleItemFromCustomDropdown("//input[@class='search']", "//span[@class='text']", "Barbados");
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("input.search + div.text")).getText(),
				"Barbados");
		selectSingleItemFromCustomDropdown("//input[@class='search']", "//span[@class='text']", "Australia");
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("input.search + div.text")).getText(),
				"Australia");
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		swd = new SeleniumWebDriver(new ChromeDriver());
		wait = new WebDriverWait(swd.getDriver(), 15);
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

	public void selectSingleItemFromCustomDropdown(String parentXpLocator, String childrenXpLocator,
			String expectedItem) {
		WebElement dropdown = swd.getDriver().findElement(By.xpath(parentXpLocator));
		dropdown.click();

		// check if it is a searchable dropdown
		if (dropdown.getTagName().equals("input")) {
			sleepInSeconds(0.5);
			dropdown.clear();
			dropdown.sendKeys(expectedItem);
		}

		sleepInSeconds(1);

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childrenXpLocator)));
		List<WebElement> elements = swd.getDriver().findElements(By.xpath(childrenXpLocator));
		for (WebElement e : elements) {
			if (e.getText().equals(expectedItem)) {
				// Scroll to the item position prior clicking on it
				jsex.executeScript("arguments[0].scrollIntoView(true);", e);
				sleepInSeconds(1);
				wait.until(ExpectedConditions.elementToBeClickable(e));
				e.click();
				sleepInSeconds(1);
				break;
			}
		}
	}

	public String getHiddenTextFromDropdown(String cssLocator) {
		// copy this js document.querySelector("ejs-dropdownlist[id=games] option").text
		// right from the browser console
		// .. to have the right format \"css_locator\"
		return (String) jsex.executeScript("return document.querySelector(\"" + cssLocator + "\").text");
	}

}
