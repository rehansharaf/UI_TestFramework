package com.marina.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utilities {
	
	
	public synchronized String screenShot(WebDriver driver, String filename) {
		
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/screenshots/" + filename + "_" + dateName + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		
		// This new path for jenkins
//		String newImageString = "http://localhost:8082/job/MyStoreProject/ws/MyStoreProject/ScreenShots/" + filename + "_"
//				+ dateName + ".png";
		
		String base64Img = null;
		try {
			base64Img = convertImgToBase64(destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return  base64Img;
	}
	
	public static String convertImgToBase64(String imgPath) throws IOException {
		
		byte[] file = FileUtils.readFileToByteArray(new File(imgPath));
        String base64Img = new String(Base64.encodeBase64String(file));
		return base64Img;
	}
	
	
	
	public static double roundValue(double value, int places) {
		
	    if (places < 0) throw new IllegalArgumentException();
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static long calculateDaysDiff(String startDate, String endDate, String dateFormat) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
	    Date firstDate = sdf.parse(startDate);
	    Date secondDate = sdf.parse(endDate);

	    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    return diff;
	}
	
	
	public static String getMonth(int month) {
		
	    return new DateFormatSymbols().getMonths()[month-1];
	}
	
	
	public static String getCurrentDate(int todayDate, int nextDate, int daysToSkip, String dateFormat) {
		
	      if(todayDate == 1) {
	    	
	    	  LocalDate dateObj = LocalDate.now();
		  	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		  	  return dateObj.format(formatter);
		  	  
	      }else {
	
	    	  Date dt = new Date();
			  Calendar c = Calendar.getInstance(); 
			  c.setTime(dt); 
			  c.add(Calendar.DATE, daysToSkip);
			  dt = c.getTime();
			  return new SimpleDateFormat(dateFormat).format(dt);

	      }
	  	  
      	
	}
	
	public String getCurrentTime() {
		
		String currentDate = new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date());
		return currentDate;
	}

	
	public static String dateConvertion(String dateNeedToConvert, String srcFormat, String targetFormat) throws ParseException {
		
		SimpleDateFormat originalFormat = new SimpleDateFormat(srcFormat);
		SimpleDateFormat destFormat = new SimpleDateFormat(targetFormat);
		Date date = originalFormat.parse(dateNeedToConvert);
		return destFormat.format(date);
	}
	

}
