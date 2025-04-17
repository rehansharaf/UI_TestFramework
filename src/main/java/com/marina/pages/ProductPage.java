package com.marina.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.marina.actiondriver.Action;

public class ProductPage {

	WebDriver driver;
	Action action;

	
	By productPageHeading = By.xpath("//h2[normalize-space()='All Products']");
	
	public ProductPage(WebDriver driver) {

		this.driver = driver;
		action = new Action(driver);
		PageFactory.initElements(driver, this);
	}

	

	public String verifyProductPage() {

		return driver.findElement(productPageHeading).getText();
	
	}
	
	

}
