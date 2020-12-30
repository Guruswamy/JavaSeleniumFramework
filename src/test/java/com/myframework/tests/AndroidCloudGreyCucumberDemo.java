package com.myframework.tests;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.collections.Maps;

import com.myframework.utils.CommonFunctions;
import com.myframework.utils.Log;
import com.myframework.pageObjects.CloudGreyScreenPageObject;
import com.myframework.pageObjects.HomeScreenPageObject;
import com.myframework.pageObjects.LoginScreenPageObject;

import com.vimalselvam.cucumber.listener.ExtentCucumberFormatter;
import com.vimalselvam.cucumber.listener.ExtentProperties;
import com.vimalselvam.cucumber.listener.Reporter;
import com.vimalselvam.testng.listener.ExtentTestNgFormatter;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import gherkin.formatter.model.Result;
import io.appium.java_client.AppiumDriver;

//@CucumberOptions(features = { "src/main/resources/scenarios" }, glue = { "com.myframwork.steps" },tags= {"@nxcWebApp_1"})
@CucumberOptions(features= {"src/main/resources/scenarios"},
										  glue= {"com.myframework.steps"},
										  tags= {"@cloudGreyAppium"},
										  //plugin = { "pretty", "html:target/cucumber-reports" },
										//plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
										plugin = {"pretty", "html:target/cucumber-reports",  "com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:"},
										monochrome = true,
										  dryRun = false)
public class AndroidCloudGreyCucumberDemo extends AbstractTestNGCucumberTests {

	private static String ExtentReportPath;
	private AppiumDriver driver;
	private LoginScreenPageObject LoginScreenPageObject;
	private HomeScreenPageObject HomeScreenPageObject;


	@Parameters({"platformname"})
	@BeforeTest
	public void beforeall(ITestContext context, String platformname) throws IOException {

		 CommonFunctions cf = new CommonFunctions();
		 cf.loadproperties();

		  context.getCurrentXmlTest().getAllParameters();

	      driver = cf.createMobileDriver(context);
	      
	      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		  CommonFunctions.getConfig().setProperty("Testngtestname.instance", context.getName());
	      CommonFunctions.getConfig().setProperty("driver.instance", driver);
	      CommonFunctions.getConfig().setProperty("CloudGreyScreen.instance", new CloudGreyScreenPageObject(driver));
	}

	@AfterTest
	public void afterall() {
		driver.quit();
	}
	
	 @BeforeClass
	    public static void setup(ITestContext context) {
	        ExtentProperties extentProperties = ExtentProperties.INSTANCE;
	        //extentProperties.setReportPath("output/myreport.html");
	        ExtentReportPath = extentProperties.getReportPath().replace("/report.html", "");
	        DOMConfigurator.configure("log4j.xml");
	        Log.startTestCase(context.getName());
	    }

	    @AfterClass
	    public static void teardown(ITestContext context) {
	        //Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
	        Reporter.loadXMLConfig("src/main/java/com/myframework/extentReports/extent-config.xml");
	        Reporter.setSystemInfo("user", System.getProperty("user.name"));
	        Reporter.setSystemInfo("os", "Mac OSX");
	        Reporter.setTestRunnerOutput("Sample test runner output message");
	        Log.endTestCase(context.getName());
	        
	        //Moving generated log files to respective Extent Reporting folder
	    	 File afile =new File("logfile.log");
	    		
	    	   if(afile.renameTo(new File(ExtentReportPath +"/"+ afile.getName()))){
	    		System.out.println("File moved Successfully!");
	    	   }else{
	    		System.out.println("Failed to Move the file!");
	    	   }
	    }
}
