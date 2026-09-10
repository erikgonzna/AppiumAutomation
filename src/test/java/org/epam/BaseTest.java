package org.epam;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class BaseTest {
    public AndroidDriver driver;
    public AppiumDriverLocalService appiumServer;
    public AppiumActions appiumActions;

    @BeforeMethod
    public void setUp() throws URISyntaxException, MalformedURLException {
        // Create Server
        appiumServer = new AppiumServiceBuilder()
                .withAppiumJS(new File("/opt/homebrew/Cellar/appium/3.6.0/libexec/lib/node_modules/appium/build/lib/main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();

        appiumServer.start();

        // AndroidDriver
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 8 Pro");
        options.setApp("/Users/Erik_Padilla/Documents/workspace/mobilePractice/Udemy/AppiumAutomation/src/test/resources/ApiDemos-debug.apk");

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);

        //Add a wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Create the object for the actions
        appiumActions = new AppiumActions(driver);

    }

    @AfterMethod
    public void tearDown() {
        // Stop driver and server
        driver.quit();
        appiumServer.stop();
    }
}
