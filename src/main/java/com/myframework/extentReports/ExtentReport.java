package com.myframework.extentReports;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReport {
	
	    ExtentReports extent;
	    ExtentTest logger;
	   public ExtentReports setUpReport (String testname)
	   {
			return extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/ExecutionReport_"+testname+".html", true);
	   }
	   
	   public void logEvents (String value)
	   {
		   logger.log(LogStatus.PASS, value);
	   }
	   
	   public ExtentTest startTestCase (String testcaseName)
	   {
		   return logger = extent.startTest(testcaseName);
	   }
	   public void createFinalReport()
	   {
		   extent.flush();
	   }
	   
		public void getResult(ITestResult result, WebDriver driver) throws Exception{
			if(result.getStatus() == ITestResult.FAILURE){
				logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
				logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
				//To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
	                      //We do pass the path captured by this mehtod in to the extent reports using "logger.addScreenCapture" method. 			
	                      String screenshotPath = getScreenshot(driver, result.getName());
				//To add it in the extent report 
	                      logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
			}else if(result.getStatus() == ITestResult.SKIP){
				logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
			}
		}
		
		  private static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
				String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				TakesScreenshot ts = (TakesScreenshot) driver;
				File source = ts.getScreenshotAs(OutputType.FILE);
		              //after execution, you could see a folder "FailedTestsScreenshots" under src folder
				String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
				File finalDestination = new File(destination);
				FileUtils.copyFile(source, finalDestination);
				return destination;
			}

}
