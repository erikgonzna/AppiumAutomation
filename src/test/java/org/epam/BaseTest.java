package org.epam;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Optional;

public class BaseTest {
    public AndroidDriver driver;
    public AppiumDriverLocalService appiumServer;

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

    }

    @AfterMethod
    public void tearDown() {
        // Stop driver and server
        driver.quit();
        appiumServer.stop();
    }

    // For all the actions you can see https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md

    public void performLongPressAction(WebElement element) {
        Optional<String> elementId = Optional.ofNullable(((RemoteWebElement) element).getId());
        // In order to perform the script we pass the gesture, and a map that contains the elementId and the duration.
        driver.executeScript(
                "mobile: longClickGesture",
                ImmutableMap.of(
                        "elementId",
                        elementId.orElse("Error"),
                        "duration",
                        2000
                )
        );
    }

    public void performScroll(String text, boolean scrollByText) {
        if (scrollByText) {
            //UIAutomator Google Actions (Scroll by locator)
            driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"))"));
        } else {
            //JS Executor (Scroll by coordinates)
            boolean canScrollMore;

            do {
                canScrollMore = (boolean) driver.executeScript(
                        "mobile: scrollGesture", ImmutableMap.builder()
                                .put("left", 500)
                                .put("top", 600)
                                .put("width", 600)
                                .put("height", 600)
                                .put("direction", "down")
                                .put("percent", 2.00)
                                .build()
                );
            } while (canScrollMore);


        }
    }

}
