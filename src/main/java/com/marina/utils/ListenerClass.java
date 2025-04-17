package com.marina.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.marina.base.BrowserFactory;



public class ListenerClass extends ExtentManager implements ITestListener {

	Utilities utils = new Utilities();
	
	public void onTestStart(ITestResult result) {

		extenttest.set(extent.createTest(result.getName()));

	}

	public void onTestSuccess(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			
			Log.info("====================================="+result.getName()+" TEST PASSED =========================================");
			//test.log(Status.PASS, "Pass Test case is: " + result.getName());
			extenttest.get().info(result.getMethod().getDescription());
			extenttest.get().log(Status.PASS, "Pass Test case is: " + result.getName());

			
			
		}
	}

	public void onTestFailure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			
			Log.error("====================================="+result.getName()+" TEST FAILED =========================================");
			
			extenttest.get().info(result.getMethod().getDescription());
			//test.log(Status.FAIL,MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			extenttest.get().log(Status.FAIL,MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));

			//test.log(Status.FAIL,MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
			extenttest.get().log(Status.FAIL,MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));

			String imgPath = utils.screenShot(BrowserFactory.getInstance().getDriver(),result.getName());

			//test.fail("ScreenShot is Attached", MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());
			//test.fail("ScreenShot is Attached", MediaEntityBuilder.createScreenCaptureFromBase64String(imgPath).build());
			extenttest.get().fail("ScreenShot is Attached", MediaEntityBuilder.createScreenCaptureFromBase64String(imgPath).build());

			
		}
	}

	public void onTestSkipped(ITestResult result) {
		if (result.getStatus() == ITestResult.SKIP) {
			
			Log.warn("====================================="+result.getName()+" TEST SKIPPED =========================================");
			//test.log(Status.SKIP, "Skipped Test case is: " + result.getName());
			extenttest.get().info(result.getMethod().getDescription());
			extenttest.get().log(Status.SKIP, "Skipped Test case is: " + result.getName());

		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}
}
