package com.myframework.tests;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import com.myframework.pageObjects.HomeScreenPageObject;
import com.myframework.pageObjects.LoginScreenPageObject;
import com.myframework.utils.CommonFunctions;
import com.myframework.extentReports.ExtentReport;
import com.vimalselvam.cucumber.listener.ExtentProperties;
import com.vimalselvam.testng.listener.ExtentTestNgFormatter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class NxcWebTestsTestNg {
	
	public RemoteWebDriver driver;
    CommonFunctions cf;
    
    
    private static String ExtentReportPath;
	LoginScreenPageObject LoginScreenPageObject;
	HomeScreenPageObject HomeScreenPageObject;

	//BaseClass BaseClass;
  @Test
  public void nxcwebtest1() throws IOException, InterruptedException {
	  cf.loadproperties();
	  
	  LoginScreenPageObject.navigateurl(cf.getProperty("url"));
	  HomeScreenPageObject.logintoapp(cf.getProperty("username"), cf.getProperty("password"));  

	  Assert.assertTrue(false);
  }

  
  @Test(dataProvider = "DataDrivenTests")
  public void nxcwebtest4(String username, String password) throws InterruptedException {

	  LoginScreenPageObject.navigateurl("http://nxc.co.il/demoaut/index.php");
	  HomeScreenPageObject.logintoapp(username, password);  
	  
  }
  
  @Parameters({"platformname", "browsername", "instance"})
  @BeforeClass
  public void beforeClass(ITestContext context, String platformname, String browsername, String instance) throws IOException {
	  ExtentProperties extentProperties = ExtentProperties.INSTANCE;
      ExtentReportPath = extentProperties.getReportPath().replace("/report.html", "");

		 CommonFunctions cf = new CommonFunctions();
		 cf.loadproperties();
		 System.setProperty("webdriver.chrome.driver", cf.getProperty("webdriver.chrome.driver"));
		 
	  driver = cf.createDriver(context);

	  LoginScreenPageObject =  new LoginScreenPageObject(driver);
	  HomeScreenPageObject = new HomeScreenPageObject(driver);
  }

  
  @AfterMethod
  public void afterMethod(ITestResult iTestResult) throws Exception {
      // The ITestResult is a mandatory parameter
		if(iTestResult.getStatus() == ITestResult.FAILURE){
			String screenshotPath = CommonFunctions.getScreenshot(driver, iTestResult.getName());
			ExtentTestNgFormatter.getInstance().addScreenCaptureFromPath(iTestResult, screenshotPath);
		}
  }
 
  @AfterClass
  public void afterClass() {
	  driver.quit();
	  //extent.flush();
  }
  
  
	@DataProvider(name="DataDrivenTests")
	public Object[][] passdata() throws IOException{
		
		String ExcelPath = "src/main/resources/data/InputData.xlsx";		
		CommonFunctions cf = new CommonFunctions();
	    List<HashMap<String, String>> t = cf.dataparser(ExcelPath, "DemoWeb");
	    
	    Object[][] data = new Object[t.size()][2];
	    int count = 0;
	    for (HashMap<String, String> tel: t) {
	    	data[count][0] = tel.get("userName");
	    	data[count][1] = tel.get("password");
	    	count++;
	    }
	
		return data;
	}
}
