package com.myframework.utils;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;




public class CommonFunctions {
	
	private static Properties properties;
	private static InputStream inputStream;
	//private RemoteWebDriver driver;
	
	public static void loadproperties() throws IOException {
		properties = new Properties();
		inputStream = new FileInputStream("src/main/resources/config/config.properties");
	    properties.load(inputStream);
	}
	
	public static String getProperty(String keyname) {
		try {
			return properties.getProperty(keyname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static InheritableThreadLocal<XMLConfiguration> props = new InheritableThreadLocal<XMLConfiguration>() {
		@Override
		public XMLConfiguration initialValue() {
			XMLConfiguration p = new XMLConfiguration();
			return p;
		}
	};
	
	public static XMLConfiguration getConfig() {
		return props.get();
	}
	
	@SuppressWarnings("deprecation")
	public static String getDataFromExcel(String AppFunctionality, String DataSheetName, String columnName)
			throws IOException {
		FileInputStream fileInputStream = new FileInputStream(getProperty("excelFilePath"));
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
		String cellValue = null;
		int cellType;

		XSSFSheet sheet = workBook.getSheet(DataSheetName);
		int rowCount = sheet.getLastRowNum() + 1;
		int columnCount = sheet.getRow(0).getLastCellNum();
		int tempColumnNumber = 0;

		for (int iRow = 1; iRow < rowCount; iRow++) {
			XSSFRow row = sheet.getRow(iRow);
			if (AppFunctionality.equals(row.getCell(0).toString())) {
				for (int iColumn = 0; iColumn < columnCount; iColumn++) {
					XSSFRow firstRow = sheet.getRow(0);
					XSSFCell cell = firstRow.getCell(iColumn);
					if (columnName.equals(cell.getStringCellValue())) {
						tempColumnNumber = iColumn;
						break;
					}
				}
				XSSFRow currentRow = sheet.getRow(iRow);
				cellValue = currentRow.getCell(tempColumnNumber).toString();
				cellType = currentRow.getCell(tempColumnNumber).getCellType();
				if (cellType == Cell.CELL_TYPE_NUMERIC) {
					if (cellValue.endsWith(".0")) {
						cellValue = cellValue.substring(0, cellValue.indexOf("."));
					}
				}
				// System.out.println(
				// "The cell value for row " + "\"" + AppFunctionality + "\"" + " and column " +
				// "\"" + columnName
				// + "\"" + " in sheet " + "\"" + DataSheetName + "\"" + " is " + "\"" +
				// cellValue + "\"");
				fileInputStream.close();
				workBook.close();
				break;
			}
		}
		return cellValue;
	}



	public List<HashMap<String, String>> dataparser(String filepath, String sheetname) throws IOException{
		
		FileInputStream fs = new FileInputStream(filepath);
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		XSSFSheet sheet = wb.getSheet(sheetname);
		
		List<HashMap<String, String>> mydata = new ArrayList();
		Row headerrow = sheet.getRow(0);
		
		for(int i = 1; i <sheet.getPhysicalNumberOfRows(); i++) {
			
			Row currentrow = sheet.getRow(i);
			HashMap<String, String> currenthash = new HashMap();
			for (int j=0; j<currentrow.getPhysicalNumberOfCells();j++) {
				Cell currentcell = currentrow.getCell(j);
				switch(currentcell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					currenthash.put(headerrow.getCell(j).getStringCellValue(), currentcell.getStringCellValue());
				}
			}
			mydata.add(currenthash);
		}
		fs.close();
		return mydata;
	}
	
	  public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
			String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
	              //after execution, you could see a folder "FailedTestsScreenshots" under src folder
			String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
			return destination;
		}
	  
	  public RemoteWebDriver createDriver(ITestContext context) throws MalformedURLException {
		  String browsername = context.getCurrentXmlTest().getParameter("browsername");
		  String platformname = context.getCurrentXmlTest().getParameter("platformname");
		  RemoteWebDriver driver = null;
		if(platformname.equalsIgnoreCase("Windows")) {
			  if(browsername.equalsIgnoreCase("chrome") || browsername.equalsIgnoreCase("gc"))
		    	  driver = new ChromeDriver();
			  else  if(browsername.equalsIgnoreCase("firefox") || browsername.equalsIgnoreCase("ff"))
		    	  driver = new FirefoxDriver();
			  else if(browsername.equalsIgnoreCase("IE") || browsername.equalsIgnoreCase("Internate Explorer"))
		    	  driver = new InternetExplorerDriver();
	  	  }
		  else {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				
				String deviceName = context.getCurrentXmlTest().getParameter("deviceName");
				String port = context.getCurrentXmlTest().getParameter("port");

				
				capabilities.setCapability("browserName", browsername);
				capabilities.setCapability("chromedriverExecutable", "/Users/admin/Downloads/chromedriver85");
				capabilities.setCapability("deviceName", deviceName);
				//capabilities.setCapability("deviceName", "emulator-5554");
				capabilities.setCapability("automationName", "Appium");
				
				capabilities.setCapability("platformName", platformname);
				capabilities.setCapability("useAppiumForWeb", true);
				
				URL url = new URL("http://127.0.0.1:" + port + "/wd/hub");
				driver = new RemoteWebDriver(url, capabilities);
				
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		  }
		return driver;
	  }
	  
	  public AppiumDriver createMobileDriver(ITestContext context) throws IOException {

		  	String platformname = context.getCurrentXmlTest().getParameter("platformname");
			String deviceName = context.getCurrentXmlTest().getParameter("deviceName");
			String port = context.getCurrentXmlTest().getParameter("port");
			String appPackage = context.getCurrentXmlTest().getParameter("appPackage");
			String appActivity = context.getCurrentXmlTest().getParameter("appActivity");
			String app = context.getCurrentXmlTest().getParameter("app");

				DesiredCapabilities capabilities = new DesiredCapabilities();
	
				capabilities.setCapability("deviceName", deviceName);
				capabilities.setCapability("automationName", "Appium");
				
				capabilities.setCapability("platformName", platformname);

				//capabilities.setCapability("fullReset", true);
				
				capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
				
				capabilities.setCapability("appPackage", appPackage);
				capabilities.setCapability("appActivity", appActivity);
				
				 CommonFunctions cf = new CommonFunctions();
				 cf.loadproperties();
				 
				
				 System.out.println();
				capabilities.setCapability("app", System.getProperty("user.dir") + "/" + cf.getProperty("MobileAppBinaryPath") + app);
		        
		        URL url = new URL("http://127.0.0.1:" + port + "/wd/hub");
		        
		        AppiumDriver driver = new AndroidDriver(url, capabilities);
	
				//driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		  
		return driver;
	  }
}
