package sele_api;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driver.SeleniumWebDriver;

public class Topic_11_Popup_iFrame_Windows_Tabs {
	SeleniumWebDriver swd;
	WebDriverWait expWait;
	Actions act;
	Alert alert;
	JavascriptExecutor jsex;

	@Test
	public void TC_02_random_popup() {
		swd.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
		swd.getDriver().get("https://blog.testproject.io/");
		String keyword = "Selenium";
		sleepInSeconds(10);
		WebElement adPopup = swd.getDriver().findElement(By.id("mailch-bg"));
		if (adPopup.isDisplayed()) {
			swd.getDriver().findElement(By.cssSelector("#close-mailch svg")).click();
			sleepInSeconds(2);
			search_for_selenium_keyword(keyword);
		} else {
			search_for_selenium_keyword(keyword);
		}
	}

//	@Test
	public void TC_03_iFrame() {
//		swd.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
		swd.getDriver().get("https://kyna.vn");
		String keyword = "Selenium Web Driver";

		List<WebElement> iframes = swd.getDriver().findElements(By.tagName("iframe"));
		int noOfiFrame = iframes.size();

		expWait.until(ExpectedConditions.elementToBeClickable(By.id("cs-live-chat"))).click();
		swd.getDriver().switchTo().frame("cs_chat_iframe");

		By textarea = By.tagName("textarea");
		expWait.until(ExpectedConditions.visibilityOf(swd.getDriver().findElement(textarea)));
		swd.getDriver().findElement(textarea).sendKeys(keyword + Keys.ENTER);
//		swd.getDriver().findElement(By.cssSelector(".cancel")).click();
//		swd.getDriver().findElement(By.cssSelector(".overlay")).click();

		swd.getDriver().switchTo().defaultContent();
		List<WebElement> adPopup = swd.getDriver().findElements(By.className("fancybox-inner"));
		if (!adPopup.isEmpty()) {
			swd.getDriver().findElement(By.cssSelector("a[title=Close]")).click();
			System.out.println("An Advertise Popup has been closed!");
		}

		By mainSearchBar = By.id("live-search-bar");
		expWait.until(ExpectedConditions.visibilityOf(swd.getDriver().findElement(mainSearchBar)))
				.sendKeys(keyword + Keys.ENTER);
	}

//	@Test
	public void TC_04_fixed_popup() {
//		swd.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
		swd.getDriver().get("https://zingpoll.com");
		expWait.until(ExpectedConditions.visibilityOf(swd.getDriver().findElement(By.id("Loginform"))));
		sleepInSeconds(3);
		swd.getDriver().findElement(By.xpath("//a[@id='Loginform']")).click();
		expWait.until(ExpectedConditions.visibilityOf(swd.getDriver().findElement(By.id("Login"))));
		if (!swd.getDriver().findElement(By.id("existUser")).isSelected()) {
			swd.getDriver().findElement(By.id("existUser")).click();
		}
		swd.getDriver().findElement(By.id("loginEmail")).clear();
		swd.getDriver().findElement(By.id("loginEmail")).sendKeys("toddler313@gmail.com");
		swd.getDriver().findElement(By.id("loginPassword")).clear();
		swd.getDriver().findElement(By.id("loginPassword")).sendKeys("Test@313" + Keys.ENTER);

		expWait.until(ExpectedConditions.visibilityOf(swd.getDriver().findElement(By.id("wide"))));
		Assert.assertTrue(swd.getDriver().findElement(By.xpath("//div[@id='wide']//div[@class='username']")).getText()
				.contains("BRIAN NGUYEN"));
	}

//	@Test
	public void TC_05_windows_tab_handling() {
//		swd.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
		swd.getDriver().get("https://kyna.vn");
		String parentWindowHandle = swd.getDriver().getWindowHandle();
		expWait.until(ExpectedConditions.visibilityOf(swd.getDriver().findElement(By.cssSelector("a[title=Close]"))));
		List<WebElement> adPopup = swd.getDriver().findElements(By.className("fancybox-inner"));
		if (!adPopup.isEmpty()) {
			swd.getDriver().findElement(By.cssSelector("a[title=Close]")).click();
			System.out.println("An Advertise Popup has been closed!");
		}

//		Click on a logo at the bottom of the page to open new tab. There are 2 ways
//		1. Move to the element before clicking on it.
//		act.moveToElement(swd.getDriver().findElement(By.xpath("//img[@alt='VietnamWorks']"))).click().perform();
//		2. Using Java Script Executor (No need to move to the element)
		clickElementUsingJS("img[alt='VietnamWorks']");

//		parameter: a function that return parentWindowID
		switchToWindowByID(parentWindowHandle);
//		expWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-bar-input")))
//				.sendKeys("Selenium WebDriver");
		// Or using JS to send keys
		expWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-bar-input")));
		sendKeysUsingJSByXPath("search-bar-input", "Send Keys Using Javascript");
		sleepInSeconds(2);
		Assert.assertEquals(swd.getDriver().getTitle(),
				"Tuyển dụng, việc làm, tìm việc làm nhanh mới nhất | VietnamWorks");

//		Switch back to parent window then open another window and do actions there.
		swd.getDriver().switchTo().window(parentWindowHandle);
		expWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='VietnamWorks Learning']")))
				.click();

