package com.myframework.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginScreenPageObject {
	
	private WebDriver driver;
	
	@FindBy(xpath="")
	WebElement 	UserName;

	@FindBy(xpath="")
	WebElement 	PassWord;

	public LoginScreenPageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void navigateurl(String url) {
		driver.get(url);
	}
}
