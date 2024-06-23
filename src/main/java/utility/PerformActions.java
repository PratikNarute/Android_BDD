package utility;

import java.time.Duration;


import com.fasterxml.jackson.databind.JsonSerializable;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.util.Arrays;
import java.util.Collections;



public class PerformActions extends AppiumDriverSetup
{
	public static void swipeAction(int startX, int startY, int endX, int endY)
	{
		    TouchAction touchAction = new TouchAction(driver);
		    PointOption pointStart = PointOption.point(startX, startY);
		    PointOption pointEnd = PointOption.point(endX, endY);
		    WaitOptions waitOption = WaitOptions.waitOptions(Duration.ofMillis(1000));

		    touchAction.press(pointStart).waitAction(waitOption).moveTo(pointEnd).release().perform();
	}
	public static void longPress(int x, int y, AndroidDriver driver)
	{
		   TouchAction touchAction = new TouchAction(driver);
		   PointOption point = PointOption.point(x, y);
		   WaitOptions waitOption = WaitOptions.waitOptions(Duration.ofMillis(15000));

		   touchAction.longPress(point).waitAction(waitOption).release().perform();

	}
	public static void scrollAction(int startX, int startY, int endX, int endY, AndroidDriver driver) throws InterruptedException
	{
		Thread.sleep(1000);
		TouchAction touchAction = new TouchAction(driver);
	    PointOption pointStart = PointOption.point(startX, startY);
	    PointOption pointEnd = PointOption.point(endX, endY);
	    WaitOptions waitOption = WaitOptions.waitOptions(Duration.ofMillis(2000));

	    touchAction.press(pointStart).waitAction(waitOption).moveTo(pointEnd).release().perform();
	    Thread.sleep(1000);
	}


	public static void ENTER_keyEventAction (AndroidDriver driver)
	{
		KeyEvent enter = new KeyEvent(AndroidKey.ENTER);
		driver.pressKey(enter);
	}
	public static void BACK_keyEventAction (AndroidDriver driver)
	{
		KeyEvent enter = new KeyEvent(AndroidKey.BACK);
		driver.pressKey(enter);
	}
	public static void BACK_SPACE_keyEventAction (AndroidDriver driver)
	{
		KeyEvent enter = new KeyEvent(AndroidKey.DEL);
		driver.pressKey(enter);
	}

