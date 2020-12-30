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


public class NxcWebTestStepDef {
	
	public LoginScreenPageObject getLoginScreenobject () {
		return (LoginScreenPageObject)CommonFunctions.getConfig().getProperty("LoginScreen.instance");
	}
	
	public HomeScreenPageObject getHomeScreenobject () {
		return (HomeScreenPageObject)CommonFunctions.getConfig().getProperty("HomeScreen.instance");
	}
	
	@Given("I launched Nxc Web url")
	public void navigateUrl() throws IOException {
		CommonFunctions.loadproperties();
		
		getLoginScreenobject().navigateurl(CommonFunctions.getProperty("url"));
	}
	
	@When("^I login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void logintoapp(String username, String password) throws InterruptedException {
		getHomeScreenobject().logintoapp(username, password);
	}
	
	@Then("I should see home page")
	public void homepage() throws Exception {
		getHomeScreenobject().HomePage();
	}
	
	@When("^I login with valid username and password$")
	public void logintoapp2() throws IOException, InterruptedException {
		getHomeScreenobject().logintoapp(CommonFunctions.getDataFromExcel("ValidLogin", "DemoWeb", "userName"),
				CommonFunctions.getDataFromExcel("ValidLogin", "DemoWeb", "password"));
	}
	
//	@When("^User login with the following username and password with data in excel at \"([^\"]*)\"$")
//	public void user_login_with_the_following_username_and_password_with_data_in_excel_at(@Transform(ExcelDataToDataTable.class) DataTable table) throws Throwable {
//		System.out.println(table.toString());
//		
//		List<String> dataList = table.asList(String.class);
//		
//		for(String str : dataList){
//			System.out.println(str);
//		}
//		
//	}
	
	@When("^I login with valid username and password with datatable$")
	public void i_login_with_valid_username_and_password_with_datatable(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
		CommonFunctions.loadproperties();
		//List<List<String>> test = arg1.raw();
		for(List<String> test : arg1.raw()) {
			System.out.println(test);
			getLoginScreenobject().navigateurl(CommonFunctions.getProperty("url"));
			getHomeScreenobject().logintoapp(test.get(0), test.get(1));
			getHomeScreenobject().HomePage();
		}
	}
	
	@When("^I login with valid username and password with datatable and maps$")
	public void i_login_with_valid_username_and_password_with_datatable_maps(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
		CommonFunctions.loadproperties();
		//List<List<String>> test = arg1.raw();
		for(Map<String, String> test : arg1.asMaps(String.class, String.class)) {
			System.out.println(test);
			getLoginScreenobject().navigateurl(CommonFunctions.getProperty("url"));
			getHomeScreenobject().logintoapp(test.get("username"), test.get("password"));
			getHomeScreenobject().HomePage();
		}
	}
	
	@Given("^Validate logins with given data at \"([^\"]*)\"$")
	public void validate_logins_with_given_data_at(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		CommonFunctions cf = new CommonFunctions();
		CommonFunctions.loadproperties();
	    List<HashMap<String, String>> t = cf.dataparser(arg1, "DemoWeb");
	    for (HashMap<String, String> tel: t) {
	    	//This is sample test creating to push scenario details to report
			Reporter.getExtentReport().createTest("DataDrivenScenario = " + tel, "DataDrivenTest As individual Scenarios");
			getLoginScreenobject().navigateurl(CommonFunctions.getProperty("url"));
			getHomeScreenobject().logintoapp(tel.get("userName"), tel.get("password"));
			getHomeScreenobject().HomePage();
			Reporter.getExtentReport().flush();
	    }
	}
	
	
	@Given("^Validate logins with given data at \"([^\"]*)\" in \"([^\"]*)\"$")
	public void validate_logins_with_given_data_at_in(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		CommonFunctions cf = new CommonFunctions();
		CommonFunctions.loadproperties();
	    List<HashMap<String, String>> t = cf.dataparser(arg2, "DemoWeb");
	    System.out.println(t);
	    Log.info(CommonFunctions.getConfig().getProperty("Testngtestname.instance")  + "--" + t.get(Integer.parseInt(arg1)-1).toString());
		getLoginScreenobject().navigateurl(CommonFunctions.getProperty("url"));
		getHomeScreenobject().logintoapp(t.get(Integer.parseInt(arg1)-1).get("userName"), t.get(Integer.parseInt(arg1)-1).get("password"));
		getHomeScreenobject().HomePage();
	}
}
