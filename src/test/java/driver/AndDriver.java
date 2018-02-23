package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;

public class AndDriver {






    private DesiredCapabilities installedAppCaps() throws Exception {

        DesiredCapabilities installedAppCaps = new DesiredCapabilities();
        installedAppCaps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        installedAppCaps.setCapability(MobileCapabilityType.PLATFORM_VERSION, versionPlatform);
        installedAppCaps.setCapability(MobileCapabilityType.NO_RESET, true);
        installedAppCaps.setCapability(MobileCapabilityType.DEVICE_NAME, nameDevice);
        installedAppCaps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
        installedAppCaps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
        installedAppCaps.setCapability("autoLaunch", "false");

        return installedAppCaps;
    }

    public AppiumDriver getDriverSetup() throws IOException, InterruptedException {
        driver = new io.appium.java_client.android.AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), setup());
        this.wait = new WebDriverWait(driver, explicitWaitTimeoutInSeconds);
        return driver;
    }

    public AppiumDriver getDriverApp() throws Exception {
        driver = new io.appium.java_client.android.AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), installedAppCaps());
        this.wait = new WebDriverWait(driver, explicitWaitTimeoutInSeconds);
        return driver;
    }

}
