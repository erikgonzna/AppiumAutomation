package org.epam;

import io.appium.java_client.AppiumBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicTests extends BaseTest {

    @Test
    public void wifiSettingsTest() {
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
    public void longPressTest() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Expandable Lists']")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();

        // Perform a long press
        // We get the element, and then we trigger a JS that performs the action
        WebElement namesElement = driver.findElement(By.xpath("//android.widget.TextView[@text='People Names']"));
        appiumActions.performLongPressAction(namesElement);

        WebElement menuElement = driver.findElement(By.id("android:id/title"));
        Assert.assertTrue(menuElement.isDisplayed());
        Assert.assertEquals(menuElement.getText(), "Sample menu");
    }

    @Test
    public void scrollTest() throws InterruptedException {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        appiumActions.performScroll("WebView", true);
        Thread.sleep(2000);
    }
}
