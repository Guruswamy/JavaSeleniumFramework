package com.myframework.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomeScreenPageObject {
	
	private WebDriver driver;
	
	@FindBy(xpath="//input[@name='username']")
	WebElement 	UserName;

	@FindBy(xpath="//input[@name='password']")
	WebElement 	PassWord;
	
	@FindBy(xpath="//button[@name='kuku']")
	WebElement 	loginButton;

	public HomeScreenPageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void navigateurl(String url) throws InterruptedException {
		driver.get(url);
		Thread.sleep(2000);
	}
	
	public void logintoapp(String username, String password) throws InterruptedException {
		UserName.sendKeys(username);
		Thread.sleep(2000);
		PassWord.sendKeys(password);
		Thread.sleep(3000);
		loginButton.click();
	}
	
	public void HomePage() throws Exception {
		String text = driver.findElement(By.xpath("//a[contains(text(),'OCR Page')]")).getText();
		if(!text.contains("OCR"))
			throw new Exception("Home page not seen");
	}
}
