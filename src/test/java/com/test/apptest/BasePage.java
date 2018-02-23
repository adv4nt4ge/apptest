package com.test.apptest;


import com.company.automation.DefaultTestBrowserManager;
import com.company.automation.Utils.DataGeneration;
import com.company.automation.core.WebElementWait;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public abstract class BasePage {

    public static long INSTALL_DURATION_IN_SECONDS = 120L;
    private WebDriverWait wait;
    private long explicitWaitTimeoutInSeconds = 15L;
    private static String versionPlatform = "8.0";
    private static String nameDevice = "Nexus";
    private final String appPackage = "com.murka.scatterslots";
    private final String appActivity = "com.murka.android.core.MurkaUnityActivity";
    private static AppiumDriver driver;

    static {
        try {
            driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), mainSetup());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static AppiumDriver getDriverSetup() {
        return driver;
    }



    protected static WebElementWait wait = new WebElementWait();

    public BasePage() {
        init();
    }

    protected void init() {
        PageFactory.initElements(getDriver(), this);
    }


    private static DesiredCapabilities mainSetup() throws IOException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, versionPlatform);
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, nameDevice);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.android.vending");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.finsky.activities.MainActivity");
        desiredCapabilities.setCapability("deviceOrientation", "portrait");

        return desiredCapabilities;
    }

}