		switchToWindowsByTitle("Trang chủ | Vietnamworks Learning for Enterprise");
		expWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='logo']")));
		Assert.assertEquals(swd.getDriver().getCurrentUrl(),
				"https://enterprise.vietnamworkslearning.com/?utm_source=from_kyna");
		act.moveToElement(swd.getDriver().findElement(By.id("fullName")));
		swd.getDriver().findElement(By.id("fullName")).sendKeys("Selenium Tester");
		swd.getDriver().findElement(By.id("btn-contact")).click();
		expWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email']/following-sibling::div")));
		Assert.assertTrue(swd.getDriver().findElement(By.xpath("//input[@id='email']/following-sibling::div")).getText()
				.contains("Xin vui lòng nhập email công ty."));

//		Switch back to parent window again then switch to an iFrame and do actions there.
		swd.getDriver().switchTo().window(parentWindowHandle);
		expWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cs-live-chat"))).click();
		swd.getDriver().switchTo().frame("cs_chat_iframe");
		expWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[ng-model='login.username']")))
				.sendKeys("Brian Nguyen");

		swd.getDriver().switchTo().window(parentWindowHandle);
		swd.getDriver().findElement(By.cssSelector("img[alt='youtube']")).click();
		switchToWindowsByTitle("Kyna.vn - YouTube");
		expWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo-icon-container")));
		Assert.assertEquals(swd.getDriver().getCurrentUrl(), "https://www.youtube.com/user/kynavn");
		Assert.assertEquals(swd.getDriver()
				.findElement(By.xpath("//app-header[@id='header']//div[@id='text-container']//yt-formatted-string"))
				.getText(), "Kyna.vn");

		// Close all tabs then back to parent
		closeAllChildrenWindows(parentWindowHandle);
	}

	@BeforeClass
	public void beforeClass() {
//		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
//		swd = new SeleniumWebDriver(new ChromeDriver());
		System.setProperty("webdriver.gecko.driver", ".\\drivers\\geckodriver.exe");
		swd = new SeleniumWebDriver(new FirefoxDriver());
		expWait = new WebDriverWait(swd.getDriver(), 15);
		swd.getDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
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

	public void clickElementUsingJS(String locator) {
		jsex.executeScript("arguments[0].click();", swd.getDriver().findElement(By.cssSelector(locator)));
	}

	public void sendKeysUsingJSByXPath(String elementId, String keys) {
		jsex.executeScript("document.getElementById('" + elementId + "').value='" + keys + "';");
	}

	private void search_for_selenium_keyword(String keyword) {
		expWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-2")));
		swd.getDriver().findElement(By.cssSelector("section[id='search-2'] input[type='search']")).clear();
		swd.getDriver().findElement(By.cssSelector("section[id='search-2'] input[type='search']"))
				.sendKeys(keyword + Keys.ENTER);

		expWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main")));
		List<WebElement> tag_a_list = swd.getDriver().findElements(By.cssSelector("main h3 a"));
		for (WebElement a : tag_a_list) {
			sleepInSeconds(2);
			Assert.assertTrue(a.getText().contains(keyword));
			sleepInSeconds(2);
			System.out.println(a.getText());
		}
	}

//	if there are only 2 windows
	public void switchToWindowByID(String parentID) {
		Set<String> allWindows = swd.getDriver().getWindowHandles();
		for (String targetWindow : allWindows) {
			if (!targetWindow.equals(parentID)) {
				swd.getDriver().switchTo().window(targetWindow);
				break;
			}
		}
	}

	public void switchToWindowsByTitle(String title) {
		Set<String> allWindows = swd.getDriver().getWindowHandles();
		for (String currentWindow : allWindows) {
			swd.getDriver().switchTo().window(currentWindow);
			if (swd.getDriver().getTitle().equals(title)) {
				break;
			}
		}
	}

	public void closeAllChildrenWindows(String parentWindowHandle) {
		Set<String> allWindows = swd.getDriver().getWindowHandles();
		for (String current : allWindows) {
			if (!current.equals(parentWindowHandle)) {
				swd.getDriver().switchTo().window(current);
				swd.getDriver().close();
			}
		}
		swd.getDriver().switchTo().window(parentWindowHandle);
	}
}
