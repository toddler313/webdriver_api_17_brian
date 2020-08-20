package sele_api;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driver.SeleniumWebDriver;
import support.GenerateData;

public class Topic_10_User_Interaction_part2 {
	SeleniumWebDriver swd;
	WebDriverWait expWait;
	Actions act;
	Alert alert;
	JavascriptExecutor jsex;
	String rootFolder = System.getProperty("user.dir");
	String javascriptPath = rootFolder + "\\dragAndDropHTML5\\drag_drop_helper.js";
	String jQueryPath = rootFolder + "\\dragAndDropHTML5\\jQuery_load_helper.js";
	
	public void tc01_mouse_right_clicks() {
		swd.getDriver().get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

		act.moveToElement(swd.getDriver().findElement(By.cssSelector("span.btn-neutral"))).contextClick().perform();

		// Verify all elements with default class value
		List<WebElement> liList = swd.getDriver()
				.findElements(By.cssSelector(".context-menu-item.context-menu-icon.context-menu-icon"));
		// Verify a specific item with default class value
		WebElement li = liList.get(liList.size() - 1);
		Assert.assertEquals(li.getAttribute("class"), "context-menu-item context-menu-icon context-menu-icon-quit");

		act.moveToElement(li).perform();
		Assert.assertFalse(
				li.getAttribute("class").equals("context-menu-item context-menu-icon context-menu-icon-quit"));
		Assert.assertTrue(li.getAttribute("class").contains("context-menu-hover context-menu-visible"));
		sleepInSeconds(3);
	}
	

	public void tc02_basic_angular_drag_drop() {
		swd.getDriver().get("https://demos.telerik.com/kendo-ui/dragdrop/angular");
		WebElement draggable = swd.getDriver().findElement(By.id("draggable"));
		WebElement droptarget = swd.getDriver().findElement(By.id("droptarget"));
		
		Assert.assertEquals(droptarget.getText(), "Drag the small circle here.");
		act.clickAndHold(draggable).moveToElement(droptarget).moveToElement(draggable).release().perform();
		Assert.assertEquals(droptarget.getText(), "Drop here.");
		act.dragAndDrop(draggable, droptarget).perform();
		Assert.assertEquals(droptarget.getText(), "You did great!");
	}
	
	
	public void tc03_html5_drag_drop() throws IOException {
//		The common dragAndDrop ability from Actions do not support the work in HTML5 and above.
//		Therefore, an external helper (jQuery) is used to accomplished the task.
//		support > dragAndDrop > drag_drop_helper.js
//		support > dragAndDrop > jQuery_load_helper.js
		
		swd.getDriver().get("https://automationfc.github.io/drag-drop-html5/");
		String sourceCss = "#column-a";
		String targetCss = "#column-b";
		
//		In case the site does not support jQuery
//		String jQueryScript = readFile(jQueryPath);
//		jsex.executeScript(jQueryScript);
		
//		Otherwise, use Javascript
		String script = readFile(javascriptPath);
		//move block A to B
		script += "$(\"" + sourceCss + "\").simulateDragDrop({ dropTarget: \"" + targetCss + "\"});";
		sleepInSeconds(2);
		jsex.executeScript(script);
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector(sourceCss + " header")).getText(), "B");
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector(targetCss + " header")).getText(), "A");
		
		//move B back again
		sleepInSeconds(2);
		jsex.executeScript(script);
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector(sourceCss + " header")).getText(), "A");
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector(targetCss + " header")).getText(), "B");
	}

	@Test
	public void TC_04_DragDrop_HTML5_Offset() throws InterruptedException, IOException, AWTException {
		swd.getDriver().get("http://automationfc.github.io/drag-drop-html5");
	
		String sourceXpath = "//div[@id='column-a']";
		String targetXpath = "//div[@id='column-b']";
		
		drag_the_and_drop_html5_by_xpath(sourceXpath, targetXpath);
		sleepInSeconds(1);
		
		drag_the_and_drop_html5_by_xpath(sourceXpath, targetXpath);
		sleepInSeconds(1);
		
		drag_the_and_drop_html5_by_xpath(sourceXpath, targetXpath);
		sleepInSeconds(1);
	}
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", ".\\drivers\\geckodriver.exe");
		swd = new SeleniumWebDriver(new FirefoxDriver());
		expWait = new WebDriverWait(swd.getDriver(), 15);
		act = new Actions(swd.getDriver());
		jsex = (JavascriptExecutor) swd.getDriver();
		swd.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@AfterClass
	public void afterClass() {
//		swd.getDriver().quit();
	}

	public void sleepInSeconds(double n) {
		try {
			Thread.sleep((long) (n * 1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String readFile(String file) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(file);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}

	public void drag_the_and_drop_html5_by_xpath(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = swd.getDriver().findElement(By.xpath(sourceLocator));
		WebElement target = swd.getDriver().findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
}
