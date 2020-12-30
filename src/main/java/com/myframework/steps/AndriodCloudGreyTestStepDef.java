package com.myframework.steps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.myframework.utils.CommonFunctions;
import com.myframework.pageObjects.CloudGreyScreenPageObject;
import com.myframework.pageObjects.HomeScreenPageObject;
import com.myframework.pageObjects.LoginScreenPageObject;
import com.myframework.utils.CommonFunctions;
import com.myframework.utils.Log;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.vimalselvam.cucumber.listener.ExtentCucumberFormatter;
import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class AndriodCloudGreyTestStepDef {
	
	public CloudGreyScreenPageObject getCloudGreyScreenobject () {
		return (CloudGreyScreenPageObject)CommonFunctions.getConfig().getProperty("CloudGreyScreen.instance");
	}
	
	
	@Given("^I launched the app$")
	public void i_launched_the_app() throws Throwable {
		getCloudGreyScreenobject().launchApp();
	}

	@Then("^I click on Login Screen Button$")
	public void i_click_on_Login_Screen_Button() throws Throwable {
		getCloudGreyScreenobject().openLoginSreenPage();

	}

	@When("^I tried to login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_tried_to_login_with_username_and_password(String username, String password) throws Throwable {
		getCloudGreyScreenobject().loginToSampleApp(username, password);

	}

	@Then("^I should see Error page$")
	public void i_should_see_Error_page() throws Throwable {
		getCloudGreyScreenobject().HomePage();

	}
	
}
