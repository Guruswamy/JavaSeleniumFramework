package com.myframework.steps;

import org.openqa.selenium.WebDriver;

import com.myframework.utils.CommonFunctions;
import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {

	public WebDriver getCurrentDriverObject () {
		return (WebDriver)CommonFunctions.getConfig().getProperty("driver.instance");
	}
	
	@Before()
	public void setUp(Scenario scenario) {
		Reporter.addScenarioLog(CommonFunctions.getConfig().getProperty("driver.instance").toString());
//	    Reporter.getExtentReport().createTest(CommonFunctions.getConfig().getProperty("Testngtestname.instance").toString()+ "-" +  scenario.getName());
        
	}


	
	@After()
	public void tearDown(Scenario scenario) throws Exception {
		//Add screenshots to Extent if Scenario is failed
		if(scenario.getStatus().equalsIgnoreCase("failed")) {
			String screenshotPath = CommonFunctions.getScreenshot(getCurrentDriverObject (), scenario.getName());
			Reporter.addScreenCaptureFromPath(screenshotPath);
		}
	}
}
