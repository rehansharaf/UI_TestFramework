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
import com.marina.pages.ProductPage;
import com.marina.utils.Log;

public class Mod_2_Product extends TestBase {
	
	LoginPage lp;
	HomePage hp;
	ProductPage pp;
	
	
	
	@Test(groups = "smoke", priority = 1,
			description = "Verify Product Page is getting opened")
	public void verifyProductPage() {
		
		Log.startTestCase("Verify Product Page TC_101");
		lp = new LoginPage(driver);
		hp = lp.login(prop.getProperty("email"), prop.getProperty("password"));
		pp = hp.clickProductLink();
		
		Assert.assertEquals(pp.verifyProductPage(), "ALL PRODUCTS");
		Log.endTestCase("Verify Product Page TC_101");

		
	}
	

}
