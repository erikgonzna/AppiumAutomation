package org.epam;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Optional;

// For all the actions you can see https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md
public class AppiumActions {
    public AndroidDriver driver;

    public AppiumActions(AndroidDriver driver) {
        this.driver = driver;
    }

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
