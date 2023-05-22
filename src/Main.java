
import base.Util;
import com.google.common.collect.ImmutableList;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DriverManager;
import java.time.Duration;

public class Main {


    private static DriverManager AppDriver;
    static AppiumDriver driver;


    public static void main(String[] args) throws MalformedURLException, InterruptedException {


        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:deviceName", "emulator-5554");
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("appium:appPackage", "eu.kanade.tachiyomi.debug");
        desiredCapabilities.setCapability("appium:appActivity", "eu.kanade.tachiyomi.ui.main.MainActivity");
        desiredCapabilities.setCapability("appium:noReset", true);
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);



        //Test
//        By manga = By.xpath("//hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.view.View/android.view.View/android.view.View");
        By startButton = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[3]/android.widget.Button");
        By manga = By.id("eu.kanade.tachiyomi.debug:id/viewer_container");

        driver.findElement(By.xpath("//hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.view.View/android.view.View/android.view.View")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(startButton));
        driver.findElement(By.xpath("//hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[3]/android.widget.Button")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(manga));

        scroll(ScrollDirection.LEFT, 0.75);
        Thread.sleep(1000);
        scroll(ScrollDirection.LEFT, 0.75);
        Thread.sleep(1000);
        scroll(ScrollDirection.RIGHT, 0.75);
        Thread.sleep(1000);

//        Util.scroll(Util.ScrollDirection.RIGHT, 0.5);

        //EndTest

            driver.quit();


    }


    public enum ScrollDirection{
        UP, DOWN, LEFT, RIGHT
    }
public static void scroll(ScrollDirection dir, double scrollRatio){

Duration SCROLL_DUR = Duration.ofMillis(300);
        if(scrollRatio < 0 || scrollRatio > 1){
            throw new Error("Scroll distance must be be between 0 and 1");
        }

       org.openqa.selenium.Dimension size = Main.driver.manage().window().getSize();
    org.openqa.selenium.Point midPoint = new org.openqa.selenium.Point((int)(size.width * 0.5), (int)(size.height *0.5));

    // Scrolling up and down
    int bottom = midPoint.y + (int)(midPoint.y * scrollRatio);
    int top = midPoint.y - (int)(midPoint.y * scrollRatio);
//    Point Start = new Point(midPoint.x, top);
//    Point End = new Point(midPoint.x, top);

    // Scrolling left and right
    int left = midPoint.x - (int)(midPoint.x * scrollRatio);
    int right = midPoint.x + (int)(midPoint.x * scrollRatio);

    if (dir == ScrollDirection.UP) {
        swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
    } else if (dir == ScrollDirection.DOWN) {
        swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
    } else if (dir == ScrollDirection.LEFT) {
        swipe(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DUR);
    } else {
        swipe(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DUR);
    }

//    Point Start = new Point(right, midPoint.y);
//    Point End = new Point(left, midPoint.y);
}



    protected static void swipe(Point start, Point end, Duration duration) {
//        boolean isAndroid = AppDriver.getDriver() instanceof AndroidDriver;

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
//        if (isAndroid) {
//            duration = duration.dividedBy(ANDROID_SCROLL_DIVISOR);
//        } else {
//            swipe.addAction(new Pause(input, duration));
//            duration = Duration.ZERO;
//        }
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        ((AppiumDriver) Main.driver).perform(ImmutableList.of(swipe));
    }
}


