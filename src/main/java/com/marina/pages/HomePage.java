package com.marina.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.marina.actiondriver.Action;

public class HomePage {

	WebDriver driver;
	Action action;

	
	By homePageHeading = By.xpath("//b[normalize-space()='rehan']");
	By invalidCredentialsMsg = By.xpath("//p[normalize-space()='Your email or password is incorrect!']");
	By productLink = By.xpath("//a[@href='/products']");
	
	public HomePage(WebDriver driver) {

		this.driver = driver;
		action = new Action(driver);
		PageFactory.initElements(driver, this);
	}

	

	public String verifyHomePageHeading() {

		return driver.findElement(homePageHeading).getText();
	
	}
	
	public String verifyLoginError() {
		
		return driver.findElement(invalidCredentialsMsg).getText();
	}

	public ProductPage clickProductLink() {
		
		action.waitForElement(productLink, 30);
		action.click(driver.findElement(productLink), "clicking product link");
		return new ProductPage(driver);
		
	}
	

}
