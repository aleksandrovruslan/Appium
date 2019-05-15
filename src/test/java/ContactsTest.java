import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class ContactsTest {

    AppiumDriver<MobileElement> driver;
    WebDriverWait wait;

    String name = "Ivan";
    String secondName = "Ivanov";
    String mobileNumber = "+79111111111";

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Pixel_2_API_27");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("appPackage",
                "com.google.android.apps.nexuslauncher");
        capabilities.setCapability("appActivity",
                "com.google.android.apps.nexuslauncher.NexusLauncherActivity");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void setup() {
        driver.findElementByAccessibilityId("Phone").click();
        MobileElement el;
        try {
            el = driver.findElementById("com.google.android.dialer:id/empty_list_view_action");
        } catch (NoSuchElementException nsee) {
            el = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout" +
                    "/android.widget.FrameLayout/android.widget.FrameLayout" +
                    "/android.view.ViewGroup/android.widget.FrameLayout[1]" +
                    "/android.view.ViewGroup/android.widget.FrameLayout[2]" +
                    "/android.widget.FrameLayout/android.widget.FrameLayout" +
                    "/android.widget.LinearLayout/android.support.v4.view.ViewPager" +
                    "/android.widget.FrameLayout/android.support.v7.widget.RecyclerView" +
                    "/android.widget.LinearLayout/android.widget.TextView");
        }
        el.click();
        driver.findElementByXPath("/hierarchy/android.widget.FrameLayout" +
                "/android.widget.LinearLayout/android.widget.FrameLayout" +
                "/android.widget.FrameLayout/android.widget.FrameLayout" +
                "/android.widget.LinearLayout/android.widget.ScrollView" +
                "/android.widget.LinearLayout/android.widget.LinearLayout[2]" +
                "/android.widget.LinearLayout/android.widget.LinearLayout" +
                "/android.widget.LinearLayout/android.widget.LinearLayout" +
                "/android.widget.LinearLayout/android.widget.EditText[1]")
                .sendKeys(name);
        driver.findElementByXPath("/hierarchy/android.widget.FrameLayout" +
                "/android.widget.LinearLayout/android.widget.FrameLayout" +
                "/android.widget.FrameLayout/android.widget.FrameLayout" +
                "/android.widget.LinearLayout/android.widget.ScrollView" +
                "/android.widget.LinearLayout/android.widget.LinearLayout[2]" +
                "/android.widget.LinearLayout/android.widget.LinearLayout" +
                "/android.widget.LinearLayout/android.widget.LinearLayout" +
                "/android.widget.LinearLayout/android.widget.EditText[2]")
                .sendKeys(secondName);
        driver.hideKeyboard();
        driver.findElementByXPath("/hierarchy/android.widget.FrameLayout" +
                "/android.widget.LinearLayout/android.widget.FrameLayout" +
                "/android.widget.FrameLayout/android.widget.FrameLayout" +
                "/android.widget.LinearLayout/android.widget.ScrollView" +
                "/android.widget.LinearLayout/android.widget.LinearLayout[2]" +
                "/android.widget.LinearLayout[2]/android.widget.LinearLayout" +
                "/android.widget.LinearLayout/android.widget.LinearLayout" +
                "/android.widget.LinearLayout/android.widget.EditText")
                .sendKeys(mobileNumber);
        driver.findElementById("com.android.contacts:id/editor_menu_save_button").click();
        driver.navigate().back();
        driver.findElementById("com.google.android.dialer:id/search_magnifying_glass").click();
        MobileElement el8 = driver.findElementById("com.google.android.dialer:id/search_view");
        el8.click();
        el8.sendKeys(name + " " + secondName);
        driver.findElementByAccessibilityId("Quick contact for " +
                name + " " + secondName).click();
        MobileElement contactName = driver.findElementById(
                "com.android.contacts:id/large_title");

        assertEquals(contactName.getText(), name + " " + secondName);

        MobileElement options = driver.findElementByAccessibilityId("More options");
        options.click();
        MobileElement deleteButton = driver.findElementByXPath("/hierarchy" +
                "/android.widget.FrameLayout/android.widget.FrameLayout" +
                "/android.widget.ListView/android.widget.LinearLayout[2]" +
                "/android.widget.RelativeLayout/android.widget.TextView");
        deleteButton.click();
        MobileElement acceptDelete = driver.findElementById("android:id/button1");
        acceptDelete.click();

        driver.navigate().back();
        driver.navigate().back();
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

}