	public static void longPressByElement(WebElement element, AndroidDriver driver)
	{
		//find location of the web element
				Point location = element.getLocation();

				PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

				Sequence longpress = new Sequence(finger, 1);
				longpress.addAction(finger.createPointerMove(Duration.ofMillis(0),PointerInput.Origin.viewport(), location.x, location.y));
				longpress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
				longpress.addAction(finger.createPointerMove(Duration.ofMillis(1000),PointerInput.Origin.viewport(), location.x, location.y));
				longpress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

				//perform the Sequence of action
				driver.perform(Collections.singletonList(longpress));
	}
	public static void click_action(WebElement el) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(el));
			System.out.println("Click on element: " + el.getText().replace("\n", "").trim());
			el.click();
			System.out.println("=====================================Done============================================");
		} catch (StaleElementReferenceException e) {
			System.out.println("Found StaleElementReferenceException: " + el);
			driver.navigate().refresh(); Thread.sleep(4000);
			System.out.println("Click on element: " + el.getText().replace("\n", "").trim());
			el.click();
			System.out.println("=====================================Done============================================");
		} catch (TimeoutException e) {
			System.out.println("==============================Fail===============================================");
			Assert.fail("Found NoSuchElementException for: " + e.getMessage());
		}catch (ElementClickInterceptedException e){
			System.out.println("Found ElementClickInterceptedException: " + el);
			Thread.sleep(4000);
			System.out.println("Click on element: " + el.getText().replace("\n", "").trim());
			el.click();
			System.out.println("=====================================Done============================================");
		}
	}

	public static void  send_action(WebElement el, String data) throws InterruptedException {
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			wait.until(ExpectedConditions.visibilityOf(el));
//			System.out.println("Send action perform on: " + el.getAttribute("value"));
			System.out.println("Send action perform on: " + el.getAttribute("text"));
			el.sendKeys(data);
			System.out.println("=====================================Done============================================");
		} catch (TimeoutException e) {
			System.out.println("==============================Fail===============================================");
			Assert.fail("Found NoSuchElementException: " + e.getMessage());
		}catch (StaleElementReferenceException e){
			driver.navigate().refresh();Thread.sleep(4000);
			System.out.println("Send action perform on: " + el.getAttribute("text"));
			el.sendKeys(data);
			System.out.println("=====================================Done============================================");
		}catch (ElementNotInteractableException e){
			Thread.sleep(2000);
			System.out.println("Send action perform on: " + el.getAttribute("text"));
			el.sendKeys(data);
			System.out.println("=====================================Done============================================");
		}

	}

	public static void scrollToElement_Downward(WebElement targetElement) {
		int start_x =631;
		int start_Y =1500;
		int end_x =631;
		int end_Y =500;

		if(AppiumDriverSetup.getDeviceName().equalsIgnoreCase("Cloud Device")){
			try {
				for (int i = 0; i < 10; i++) {
					swipeAction(start_x, start_Y, end_x, end_Y);
					if (isElementDisplayed(targetElement)) {
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("Scroll to mobile element failed"+e.getMessage());
			}
		}else{
			try {
				for (int i = 0; i < 10; i++) {
					swipeDownwardBy_Coordinates();
					if (isElementDisplayed(targetElement)) {
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("Scroll to mobile element failed"+e.getMessage());
			}
		}
	}

	public static void scrollToElement_Upward(WebElement targetElement) {
		int start_x =631;
		int start_Y =500;
		int end_x =631;
		int end_Y =1500;

		if(AppiumDriverSetup.getDeviceName().equalsIgnoreCase("Cloud Device")){
			try {
				for (int i = 0; i < 10; i++) {
					swipeAction(start_x, start_Y, end_x, end_Y);
					if (isElementDisplayed(targetElement)) {
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("Scroll to mobile element failed"+e.getMessage());
			}
		}else{
			try {
				for (int i = 0; i < 10; i++) {
					swipeUpwardBy_Coordinates();
					if (isElementDisplayed(targetElement)) {
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("Scroll to mobile element failed"+e.getMessage());
			}
		}
	}

	public static boolean isElementDisplayed(WebElement element) throws InterruptedException {
		boolean elementDisplayed = false;
		try{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
			wait.until(ExpectedConditions.visibilityOf(element));
			elementDisplayed = element.isDisplayed();
			if (elementDisplayed){
				System.out.println("Element is present on page: " + element.getAttribute("text"));
			}
		}catch (TimeoutException e){
			System.out.println("Element is not present on page: " + e.getMessage());
		}
		System.out.println("=====================================Done============================================");
		return elementDisplayed;
	}

	public static boolean isElementEnabled(WebElement element) throws InterruptedException {
		boolean elementEnabled = false;
		try{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.visibilityOf(element));
			elementEnabled = element.isEnabled();
			if (elementEnabled){
				System.out.println("Element is enable to clickable: " + element.getText().replace("\n", "").trim());
			}
			else {
				System.out.println("Element is does not enable to clickable: " + element.getText().replace("\n", "").trim());
			}
		}catch (TimeoutException e){
			System.out.println("Element is not present on page: " + e.getMessage());
		}catch (StaleElementReferenceException e){
			driver.navigate().refresh();Thread.sleep(4000);
			elementEnabled = element.isEnabled();
			if (elementEnabled){
				System.out.println("Element is enable to clickable: " + element.getText().replace("\n", "").trim());
			}
			else {
				System.out.println("Element is does not enable to clickable: " + element.getText().replace("\n", "").trim());
			}
		}
		System.out.println("=====================================Done============================================");
		return elementEnabled;
	}

	public static void swipeDownwardBy_Coordinates(){
		PointerInput  finger= new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Point start = new Point(631, 1500);
		Point end = new Point (631, 500);
		Sequence swipe = new Sequence(finger, 1);
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(500),
				PointerInput.Origin.viewport(), start.getX(), start.getY()));
		swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
				PointerInput.Origin.viewport(), end.getX(), end.getY()));
		swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		driver.perform(Arrays.asList(swipe));
	}
	public static void swipeUpwardBy_Coordinates(){
		PointerInput  finger= new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Point start = new Point(631, 500);
		Point end = new Point (631, 1500);
		Sequence swipe = new Sequence(finger, 1);
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(500),
				PointerInput.Origin.viewport(), start.getX(), start.getY()));
		swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
				PointerInput.Origin.viewport(), end.getX(), end.getY()));
		swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		driver.perform(Arrays.asList(swipe));
	}



}
