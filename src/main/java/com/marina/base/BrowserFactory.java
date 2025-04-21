package com.marina.base;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static ThreadLocal<String> browser = new ThreadLocal<>();
    public static BrowserFactory instance;

    private BrowserFactory() {}

    public static synchronized BrowserFactory getInstance() {
        if (instance == null)
            instance = new BrowserFactory();
        return instance;
    }

    public void setDriver(String browserName) {
        setDriver(browserName, false, false, null, 2);
        browser.set(browserName);
    }

    public void setDriver(String browserName, boolean headless, boolean useRemote, String remoteUrl, int maxRetries) {
        Exception lastException = null;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                String downloadDir = System.getProperty("user.dir") + "/src/test/resources/downloadeddata/";

                if (browserName.equalsIgnoreCase("Chrome")) {
                    WebDriverManager.chromedriver().setup();

                    ChromeOptions chromeOptions = new ChromeOptions();

                    if (headless) {
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--disable-gpu");
                        chromeOptions.addArguments("--window-size=1920,1080");
                    }

                    HashMap<String, Object> prefs = new HashMap<>();
                    prefs.put("plugins.always_open_pdf_externally", true);
                    prefs.put("download.prompt_for_download", false);
                    prefs.put("download.default_directory", downloadDir);
                    chromeOptions.setExperimentalOption("prefs", prefs);

                    if (useRemote && remoteUrl != null) {
                        driver.set(new RemoteWebDriver(new URL(remoteUrl), chromeOptions));
                    } else {
                        driver.set(new ChromeDriver(chromeOptions));
                    }
                    browser.set(browserName);
                    return;

                } else if (browserName.equalsIgnoreCase("Firefox")) {
                    WebDriverManager.firefoxdriver().setup();

                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setPreference("pdfjs.disabled", true);
                    profile.setPreference("browser.download.folderList", 2);
                    profile.setPreference("browser.download.dir", downloadDir);
                    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
                    profile.setPreference("browser.download.manager.showWhenStarting", false);

                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setProfile(profile);

                    if (headless) {
                        firefoxOptions.addArguments("--headless");
                        firefoxOptions.addArguments("--width=1920");
                        firefoxOptions.addArguments("--height=1080");
                    }

                    if (useRemote && remoteUrl != null) {
                        driver.set(new RemoteWebDriver(new URL(remoteUrl), firefoxOptions));
                    } else {
                        driver.set(new FirefoxDriver(firefoxOptions));
                    }
                    browser.set(browserName);
                    return;

                } else if (browserName.equalsIgnoreCase("Edge")) {
                    WebDriverManager.edgedriver().setup();

                    EdgeOptions edgeOptions = new EdgeOptions();

                    if (headless) {
                        edgeOptions.addArguments("--headless=new");
                        edgeOptions.addArguments("--disable-gpu");
                        edgeOptions.addArguments("--window-size=1920,1080");
                    }

                    HashMap<String, Object> edgePrefs = new HashMap<>();
                    edgePrefs.put("plugins.always_open_pdf_externally", true);
                    edgePrefs.put("download.prompt_for_download", false);
                    edgePrefs.put("download.default_directory", downloadDir);
                    edgeOptions.setExperimentalOption("prefs", edgePrefs);

                    if (useRemote && remoteUrl != null) {
                        driver.set(new RemoteWebDriver(new URL(remoteUrl), edgeOptions));
                    } else {
                        driver.set(new EdgeDriver(edgeOptions));
                    }
                    browser.set(browserName);
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
