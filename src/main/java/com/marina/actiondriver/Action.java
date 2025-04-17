/**
 * 
 */
package com.marina.actiondriver;

import static org.testng.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.marina.actioninterface.ActionInterface;
import com.marina.base.TestBase;

/**
 * @author Rehan Sharaf 
 *
 */
public class Action extends TestBase implements ActionInterface {
	
	WebDriver driver;
	
	public Action(WebDriver driver) {
		
		this.driver = driver;
	}
	

	@Override
	public void scrollByVisibilityOfElement(WebElement ele) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", ele);
		Thread.sleep(1000);
	}

	@Override
	public boolean click(WebElement ele, String locatorName) {

		boolean flag = false;
		String errorMessage = null;
		try {
			ele.click();
			flag = true;
			return true;
		} catch (Exception e) {
			errorMessage = e.getMessage();
			return false;
		} finally {
			if (flag) {
				System.out.println("Able to click on \""+locatorName+"\"");
			} else {
				System.out.println("Click Unable to click on \""+locatorName+"\""+" Error: "+errorMessage);
			}
		}

	}

	@Override
	public boolean findElement(WebElement ele) {
		boolean flag = false;
		try {
			ele.isDisplayed();
			flag = true;
		} catch (Exception e) {
			// System.out.println("Location not found: "+locatorName);
			flag = false;
		} finally {
			if (flag) {
				System.out.println("Successfully Found element at");

			} else {
				System.out.println("Unable to locate element at");
			}
		}
		return flag;
	}

	@Override
	public boolean isDisplayed(WebElement ele) {
		boolean flag = false;
		flag = findElement(ele);
		if (flag) {
			flag = ele.isDisplayed();
			if (flag) {
				System.out.println("The element is Displayed");
			} else {
				System.out.println("The element is not Displayed");
			}
		} else {
			System.out.println("Not displayed ");
		}
		return flag;
	}

	@Override
	public boolean isSelected(WebElement ele) {
		boolean flag = false;
		flag = findElement(ele);
		if (flag) {
			flag = ele.isSelected();
			if (flag) {
				System.out.println("The element is Selected");
			} else {
				System.out.println("The element is not Selected");
			}
		} else {
			System.out.println("Not selected ");
		}
		return flag;
	}
	
	@Override
	public boolean type(WebElement ele, String text) {
		
		String errorMessage = null;
		boolean flag = false;
		try {
			flag = ele.isDisplayed();
			ele.clear();
			ele.sendKeys(text);
			// logger.info("Entered text :"+text);
			flag = true;
		} catch (Exception e) {
			System.out.println("Location Not found");
			errorMessage = e.getMessage();
			flag = false;
		} finally {
			if (flag) {
				System.out.println("Successfully entered value");
			} else {
				System.out.println("Unable to enter value :"+errorMessage);
			}

		}
		return flag;
	}

	@Override
	public boolean isEnabled(WebElement ele) {
		boolean flag = false;
		flag = findElement(ele);
		if (flag) {
			flag = ele.isEnabled();
			if (flag) {
				System.out.println("The element is Enabled");
			} else {
				System.out.println("The element is not Enabled");
			}
		} else {
			System.out.println("Not Enabled ");
		}
		return flag;
	}

	@Override
	public boolean selectBySendkeys(WebElement ele, String value) {
		boolean flag = false;
		try {
			ele.sendKeys(value);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("Select value from the DropDown");		
			} else {
				System.out.println("Not Selected value from the DropDown");
				// throw new ElementNotFoundException("", "", "")
			}
		}
	}


	@Override
	public boolean selectByIndex(WebElement ele, int index) {
		boolean flag = false;
		try {
			Select s = new Select(ele);
			s.selectByIndex(index);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				System.out.println("Option selected by Index");
			} else {
				System.out.println("Option not selected by Index");
			}
		}
	}


	@Override
	public boolean selectByValue(WebElement ele,String value) {
		boolean flag = false;
		try {
			Select s = new Select(ele);
			s.selectByValue(value);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("Option selected by Value");
			} else {
				System.out.println("Option not selected by Value");
			}
		}
	}


	@Override
	public boolean selectByVisibleText(WebElement ele, String visibletext) {
		boolean flag = false;
		try {
			Select s = new Select(ele);
			s.selectByVisibleText(visibletext);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				System.out.println("Option selected by VisibleText");
			} else {
				System.out.println("Option not selected by VisibleText");
			}
		}
	}

	@Override
	public boolean selectByPartialVisibleText(By byLocator, String visibletext) {
		
		boolean flag = false;
		try {
		
			List<WebElement> options = driver.findElements(byLocator);
			for (WebElement option : options) {
			    if (option.getText().contains(visibletext)) {
			        option.click();
			        flag = true;
			        break;
			    }
			}
			return true;
		}catch(Exception e) {
			return false;
		}finally {
			if (flag) {
				System.out.println("Option selected by Partial VisibleText");
			} else {
				System.out.println("Option not selected by Partial VisibleText");
			}
		}
	
	}
	
	@Override
	public boolean mouseHoverByJavaScript(WebElement ele) {
		boolean flag = false;
		try {
			WebElement mo = ele;
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, mo);
			flag = true;
			return true;
		}

		catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("MouseOver Action is performed");
			} else {
				System.out.println("MouseOver Action is not performed");
			}
		}
	}

	@Override
	public boolean JSClick(WebElement ele) {
		boolean flag = false;
		try {
			// WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele);
			// driver.executeAsyncScript("arguments[0].click();", element);

			flag = true;

		}

		catch (Exception e) {
			throw e;

		} finally {
			if (flag) {
				System.out.println("Click Action is performed");
			} else if (!flag) {
				System.out.println("Click Action is not performed");
			}
		}
		return flag;
	}
	
	@Override
	public boolean switchToFrameByLocator(By byLocator) {
		

		boolean flag = false;
		try {
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			driver.switchTo().frame(driver.findElement(byLocator));
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("Frame with locator \"" + byLocator + "\" is selected");
			} else {
				System.out.println("Frame with locator \"" + byLocator + "\" is not selected");
			}
		}
	
	}

	@Override
	public boolean switchToFrameByIndex(int index) {
		boolean flag = false;
		try {
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe")));
			driver.switchTo().frame(index);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("Frame with index \"" + index + "\" is selected");
			} else {
				System.out.println("Frame with index \"" + index + "\" is not selected");
			}
		}
	}

	
	@Override
	public boolean switchToFrameById(String idValue) {
		boolean flag = false;
		try {
			driver.switchTo().frame(idValue);
			flag = true;
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		} finally {
			if (flag) {
				System.out.println("Frame with Id \"" + idValue + "\" is selected");
			} else {
				System.out.println("Frame with Id \"" + idValue + "\" is not selected");
			}
		}
	}


	@Override
	public boolean switchToFrameByName(String nameValue) {
		boolean flag = false;
		try {
			driver.switchTo().frame(nameValue);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("Frame with Name \"" + nameValue + "\" is selected");
			} else if (!flag) {
				System.out.println("Frame with Name \"" + nameValue + "\" is not selected");
			}
		}
	}

	@Override
	public boolean switchToDefaultFrame() {
		boolean flag = false;
		try {
			driver.switchTo().defaultContent();
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (flag) {
				// SuccessReport("SelectFrame ","Frame with Name is selected");
			} else if (!flag) {
				// failureReport("SelectFrame ","The Frame is not selected");
			}
		}
	}

	@Override
	public void mouseOverElement(WebElement ele) {
		boolean flag = false;
		try {
			new Actions(driver).moveToElement(ele).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				System.out.println(" MouserOver Action is performed on ");
			} else {
				System.out.println("MouseOver action is not performed on");
			}
		}
	}

	@Override
	public boolean moveToElement(WebElement ele) {
		boolean flag = false;
		try {
			// WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", ele);
			Actions actions = new Actions(driver);
			// actions.moveToElement(driver.findElement(locator)).build().perform();
			actions.moveToElement(ele).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean mouseover(WebElement ele) {
		try {
			new Actions(driver).moveToElement(ele).build().perform();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			/*
			 * if (flag) {
			 * SuccessReport("MouseOver ","MouserOver Action is performed on \""+locatorName
			 * +"\""); } else {
			 * failureReport("MouseOver","MouseOver action is not performed on \""
			 * +locatorName+"\""); }
			 */
		}
	}
	@Override
	public boolean draggable(WebElement eleSource, int x, int y) {
		boolean flag = false;
		try {
			new Actions(driver).dragAndDropBy(eleSource, x, y).build().perform();
			Thread.sleep(5000);
			flag = true;
			return true;

		} catch (Exception e) {
		
			return false;
			
		} finally {
			if (flag) {
				System.out.println("Draggable Action is performed on \""+eleSource+"\"");			
			} else if(!flag) {
				System.out.println("Draggable action is not performed on \""+eleSource+"\"");
			}
		}
	}
	@Override
	public boolean draganddrop(WebElement eleSource, WebElement eleTarget) {
		boolean flag = false;
		try {
			new Actions(driver).dragAndDrop(eleSource, eleTarget).perform();
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("DragAndDrop Action is performed");
			} else if(!flag) {
				System.out.println("DragAndDrop Action is not performed");
			}
		}
	}
	
	@Override
	public boolean slider(WebElement ele, int x, int y) {
		boolean flag = false;
		try {
			// new Actions(driver).dragAndDropBy(dragitem, 400, 1).build()
			// .perform();
			new Actions(driver).dragAndDropBy(ele, x, y).build().perform();// 150,0
			Thread.sleep(5000);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("Slider Action is performed");
			} else {
				System.out.println("Slider Action is not performed");
			}
		}
	}
	
	@Override
	public boolean rightclick(WebElement ele) {
		boolean flag = false;
		try {
			Actions clicker = new Actions(driver);
			clicker.contextClick(ele).perform();
			flag = true;
			return true;
			// driver.findElement(by1).sendKeys(Keys.DOWN);
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("RightClick Action is performed");
			} else {
				System.out.println("RightClick Action is not performed");
			}
		}
	}
	
	@Override
	public boolean switchWindowByTitle(String windowTitle, int count) {
		boolean flag = false;
		try {
			Set<String> windowList = driver.getWindowHandles();

			String[] array = windowList.toArray(new String[0]);

			driver.switchTo().window(array[count-1]);

			if (driver.getTitle().contains(windowTitle)){
				flag = true;
			}else{
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			//flag = true;
			return false;
		} finally {
			if (flag) {
				System.out.println("Navigated to the window with title");
			} else {
				System.out.println("The Window with title is not Selected");
			}
		}
	}
	@Override
	public boolean switchToNewWindow() {
		boolean flag = false;
		try {

			Set<String> s=driver.getWindowHandles();
			Object popup[]=s.toArray();
			driver.switchTo().window(popup[1].toString());
			flag = true;
			return flag;
		} catch (Exception e) {
			flag = false;
			return flag;
		} finally {
			if (flag) {
				System.out.println("Window is Navigated with title");				
			} else {
				System.out.println("The Window with title: is not Selected");
			}
		}
	}
	@Override
	public boolean switchToOldWindow() {
		boolean flag = false;
		try {

			Set<String> s=driver.getWindowHandles();
			Object popup[]=s.toArray();
			driver.switchTo().window(popup[0].toString());
			flag = true;
			return flag;
		} catch (Exception e) {
			flag = false;
			return flag;
		} finally {
			if (flag) {
				System.out.println("Focus navigated to the window with title");			
			} else {
				System.out.println("The Window with title: is not Selected");
			}
		}
	}
	@Override
	public int getColumncount(WebElement ele) {
		List<WebElement> columns = ele.findElements(By.tagName("td"));
		int a = columns.size();
		System.out.println(columns.size());
		for (WebElement column : columns) {
			System.out.print(column.getText());
			System.out.print("|");
		}
		return a;
	}
	
	@Override
	public int getRowCount(WebElement eleTable) {
		List<WebElement> rows = eleTable.findElements(By.tagName("tr"));
		int a = rows.size() - 1;
		return a;
	}
	
	
	@Override
	public boolean Alert() {
		boolean presentFlag = false;
		Alert alert = null;

		try {
			// Check the presence of alert
			alert = driver.switchTo().alert();
			// if present consume the alert
			alert.accept();
			presentFlag = true;
		} catch (NoAlertPresentException ex) {
			// Alert present; set the flag

			// Alert not present
			ex.printStackTrace();
		} finally {
			if (!presentFlag) {
				System.out.println("The Alert is handled successfully");		
			} else{
				System.out.println("There was no alert to handle");
			}
		}

		return presentFlag;
	}
	@Override
	public boolean launchUrl(String url) {
		boolean flag = false;
		try {
			driver.navigate().to(url);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				System.out.println("Successfully launched \""+url+"\"");				
			} else {
				System.out.println("Failed to launch \""+url+"\"");
			}
		}
	}
	
	@Override
	public boolean isAlertPresent() 
	{ 
		try 
		{ 
			driver.switchTo().alert(); 
			return true; 
		}   // try 
		catch (NoAlertPresentException Ex) 
		{ 
			return false; 
		}   // catch 
	}
	
	@Override
	public String getAlertText() 
	{
		String presentText = "";
		Alert alert = null;

		try {
			// Check the presence of alert
			alert = driver.switchTo().alert();
			// if present consume the alert
			presentText = alert.getText();
			alert.accept();
			
		} catch (NoAlertPresentException ex) {
			// Alert present; set the flag

			// Alert not present
			ex.printStackTrace();
		} 

		return presentText;
	}
	
	@Override
	public String getTitle() {
		boolean flag = false;

		String text = driver.getTitle();
		if (flag) {
			System.out.println("Title of the page is: \""+text+"\"");
		}
		return text;
	}
	
	@Override
	public String getCurrentURL()  {
		boolean flag = false;

		String text = driver.getCurrentUrl();
		if (flag) {
			System.out.println("Current URL is: \""+text+"\"");
		}
		return text;
	}
	
	
	@Override
	public void fluentWait(WebElement ele, int timeOut) {
	    Wait<WebDriver> wait = null;
	    try {
	        wait = new FluentWait<WebDriver>((WebDriver) driver)
	        		.withTimeout(Duration.ofSeconds(20))
	        	    .pollingEvery(Duration.ofSeconds(2))
	        	    .ignoring(Exception.class);
	        wait.until(ExpectedConditions.visibilityOf(ele));
	        ele.click();
	    }catch(Exception e) {
	    }
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void pageLoadTimeOut(int timeOut) {
		driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
	}
	
	
	
	@Override
	 public String isFileDownloaded(String fileText, String fileExtension, int timeOut) {
        String folderName = System.getProperty("user.dir")+"\\src\\test\\resources\\downloadeddata\\";
        File[] listOfFiles;
        int waitTillSeconds = timeOut;
        boolean fileDownloaded = false;
        String filePath = null; 

	        long waitTillTime = Instant.now().getEpochSecond() + waitTillSeconds;
	        while (Instant.now().getEpochSecond() < waitTillTime) {
	            listOfFiles = new File(folderName).listFiles();
	            for (File file : listOfFiles) {
	                String fileName = file.getName().toLowerCase();
	                fileText = fileText.toLowerCase();
	                if (!fileName.contains(".tmp")) {
	                	if(!fileName.contains(".part")) {
	                		if(!fileName.contains(".crdownload")) {
	                			if(fileName.contains(fileText) && fileName.contains(fileExtension)) {
	                			
	                				fileDownloaded = true;
		                			filePath = file.getAbsolutePath();
		                			break;
	                			}
	                			
	                		}
	                	}
	                }
	            }
	            if (fileDownloaded) {
	                break;
	            }
	        }
	        return filePath;
	}

	

	
	
	@Override
	public boolean isAttribtuePresent(WebElement element, String attribute) {
	    Boolean result = false;
	    try {
	        String value = element.getAttribute(attribute);
	        if (value != null){
	            result = true;
	        }
	    } catch (Exception e) {}

	    return result;
	}
	
	@Override
	public void waitForElement(By byLocator, int duration) {
		
	  	  for (int second = 0;; second++) {
	  		     if (second >= duration) fail("timeout");
	  		     try { 
	  		    	 Thread.sleep(1000);
	  		    	 driver.findElement(byLocator);
	  		    	 //System.out.println("g1 is "+g1);
	  		    	 //v1 = v1.trim();
	  		    	 //g1 = g1.trim();
	  		    	 //System.out.println("Actual Text is "+g1);
	  		    	 //System.out.println("Expected Text is "+v1);
	  		    	 //if (v1.equals(g1))
	  		    	 break;	 
	  		    	 
	  		     } 
	  		     catch (Exception e){
	  		    	 System.out.println("Exception is "+e.getMessage());
	  		     }
	  		     try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
	  	  	
	  	  }
	}
	
	@Override
	public void waitForElementByText(By byLocator, String expectedText, int duration) {
		
	  	  for (int second = 0;; second++) {
	  		     if (second >= duration) fail("timeout");
	  		     try { 
	  		    	 Thread.sleep(1000);
	  		    	 String actualText = driver.findElement(byLocator).getText();
	  		    	 //System.out.println("g1 is "+g1);
	  		    	 actualText = actualText.trim();
	  		    	 expectedText = expectedText.trim();
	  		    	 System.out.println("Expected Text is "+expectedText);
	  		    	 System.out.println("Actual Text is "+actualText);
	  		    	 if (actualText.equals(expectedText))
	  		    		 break;	 
	  		    	 
	  		     } 
	  		     catch (Exception e){
	  		    	 System.out.println("Exception is "+e.getMessage());
	  		     }
	  		     try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
	  	  	
	  	  }
	}
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void implicitWait(int timeOut) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	

	@Override
	public void explicitWaitPresenceOfElement(By byLocator, Duration timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,timeOut);
		//wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));
	}
	
	@Override
	public void explicitWaitElementClickable(WebElement ele, Duration timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,timeOut);
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
	
	@Override
	public void explicitWaitVisibility(WebElement ele, By locator ,Duration timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,timeOut);
		
		int cond = 0;
		while(cond == 0) {
		
			try {
				
				wait.until(ExpectedConditions.presenceOfElementLocated(locator));
				wait.until(ExpectedConditions.visibilityOf(ele));
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				cond = 1;
				
			}catch(StaleElementReferenceException ste) {
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				wait.until(ExpectedConditions.presenceOfElementLocated(locator));
				wait.until(ExpectedConditions.visibilityOf(ele));
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				
			}
			
		}
		
		
		
	}

	@Override
	public void explicitWaitElementInvisibilityOf(WebElement ele, Duration timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,timeOut);
		wait.until(ExpectedConditions.invisibilityOf(ele));
		
	}



	
	

}
