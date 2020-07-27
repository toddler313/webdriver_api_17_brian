package sele_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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

public class Topic_07_08_textbox_textarea_dropdown {
	SeleniumWebDriver swd;
	WebDriverWait wait;
	String registrationURL, mainURL, homepageURL;
	String signupEmail, login_user, login_password;
	String custId, custName, gender, address, city, state, pin, mobile, email, password;
	
	By inputNameTb = By.name("uid");
	By inputPasswordTb = By.name("password");
	By inputLoginBtn = By.cssSelector("input[name='btnLogin']");
	
	@Test
	public void tc01_New_Verify_CustomerInfo() {
		userRegistration();
		swd.getDriver().get(mainURL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(inputNameTb));
		swd.getDriver().findElement(inputNameTb).clear();
		swd.getDriver().findElement(inputNameTb).sendKeys(login_user);
		swd.getDriver().findElement(inputPasswordTb).clear();
		swd.getDriver().findElement(inputPasswordTb).sendKeys(login_password);
		swd.getDriver().findElement(inputLoginBtn).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("menusubnav")));
		Assert.assertTrue(swd.getDriver().findElement(By.xpath("//td[contains(text(),'Manger Id')]")).getText()
				.contains(login_user));

		swd.getDriver().findElement(By.cssSelector("a[href*='addcustomerpage']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']")));
		swd.getDriver().findElement(By.cssSelector("input[name='name']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='name']")).sendKeys(custName);
		if (gender.equals("male")) {
			swd.getDriver().findElement(By.cssSelector("input[name='rad1'][value='m']")).click();
		} else {
			swd.getDriver().findElement(By.cssSelector("input[name='rad1'][value='f']")).click();
		}
		swd.getDriver().findElement(By.id("dob")).sendKeys("03311990");
		swd.getDriver().findElement(By.cssSelector("textarea[name='addr']")).clear();
		swd.getDriver().findElement(By.cssSelector("textarea[name='addr']")).sendKeys(address);
		swd.getDriver().findElement(By.cssSelector("input[name='city']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='city']")).sendKeys(city);
		swd.getDriver().findElement(By.cssSelector("input[name='state']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='state']")).sendKeys(state);
		swd.getDriver().findElement(By.cssSelector("input[name='pinno']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='pinno']")).sendKeys(pin);
		swd.getDriver().findElement(By.cssSelector("input[name='telephoneno']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='telephoneno']")).sendKeys(mobile);
		swd.getDriver().findElement(By.cssSelector("input[name='emailid']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='emailid']")).sendKeys(email);
		swd.getDriver().findElement(By.cssSelector("input[name='password']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='password']")).sendKeys(password);
		swd.getDriver().findElement(By.cssSelector("input[name='sub']")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table[id='customer']")));
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("table[id='customer'] p")).getText(),
				"Customer Registered Successfully!!!");
		custId = swd.getDriver().findElement(By.xpath("//td[text()=\"Customer ID\"]/following-sibling::td")).getText();
		System.out.println("Customer ID: " + custId);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(),
				custName);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),
				"1990-03-31");
//		System.out.println(address);
//		System.out.println(swd.getDriver().findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText());
//		Assert.assertTrue(swd.getDriver().findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText().contains(address));
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(swd.getDriver().findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),
				pin);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
				mobile);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);

		swd.getDriver().findElement(By.xpath("//a[text()='Continue']")).click();

	}

	@Test
	public void tc02_Edit_Reverify_CustomerInfo() {
		swd.getDriver().get(homepageURL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Edit Customer')]")));
		swd.getDriver().findElement(By.xpath("//a[contains(text(),'Edit Customer')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='cusid']")));
		Assert.assertEquals(swd.getDriver().findElement(By.xpath("//p[contains(text(),'Edit Customer')]")).getText(),
				"Edit Customer Form");
		swd.getDriver().findElement(By.cssSelector("input[name='cusid']")).sendKeys(custId);
		swd.getDriver().findElement(By.cssSelector("input[name='AccSubmit']")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']")));
		Assert.assertFalse(swd.getDriver().findElement(By.cssSelector("input[name='name']")).isEnabled());
		Assert.assertFalse(swd.getDriver().findElement(By.cssSelector("input[name='gender']")).isEnabled());
		Assert.assertFalse(swd.getDriver().findElement(By.cssSelector("input[name='dob']")).isEnabled());
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("input[name='name']")).getAttribute("value"),
				custName);
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("input[name='gender']")).getAttribute("value"),
				gender);
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("input[name='dob']")).getAttribute("value"),
				"1990-03-31");
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("textarea[name='addr']")).getText(), address);
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("input[name='city']")).getAttribute("value"),
				city);
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("input[name='state']")).getAttribute("value"),
				state);
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("input[name='pinno']")).getAttribute("value"),
				pin);
		Assert.assertEquals(
				swd.getDriver().findElement(By.cssSelector("input[name='telephoneno']")).getAttribute("value"), mobile);
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("input[name='emailid']")).getAttribute("value"),
				email);

		generateUserData("update");
		swd.getDriver().findElement(By.cssSelector("textarea[name='addr']")).clear();
		swd.getDriver().findElement(By.cssSelector("textarea[name='addr']")).sendKeys(address);
		swd.getDriver().findElement(By.cssSelector("input[name='city']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='city']")).sendKeys(city);
		swd.getDriver().findElement(By.cssSelector("input[name='state']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='state']")).sendKeys(state);
		swd.getDriver().findElement(By.cssSelector("input[name='pinno']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='pinno']")).sendKeys(pin);
		swd.getDriver().findElement(By.cssSelector("input[name='telephoneno']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='telephoneno']")).sendKeys(mobile);
		swd.getDriver().findElement(By.cssSelector("input[name='emailid']")).clear();
		swd.getDriver().findElement(By.cssSelector("input[name='emailid']")).sendKeys(email);
		swd.getDriver().findElement(By.cssSelector("input[name='sub']")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table[id='customer']")));
		Assert.assertEquals(swd.getDriver().findElement(By.cssSelector("table[id='customer'] p")).getText(),
				"Customer details updated Successfully!!!");
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(),
				custName);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),
				"1990-03-31");
