package com.myframework.pageObjects;



import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;



public class LocalAppium {

	private static AppiumDriverLocalService service;
	
   // private static String APP = "/Users/admin/Guru/MyPerData/MyTraining/TheApp-v1.10.0.apk";
	//  private static String APP = "/Users/admin/Guru/MyPerData/MyTraining/Appium/app_1.11.23_alpha_01-debug.apk";
	  private static String APP = "/Users/admin/Guru/MyPerData/MyTraining/Appium/ExpenseHybridAppVer1.0.apk";


	public static void main(String[] args) throws MalformedURLException {
		
		AppiumDriver driver = null;
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        //caps.setCapability("platformVersion", "12.2");
        caps.setCapability("deviceName", "25e5780d331c7ece");
        //caps.setCapability("automationName", "XCUITest");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("fullReset", true);
        
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");

        
//        caps.setCapability("appPackage", "io.cloudgrey.the_app");
//        caps.setCapability("appActivity", "io.cloudgrey.the_app.MainActivity");
        
//        caps.setCapability("appPackage", "com.triesten.trucktax");
//        caps.setCapability("appActivity", "com.triesten.trucktax.eld.activity.InitialSplash");

      caps.setCapability("appPackage", "io.perfecto.expense.tracker.hybrid");
      caps.setCapability("appActivity", "io.perfecto.expense.tracker.hybrid.MainActivity");
        
        caps.setCapability("app", APP);
        
        driver = new AndroidDriver(url, caps);
        
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        
        System.out.println(driver.getContext());
        
        //driver.launchApp();
        
//        driver.findElementByAccessibilityId("Login Screen").click();
//       
//        MobileElement el2 = (MobileElement) driver.findElementByAccessibilityId("username");
//        el2.sendKeys("sample Email");
//        MobileElement el3 = (MobileElement) driver.findElementByAccessibilityId("password");
//        el3.sendKeys("sample Password");
//        MobileElement el4 = (MobileElement) driver.findElementByXPath("//android.view.ViewGroup[@content-desc=\"loginBtn\"]/android.widget.TextView");
//        el4.click();
//        MobileElement el5 = (MobileElement) driver.findElementById("android:id/button1");
//        el5.click();
        
        MobileElement el2 = (MobileElement) driver.findElementById("com.android.permissioncontroller:id/permission_allow_foreground_only_button");
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementById("com.android.permissioncontroller:id/permission_allow_button");
        el3.click();
        
		driver.quit();
		
		
		
	}

}
