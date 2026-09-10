package org.epam;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Optional;

public class BasicTests extends BaseTest {

    @Test
    public void wifiSettingsTest() throws URISyntaxException, MalformedURLException {
        // Automation
        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();
        driver.findElement(By.id("android:id/checkbox")).click();
        driver.findElement(By.xpath("(//android.widget.RelativeLayout)[2]")).click();

        String alertTitle = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertEquals(alertTitle, "WiFi settings");

        driver.findElement(By.id("android:id/edit")).sendKeys("Menuet");
        driver.findElements(AppiumBy.className("android.widget.Button")).get(1).click();
    }

    @Test
    public void appiumActionsTest() throws InterruptedException {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Expandable Lists']")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();

        // Perform a long press
        // We get the element, and then we trigger a JS that performs the action
        WebElement namesElement = driver.findElement(By.xpath("//android.widget.TextView[@text='People Names']"));
        Optional<String> elementId = Optional.ofNullable(((RemoteWebElement) namesElement).getId());

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
        Thread.sleep(2000);

    }
}
