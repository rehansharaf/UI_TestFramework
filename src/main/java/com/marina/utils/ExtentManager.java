package com.marina.utils;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
/**
 * ExtentManager class is used for Extent Report
 *  
 */
public class ExtentManager {
	
	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> extenttest = new ThreadLocal<>();
	
	public static void setExtent() {
		//htmlReporter= new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/ExtentReport/"+"MyReport_"+BaseClass.getCurrentTime()+".html");
		htmlReporter= new ExtentSparkReporter(System.getProperty("user.dir")+"/report/"+"MyReport.html");
		try {
			htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		htmlReporter.config().setDocumentTitle("Automation Test Report");
//		htmlReporter.config().setReportName("Medflow Test Automation Report");
//		htmlReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		 String executionType = Boolean.parseBoolean(System.getProperty("remote"))
		            ? "Remote"
		            : "Local";
		 
	
		extent.setSystemInfo("OS", System.getProperty("os.name")); // e.g., Windows 11
		extent.setSystemInfo("OS Version", System.getProperty("os.version")); // e.g., 10.0
		extent.setSystemInfo("Architecture", System.getProperty("os.arch")); // e.g., amd64
		extent.setSystemInfo("Java Version", System.getProperty("java.version")); // e.g., 17
		extent.setSystemInfo("Test Type", System.getProperty("testGroup")); // from -Denv or fallback
		extent.setSystemInfo("Execution Environment", executionType); // from -Denv or fallback
		
	}
	public static void endReport() {
		extent.flush();
	}
}
