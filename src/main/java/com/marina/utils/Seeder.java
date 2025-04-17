package com.marina.utils;

import org.testng.annotations.Parameters;

import com.marina.base.BrowserFactory;
import com.marina.base.TestBase; 
import com.marina.pages.HomePage;
import com.marina.pages.LoginPage;

public class Seeder extends TestBase {
	
	LoginPage lp;
	HomePage hp;
	Database db;

	
	public Seeder() throws Exception {
		
		//db = new Database();

	}
	
	public void importSeederData() throws Exception {
		
		//Initiate Login Request
		login();
		
		closeBrowser();
	}
	
	
	public void closeBrowser() {

		driver.get(prop.getProperty("logout_url"));
		BrowserFactory.getInstance().removeDriver();
	}

	public void login() {
			
		seederBrowserIntialization();
		lp = new LoginPage(driver);
		hp = lp.login(prop.getProperty("email"), prop.getProperty("password"));
	}

}
