package com.myframework.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CloudGreyScreenPageObject {
	
	private AppiumDriver driver;
	
	@AndroidFindBy(accessibility="Login Screen")
	MobileElement 	loginScreenBtn;

	@AndroidFindBy(accessibility="username")
	MobileElement 	userNameTxt;
	
	@AndroidFindBy(accessibility="password")
	MobileElement 	passWordTxt;

	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"loginBtn\"]/android.widget.TextView")
	MobileElement 	loginBtn;

    
	public CloudGreyScreenPageObject(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}
	
	public void launchApp() throws InterruptedException {
		driver.launchApp();
	}
	
	public void openLoginSreenPage() throws InterruptedException {
		loginScreenBtn.click();
	}
	
	
	public void loginToSampleApp(String username, String password) throws InterruptedException {

		userNameTxt.sendKeys(username);
		Thread.sleep(2000);
		passWordTxt.sendKeys(password);
		Thread.sleep(1000);
		loginBtn.click();
	}
	
	public void HomePage() throws Exception {
		String text = driver.findElement(By.id("android:id/message")).getText();
		if(!text.contains("Invalid login"))
			throw new Exception("Invalid Login Alert should be displayed, but not");
		
	}
}
