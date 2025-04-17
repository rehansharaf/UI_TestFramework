package com.marina.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.marina.utils.ExtentManager;
import com.marina.utils.Seeder;

public class TestBase {

	public WebDriver driver;
	public static Properties prop;
	

	@BeforeSuite(alwaysRun = true)
	public synchronized void loadConfig() throws IOException {

		FileUtils.cleanDirectory(new File(System.getProperty("user.dir")+"/screenshots"));
		//FileUtils.cleanDirectory(new File(System.getProperty("user.dir")+"\\test-output"));
		File screeshot_file = new File(System.getProperty("user.dir")+"/screenshots/.gitkeep");
		screeshot_file.createNewFile();
		
		ExtentManager.setExtent();
		DOMConfigurator.configure("log4j.xml");

		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/configuration/config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		//Seeder Data
//		try {
//			Seeder seeder = new Seeder();
//			seeder.importSeederData();
//		} catch (Exception e) {}
		
	}

	@Parameters({"browser", "remote", "remoteUrl"})
	@BeforeMethod(alwaysRun = true)
	public synchronized void browserIntialization(String xmlBrowser,
												  @Optional("false") String remote,
												  @Optional("") String remoteUrl) {
		
		// Set System property that will be passed through mvn command
        String browser = System.getProperty("browser");
        
        if (browser == null || browser.isEmpty()) {
            System.setProperty("browser", xmlBrowser); // fallback to testNG xml value
            browser = System.getProperty("browser");
        }


        boolean useRemote = Boolean.parseBoolean(remote);
        int maxRetries = Integer.parseInt(prop.getProperty("browserLaunch_maxRetry"));
        BrowserFactory.getInstance().setDriver(browser, useRemote, remoteUrl, maxRetries);        
		driver = BrowserFactory.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("implicit_wait_timeout"))));
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
				

	}

	public void seederBrowserIntialization() {

		BrowserFactory.getInstance().setDriver(prop.getProperty("seederData_browser"));
		driver = BrowserFactory.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("implicit_wait_timeout"))));
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
				
	}
	
	
	@AfterMethod(alwaysRun = true)
	public synchronized void afterTest() {

		driver.get(prop.getProperty("logout_url"));
		BrowserFactory.getInstance().removeDriver();

	}


	@AfterSuite(alwaysRun = true)
	public synchronized void afterSuite() {
		ExtentManager.endReport();
	}

}
