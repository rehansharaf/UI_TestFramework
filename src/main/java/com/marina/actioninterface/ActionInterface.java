package com.marina.actioninterface;

import java.text.ParseException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface ActionInterface {
	
	//Added all user actions abstract methods to achieve Abstraction  
	public void scrollByVisibilityOfElement(WebElement ele) throws InterruptedException;
	public boolean click(WebElement ele, String locatorName);
	public boolean findElement(WebElement ele);
	public boolean isDisplayed(WebElement ele);
	public boolean isSelected(WebElement ele);
	public boolean type(WebElement ele, String text);
	public boolean isEnabled(WebElement ele);
	public boolean selectBySendkeys(WebElement ele, String value);
	public boolean selectByIndex(WebElement ele, int index);
	public boolean selectByValue(WebElement ele,String value);
	public boolean selectByVisibleText(WebElement ele, String visibletext);
	public boolean selectByPartialVisibleText(By byLocator, String visibletext);
	public boolean mouseHoverByJavaScript(WebElement ele);
	public boolean JSClick(WebElement ele);
	public boolean switchToFrameByLocator(By byLocator);
	public boolean switchToFrameByIndex(int index);
	public boolean switchToFrameById(String idValue);
	
	public boolean switchToFrameByName(String nameValue);
	public boolean switchToDefaultFrame();
	public void mouseOverElement(WebElement ele);
	public boolean moveToElement(WebElement ele);
	public boolean mouseover(WebElement ele);
	public boolean draggable(WebElement eleSource, int x, int y);
	public boolean draganddrop(WebElement eleSource, WebElement eleTarget);
	public boolean slider(WebElement ele, int x, int y);
	
	public boolean rightclick(WebElement ele);
	public boolean switchWindowByTitle(String windowTitle, int count);
	public boolean switchToNewWindow();
	public boolean switchToOldWindow();
	public int getColumncount(WebElement eleRow);
	public int getRowCount(WebElement eleTable);
	public boolean Alert();
	
	public boolean launchUrl(String url);
	public boolean isAlertPresent();
	public String getAlertText();
	public String getTitle();

	public String getCurrentURL();
	public void pageLoadTimeOut(int timeOut);
	public String isFileDownloaded(String fileText, String fileExtension, int timeOut);
	public boolean isAttribtuePresent(WebElement ele, String attribute);

	public void waitForElement(By byLocator, int waitForSeconds);
	public void waitForElementByText(By byLocator, String expectedText, int waitForSeconds);
	
	
	public void implicitWait(int timeOut);
	public void explicitWaitPresenceOfElement(By byLocator, Duration timeOut);
	public void explicitWaitVisibility(WebElement ele, By byLocator, Duration timeOut);
	public void explicitWaitElementInvisibilityOf(WebElement ele, Duration timeOut);
	
	public void fluentWait(WebElement ele, int timeOut);
	public void explicitWaitElementClickable(WebElement ele, Duration timeOut);

}
