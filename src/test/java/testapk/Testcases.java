package testapk;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjects.appobjects;

public class Testcases {


	@BeforeSuite
	@Severity(SeverityLevel.BLOCKER) 
	//-----------------------------------------------Starts appium and emulator-------------------------------------------------------//
	public void startappemu() throws InterruptedException, IOException {
		ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "appiumemulator.bat");
		File dir = new File("src");
		pb1.directory(dir);
		Process p1 = pb1.start();
		Thread.sleep(10000);
		System.out.println("Appium and Emulator started succcesfully");
	}


	@BeforeTest
	@Severity(SeverityLevel.BLOCKER) 
	public static AndroidDriver<AndroidElement> Capabilities() throws MalformedURLException
	//-----------------------------------------------Driver function-------------------------------------------------------//
	{
		File file = new File("src");
		File location = new File(file,"app-release-signed.apk");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "firstemulator");
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
		cap.setCapability(MobileCapabilityType.APP, location.getAbsolutePath());
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),cap);
		System.out.println("driver testcase");
		return driver;
	}


	@Test(priority=1)
	@Severity(SeverityLevel.CRITICAL)
	//-----------------------------------------------Correct email and correct password-------------------------------------------------------//
	public void tc1() throws MalformedURLException, InterruptedException {     
		AndroidDriver<AndroidElement> driver=Capabilities();
		appobjects ao=new appobjects(driver);
		WebDriverWait loadwait = new WebDriverWait(driver, 30);
		loadwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Jenkins-CI-Test']")));
		ao.selectemail.sendKeys("abbasshah17@gmail.com");
		ao.selectpassword.sendKeys("12345678");
		ao.selectloginbutton.click();
		loadwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.jenkins_ci_test:id/loginStatus")));
		String toasttext = ao.checkloginstatus.getText();
		System.out.println(toasttext);
		Assert.assertEquals("Login successful", toasttext);
	}


	@Test(priority=2)
	@Severity(SeverityLevel.CRITICAL)
	//-----------------------------------------------Incorrect email-------------------------------------------------------//
	public void tc2() throws MalformedURLException, InterruptedException {     
		AndroidDriver<AndroidElement> driver=Capabilities();
		appobjects ao=new appobjects(driver);
		WebDriverWait loadwait = new WebDriverWait(driver, 30);
		loadwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Jenkins-CI-Test']")));
		ao.selectemail.sendKeys("engrmharis@gmail.com");
		ao.selectpassword.sendKeys("12345678");
		ao.selectloginbutton.click();
		loadwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.jenkins_ci_test:id/loginStatus")));
		String toasttext = ao.checkloginstatus.getText();
		System.out.println(toasttext);
		Assert.assertEquals("Email not valid", toasttext);
	}


	@Test(priority=3)
	@Severity(SeverityLevel.NORMAL)
	//-----------------------------------------------empty email and empty password-------------------------------------------------------//
	public void tc3() throws MalformedURLException, InterruptedException {     
		AndroidDriver<AndroidElement> driver=Capabilities();
		appobjects ao=new appobjects(driver);
		WebDriverWait loadwait = new WebDriverWait(driver, 30);
		loadwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Jenkins-CI-Test']")));
		ao.selectloginbutton.click();
		loadwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.jenkins_ci_test:id/loginStatus")));
		String toasttext = ao.checkloginstatus.getText();
		System.out.println(toasttext);
		Assert.assertEquals("Please enter valid email and password", toasttext);
	}


	@Test(priority=4)
	@Severity(SeverityLevel.MINOR)
	//-----------------------------------------------correct email and empty password-------------------------------------------------------//
	public void tc4() throws MalformedURLException, InterruptedException {     
		AndroidDriver<AndroidElement> driver=Capabilities();
		appobjects ao=new appobjects(driver);
		WebDriverWait loadwait = new WebDriverWait(driver, 30);
		loadwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Jenkins-CI-Test']")));
		ao.selectemail.sendKeys("abbasshah17@gmail.com");
		ao.selectloginbutton.click();
		loadwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.jenkins_ci_test:id/loginStatus")));
		String toasttext = ao.checkloginstatus.getText();
		System.out.println(toasttext);
		Assert.assertEquals("Please enter valid email and password", toasttext);
	}


	@Test(priority=5)
	@Severity(SeverityLevel.TRIVIAL)
	//-----------------------------------------------correct email and empty password-------------------------------------------------------//
	public void tc5() throws MalformedURLException, InterruptedException {     
		AndroidDriver<AndroidElement> driver=Capabilities();
		System.out.println("Test jenkinsCIapp new");
	}


	@AfterSuite
	@Severity(SeverityLevel.TRIVIAL) 
	//-----------------------------------------------Stops appium and emulator-------------------------------------------------------//
	public void stopappemu() throws InterruptedException, IOException {
		ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "stopappiumemulator.bat");
		File dir2 = new File("src");
		pb2.directory(dir2);
		Process p2 = pb2.start();
		System.out.println("Appium and Emulator stopped succcesfully");
	}
}