//		System.out.println(address);
//		System.out.println(swd.getDriver().findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText());
//		Assert.assertTrue(swd.getDriver().findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText().contains(address));
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(swd.getDriver().findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),
				pin);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
				mobile);
		Assert.assertEquals(
				swd.getDriver().findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);

		swd.getDriver().findElement(By.xpath("//a[text()='Continue']")).click();
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		swd = new SeleniumWebDriver(new ChromeDriver());
		wait = new WebDriverWait(swd.getDriver(), 15);
		generateUserData("new");
		swd.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@AfterClass
	public void afterClass() {
		swd.getDriver().quit();
	}

	private void generateUserData(String statusRequest) {
		if (statusRequest.equals("new")) {
			mainURL = "http://demo.guru99.com/v4/";
			registrationURL = "http://demo.guru99.com/";
			homepageURL = "http://demo.guru99.com/v4/manager/Managerhomepage.php";
			signupEmail = GenerateData.generateEmail(15);
			custName = GenerateData.generateRandomString(7) + " " + GenerateData.generateRandomString(6);
			gender = (String) GenerateData.generateRandomIntegerWithinRange(1, 3);
			address = GenerateData.generateRandomNumber(3) + " " + GenerateData.generateRandomString(7) + "\n"
					+ GenerateData.generateRandomString(7) + GenerateData.generateRandomString(7) + "\n"
					+ GenerateData.generateRandomNumber(3) + GenerateData.generateRandomString(7);
			city = GenerateData.generateRandomString(7) + " " + GenerateData.generateRandomString(6);
			state = GenerateData.generateRandomString(7) + " " + GenerateData.generateRandomString(6);
			pin = GenerateData.generateRandomNumber(6);
			mobile = GenerateData.generateRandomNumber(10);
			email = GenerateData.generateEmail(15);
			password = GenerateData.generateRandomAlphaNumeric(7);
		} else if (statusRequest.equals("update")) {
			address = GenerateData.generateRandomNumber(3) + " " + GenerateData.generateRandomString(7) + "\n"
					+ GenerateData.generateRandomString(7) + GenerateData.generateRandomString(7) + "\n"
					+ GenerateData.generateRandomNumber(3) + GenerateData.generateRandomString(7);
			city = GenerateData.generateRandomString(7) + " " + GenerateData.generateRandomString(6);
			state = GenerateData.generateRandomString(7) + " " + GenerateData.generateRandomString(6);
			pin = GenerateData.generateRandomNumber(6);
			mobile = GenerateData.generateRandomNumber(10);
			email = GenerateData.generateEmail(15);
		}

	}

	public void userRegistration() {
		swd.getDriver().get(registrationURL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("emailid")));
		swd.getDriver().findElement(By.name("emailid")).sendKeys(signupEmail);
		swd.getDriver().findElement(By.name("btnLogin")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='User ID :']")));
		login_user = swd.getDriver().findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		login_password = swd.getDriver().findElement(By.xpath("//td[text()='Password :']/following-sibling::td"))
				.getText();
		System.out.println(login_user + " " + login_password);
	}

}
