package com.test.apptest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppTestIos {

    private static long INSTALL_DURATION_IN_SECONDS = 120L;
    private AppiumDriver driver;
    private long explicitWaitTimeoutInSeconds = 10L;
    private String udid = "f63531d60e46804d84e88b9747b8d245321bf2a0";


    @Before
    public void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("bundleId", "com.apple.AppStore");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("deviceName", "iPhone 6s Plus");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("xcodeOrgId", "KPY2W2D6J7");
        capabilities.setCapability("xcodeSigningId", "iPhone Developer");
        capabilities.setCapability("startIWDP", true);
        capabilities.setCapability("noReset", true);


        driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }

    @Test
    public void AppStoreTest() throws Exception {

        // Search button at bottom menu
        driver.findElement(By.id("Search")).click();

        // Search text field
        driver.findElement(By.className("XCUIElementTypeSearchField"))
                .sendKeys("Scatter Slots");

        // driver.hideKeyboard() enclosed within try/catch
        driver.hideKeyboard();

        // Find the parent element of the desired app
        MobileElement parent = (MobileElement) driver.findElement(By.id("Pineapple Pen, Ketchapp"));

        // Try to find and click the GET and then INSTALL buttons under the parent
        try {
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            parent.findElement(By.id("GET")).click();
            parent.findElement(By.id("INSTALL")).click();
        } catch (NoSuchElementException e) {
            // If GET button wasn't found, the app has been purchased before and there's a Download button instead
            parent.findElement(By.id("Download")).click();
        }

        // Increase implicit wait time for the download
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);

        // Open the app from within App Store
        driver.findElement(By.id("OPEN")).click();

        // Put implicit wait back to our default value
        driver.manage().timeouts().implicitlyWait(explicitWaitTimeoutInSeconds, TimeUnit.SECONDS);

        // Allow notifications pop-up
        driver.findElement(By.id("Allow")).click();
        //takeScreenshot("PPAP");
    }

    /*@After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }*/

}
