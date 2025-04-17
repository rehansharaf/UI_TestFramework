package com.marina.tests;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.marina.base.BrowserFactory;
import com.marina.base.TestBase;
import com.marina.pages.HomePage;
import com.marina.pages.LoginPage;
import com.marina.utils.Log;

public class Mod_1_login extends TestBase {

	LoginPage lp;
	HomePage hp;
	

	@Test(groups = "smoke", priority = 1, 
			description = "insert login credential, and click on sign in button,  page should redirect to home page dashboard")

	public void verifySuccessfulLogin_TC_001() {

		Log.startTestCase("Verify Successful Login Only TC_001");
		lp = new LoginPage(driver);
		hp = lp.login(prop.getProperty("email"), prop.getProperty("password"));
		
		Assert.assertEquals(hp.verifyHomePageHeading(), "rehans");
		Log.endTestCase("Verify Successful Login Only TC_001");

	}
	
	
	@Test(groups = {"sanity", "regression"}, priority = 2,
			description = "Enter Incorrect login credential, and click on sign in button,  Error Msg should appear")
	
	public void verifyLoginIncorrectData_TC_002() {
		
		
		Log.startTestCase("Verify Successful Login Only TC_001");
		lp = new LoginPage(driver);
		hp = lp.login(prop.getProperty("email"), "rehan123");
		
		Assert.assertEquals(hp.verifyLoginError(), "Your email or password is incorrect!");
		Log.endTestCase("Verify Incorrect Data Login TC_002");
	
	
	}
	
	
	
	
	
	
//	(dataProvider = "credentials", dataProviderClass = DataProviders.class)



}
