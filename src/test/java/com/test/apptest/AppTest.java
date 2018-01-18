package com.test.apptest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class AppTest {

    private static long INSTALL_DURATION_IN_SECONDS = 120L;
    private AppiumDriver driver;
    MobileElement appTitle;
    private WebDriverWait wait;
    private long explicitWaitTimeoutInSeconds = 10L;

    @Before
    public void setup() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.android.vending");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.finsky.activities.MainActivity");
        desiredCapabilities.setCapability("deviceOrientation", "portrait");
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), desiredCapabilities);

        this.wait = new WebDriverWait(driver, explicitWaitTimeoutInSeconds);
    }

    @Test
    public void testGooglePlayApp() throws Exception {
        String testAppName = "Scatter Slots Murka";
        String checkName = "Игровые Автоматы Scatter Slots";
        String version = "3.17.1";


        // wait until search bar is visible, and then tap on it
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.android.vending:id/search_box_idle_text\")"))))
                .click();

        // type in the name of the app into the search bar
        driver.findElement(MobileBy.className("android.widget.EditText"))
                .sendKeys(testAppName);

        // tap on the suggested option that contains the app name
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.android.vending:id/suggest_text\").text(\"" + testAppName.toLowerCase() + "\")"))))
                .click();

        // tap for the app title to be displayed
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.android.vending:id/li_title\").text(\"" + checkName + "\")")))).click();

        // check version app
        driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.android.vending:id/callout\")"))
                .click();
        driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"Адрес эл. почты разработчика\"));"));


        try {
            driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.android.vending:id/extra_description\").text(\"" + version.trim() + "\")"));
        } catch (NoSuchElementException ex) {
            ex.getMessage();
            System.out.println("Version not found");
            driver.quit();
        }


        //закрыть доп инфо
        driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.ImageButton\")"))
                .click();

        // tap on the Install button
        driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.Button\").text(\"УСТАНОВИТЬ\")"))
                .click();

        // tap on accept
        try {
            MobileElement accept = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.android.vending:id/continue_button\")"));
            accept.click();
        } catch (NoSuchElementException pr) {
            pr.getMessage();
        }

        // wait until "installed" shows up for INSTALL_DURATION_IN_SECONDS
        new WebDriverWait(driver, INSTALL_DURATION_IN_SECONDS).until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.Button\").text(\"ОТКРЫТЬ\")")));

        // quit current driver instance - this quits the google playstore
        driver.quit();

        // launch newly installed app
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), installedAppCaps());
        driver.launchApp();

        //access granted
        MobileElement buttonGranted = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.Button\").text(\"РАЗРЕШИТЬ\")"));
        for (int i = 0; buttonGranted.isDisplayed(); i++) {
            buttonGranted.click();
        }

    }

    private DesiredCapabilities installedAppCaps() throws Exception {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.murka.scatterslots");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.murka.android.core.MurkaUnityActivity");
        desiredCapabilities.setCapability("autoLaunch", "false");

        return desiredCapabilities;
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
