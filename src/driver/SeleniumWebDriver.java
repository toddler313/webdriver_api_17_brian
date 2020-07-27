package driver;

import org.openqa.selenium.WebDriver;

public class SeleniumWebDriver {
	private WebDriver driver;
	
	public SeleniumWebDriver(WebDriver driver) {
		setDriver(driver);
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver getDriver() {
		return this.driver;
	}
}
