package com.marina.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.marina.actiondriver.Action;

public class LoginPage {
	
	WebDriver driver;
	Action action;
	
	By email = By.xpath("//input[@data-qa='login-email']");
	By password = By.xpath("//input[@placeholder='Password']");
	By btnLogin = By.xpath("//button[normalize-space()='Login']");
	
	
	
	
	
	public LoginPage(WebDriver driver) {
	
		this.driver = driver;
		action = new Action(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	
	public HomePage login(String email, String pass) {
				
		action.type(driver.findElement(this.email), email);
		action.type(driver.findElement(password), pass);
		action.click(driver.findElement(btnLogin), "Login btn");
		return new HomePage(driver);
		
	}
	
	
	

	
}
