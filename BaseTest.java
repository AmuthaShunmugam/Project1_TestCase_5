package com.ibm.test;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ibm.pages.AdminPage;
import com.ibm.utilities.ExcelUtil;
import com.ibm.utilities.PropertiesFileHandler;

public class BaseTest {
	WebDriver driver;
	WebDriverWait wait;
	PropertiesFileHandler propFIleHandler;
	HashMap<String, String> data;
	@BeforeSuite
	public void propertiesfile() throws IOException
	{
		 String file = "./TestData/data.properties";
		 	PropertiesFileHandler propFileHandler = new PropertiesFileHandler();
		 	data = propFileHandler.getPropertiesAsMap(file); 
	}
	@BeforeMethod
	public void BrowserInitialization()
	{
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
	 	driver = new ChromeDriver();
	 	wait = new WebDriverWait(driver, 60);
	 	driver.manage().window().maximize();
	 	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
	}
		@Test(priority = 1, testName = "Modified Cost")
	public void ModifiedCost() throws IOException, InterruptedException {
		String url = data.get("url");
		String userName = data.get("username");
		String password = data.get("password");
		String search=data.get("Search");
		String cost=data.get("Modifiedcost");
        driver.get(url); 
        //To get the method created in Admin page
		AdminPage home = new AdminPage(driver, wait);
        //To get the mail id or user name
		home.EnetrEmailAddress(userName);
        //To give to password
		home.EnetrPassword(password);
        //To give the password
		home.ClickonLoginButton();
		//To click on 'System Options' tab
		home.ClickOnSystemOptions();
		//To click on 'Shipping' under 'System'
		home.ClickOnShipping(); 
		home.ClickOnShipping();
		//To click on 'Shipping Method' under 'Shipping'
		home.ClickOnShippingMethod();
		//To click on Action button in one of the record
		home.ClickOnShippingMethodAction();
		//To Click on Edit button
		home.ClickOnShippingMethodEdit();
		//To modify the cost
		home.EnterValueofCost(cost);
		//To click on save button
		home.ClickonTheSaveButton();
		WebElement cost1=driver.findElement(By.xpath("//table[@id='dataTableExample2']/tbody/tr/td[2]"));
		String Cost2=cost1.getText().trim().replace(",","").replace(".00","");
		System.out.println(Cost2);
		Assert.assertEquals(cost,Cost2);
		System.out.println("The modification has happened");
		}

}
