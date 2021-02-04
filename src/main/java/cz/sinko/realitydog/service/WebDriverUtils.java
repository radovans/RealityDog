package cz.sinko.realitydog.service;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebDriverUtils {

	public static void openWebsite(WebDriver webDriver, String url, long timeout, int wait)
			throws InterruptedException {
		webDriver.navigate().to(url);
		webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		WaitUtils.wait(wait);
	}

	public static void fillInput(WebDriver webDriver, String xpath, String input, long timeout, int wait)
			throws InterruptedException {
		webDriver.findElement(By.xpath(xpath)).sendKeys(input);
		webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		WaitUtils.wait(wait);
	}

	public static void buttonClick(WebDriver webDriver, String xpath, long timeout, int wait)
			throws InterruptedException {
		webDriver.findElement(By.xpath(xpath)).click();
		webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		WaitUtils.wait(wait);
	}

}
