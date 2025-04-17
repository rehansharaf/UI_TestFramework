package com.marina.base;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static BrowserFactory instance;

    private BrowserFactory() {}

    public static synchronized BrowserFactory getInstance() {
        if (instance == null)
            instance = new BrowserFactory();
        return instance;
    }

    public void setDriver(String browserName) {
        setDriver(browserName, false, null, 2);
    }

    public void setDriver(String browserName, boolean useRemote, String remoteUrl, int maxRetries) {
        Exception lastException = null;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                if (browserName.equalsIgnoreCase("Chrome")) {

                    WebDriverManager.chromedriver().setup();

                    ChromeOptions chromeOptions = new ChromeOptions();
                    HashMap<String, Object> prefs = new HashMap<>();
                    prefs.put("plugins.always_open_pdf_externally", true);
                    prefs.put("download.prompt_for_download", false);
                    prefs.put("download.default_directory", System.getProperty("user.dir") + "/src/test/resources/downloadeddata/");
                    chromeOptions.setExperimentalOption("prefs", prefs);

                    if (useRemote && remoteUrl != null) {
                        driver.set(new RemoteWebDriver(new URL(remoteUrl), chromeOptions));
                    } else {
                        driver.set(new ChromeDriver(chromeOptions));
                    }
                    return;

                } else if (browserName.equalsIgnoreCase("FireFox")) {

                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();

                    if (useRemote && remoteUrl != null) {
                        driver.set(new RemoteWebDriver(new URL(remoteUrl), firefoxOptions));
                    } else {
                        driver.set(new FirefoxDriver(firefoxOptions));
                    }
                    return;

                } else if (browserName.equalsIgnoreCase("IE")) {

                    WebDriverManager.iedriver().setup();
                    driver.set(new InternetExplorerDriver());
                    return;

                } else {
                    throw new IllegalArgumentException("Unsupported browser: " + browserName);
                }

            } catch (Exception e) {
                lastException = e;
                System.err.println("Attempt " + attempt + " to start " + browserName + " failed.");
                e.printStackTrace();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignored) {}
            }
        }

        throw new RuntimeException("Failed to initialize " + browserName + " after " + maxRetries + " attempts", lastException);
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void removeDriver() {
        try {
            getDriver().quit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.remove();
        }
    }
}
