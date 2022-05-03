package com.mkyong.web.selenium;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;


public class TestSelenium {
	public String baseUrl = "http://3.210.29.59:8080/";  
	String driverPath = "/usr/bin/chromedriver";  
	public WebDriver driver ;
	
	@Test             
	public void testSeleniumUrl() {      
	System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--no-sandbox","--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors","--disable-extensions","--disable-dev-shm-usage");
    driver = new ChromeDriver(options);	   
	driver.get(baseUrl);
	
	String URL= driver.getCurrentUrl();  
	System.out.print("Url is --> "+ URL);
	assertEquals(baseUrl, URL);

	}
	
	@Test             
	public void testSeleniumPageDetails() {      
	System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--no-sandbox","--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors","--disable-extensions","--disable-dev-shm-usage");
	driver = new ChromeDriver(options);
	driver.get(baseUrl);  
	 
	String nameText = driver.findElement(By.id("myMsg")).getText();
	System.out.println("Header is --> "+ nameText); 
	assertEquals("Hello World - Prasanna welcomes you.", nameText);  
	   
        String envText = driver.findElement(By.id("myEnv")).getText();
	System.out.println("envText is --> "+ envText); 
	assertEquals("dev", envText);
	   
	String dateText = driver.findElement(By.id("myTime")).getText();
	System.out.println("Date --> "+ dateText); 
	assertNotNull(dateText);
	
	}
	
	@BeforeEach 
	public void beforeTest() {    
	System.out.println("Before Selenium Test");
	
	}     
	@AfterEach  
	public void afterTest() {  
	driver.quit();  
	System.out.println("After Selenium Test ");  
	}         
}

