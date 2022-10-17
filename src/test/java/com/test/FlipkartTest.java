package com.test;

import org.openqa.selenium.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FlipkartTest {
	WebDriver driver;
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver","D:/chrome driver/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.flipkart.com/");
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
		driver = null;
	}

	@Test(priority = 1) //closing the login pop up
	public void loginClose() throws InterruptedException {

		try {
			System.out.println("-----------Working in Chrome browser---------");
			System.out.println(driver.getTitle());
			driver.findElement(By.cssSelector("body > div._2Sn47c > div > div > button")).click();
			Thread.sleep(1000);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		screenshot(driver,"D://pics//Loginclosed");
	}

	@Test(priority = 2)//verify if it scrolls to the bottom of page and check if the scroll feature available
	public void scroll() throws InterruptedException {

		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		System.out.println("\nNavigated to bottom of the page");
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,-document.body.scrollHeight)", "");
		System.out.println("\nScroll Feature available");
		Thread.sleep(2000);
		screenshot(driver,"D://pics//scroll");
	}

	@Test(priority = 3) // search for the product and page load time
	public void searchProduct() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@class='_3704LK']")).sendKeys("iPhone 13");
		Thread.sleep(1000);
		WebElement searchbuton= driver.findElement(By.cssSelector("button[class='L0Z3Pu']"));
		searchbuton.click();

		Thread.sleep(3000);
		By load = By.cssSelector("#container > div > div._36fx1h._6t1WkM._3HqJxg > div._1YokD2._2GoDe3 > div:nth-child(2) > div:nth-child(9) > div > div");
		long start = System.currentTimeMillis();
		driver.findElement(load).click();
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		System.out.println("\nTime to load page in milliseconds: " + totalTime);
		screenshot(driver,"D://pics//searchproduct");
	}

	@Test(priority = 4)
	public void loadImage() throws InterruptedException {

		String url = "https://www.flipkart.com/apple-iphone-13-product-red-128-gb/p/itm99b5658d148b0?pid=MOBG6VF59ZFEPEBX&lid=LSTMOBG6VF59ZFEPEBX8XIKMW&marketplace=FLIPKART&q=iphone+13&store=tyy%2F4io&srno=s_1_5&otracker=search&otracker1=search&fm=Search&iid=ae6290fe-7cc3-401c-8d4e-8065a0732d56.MOBG6VF59ZFEPEBX.SEARCH&ppt=sp&ppn=sp&ssid=zhv58gshhs0000001666002567456&qH=c68a3b83214bb235";
		driver.get(url);
		Thread.sleep(3000);
		driver.navigate().refresh();

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class); 
		wait.until(new Function<WebDriver, WebElement>() {

			@Test
			public WebElement apply(WebDriver driver) {
				WebElement img = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[9]/div[4]/div[3]/div/div/div/div[1]/img"));
			
				if (img.isDisplayed()) {
					System.out.println("\nImage Loaded");
					return img;
				} else {
					System.out.println("\nElement is not loaded!");
					return null;
				}
			}
		});
		screenshot(driver,"D://pics//imgLoad");
	}

	@Test(priority = 5)
	public void scrollFrequency() throws InterruptedException {
		Thread.sleep(2000);
		long start = System.currentTimeMillis();
		WebElement element = driver.findElement(By.cssSelector("#container > div > div._2c7YLP.UtUXW0._6t1WkM._3HqJxg > div._1YokD2._2GoDe3 > div._1YokD2._3Mn1Gg.col-8-12 > div._1YokD2._3Mn1Gg > div:nth-child(7) > div > div:nth-child(3) > div > div > div:nth-child(1)"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		long stop = System.currentTimeMillis();
		long totalTime = stop - start;
		System.out.println("\nScroll Frequency in milliseconds: " + totalTime);
		screenshot(driver,"D://pics//scrollfrequency");

	}

	@Test(priority = 6)
	public void downloadImages() throws InterruptedException {
		WebElement img = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[9]/div[4]/div[3]/div/div/div/div[1]/img"));
		Boolean p = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete "+ "&& typeof arguments[0].naturalWidth != \"undefined\" " + "&& arguments[0].naturalWidth > 0", img);

		if (p) {
			System.out.println("\nImage is present");
		} else {
			System.out.println("\nImage is not present");
		}
		screenshot(driver,"D://pics//downloadImages");
	}

	@Test(priority = 7)
	public void screenResolution() throws InterruptedException {
		Thread.sleep(1000);
		driver.manage().window().setSize(new Dimension(760,1280));
		Thread.sleep(2000);

		
		driver.manage().window().setSize(new Dimension(1080, 1920));
		Thread.sleep(2000);

	
		driver.manage().window().setSize( new Dimension(1440, 2560));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		int contentHeight = ((Number) js.executeScript("return window.innerHeight")).intValue();
		int contentWidth = ((Number) js.executeScript("return window.innerWidth")).intValue();
		System.out.println("\nHeight: " + contentHeight + " Width: " + contentWidth + "\n");
		screenshot(driver,"D://pics//screenshotResolution");

	}
	@Test(priority = 8)
	  public void diffBrowEdge()
	  {
		  System.setProperty("webdriver.edge.driver", "C:/Users/user/Downloads/edgedriver_win64 (2)/msedgedriver.exe");
		  WebDriver edge= new EdgeDriver();
		  edge.get("https://www.flipkart.com");
		  System.out.println("----------Working in Edge browser----------");
		  Dimension d= edge.manage().window().getSize();
		  System.out.println( "height : "+d.getHeight() +"\n width : "+d.getWidth());
		  edge.manage().window().setSize(new Dimension(702, 613));
		  d= edge.manage().window().getSize();
		  System.out.println("After changing resoulution:");
		  System.out.println( "Height : "+d.getHeight() +"\n Width : "+d.getWidth());
	  }
	
	public static void screenshot(WebDriver driver,String screenshotName){
		  TakesScreenshot ts = (TakesScreenshot)driver;
		   File scr = ts.getScreenshotAs(OutputType.FILE);
		   try {
				FileUtils.copyFile(scr, new File(screenshotName+".png"));
				System.out.println("Screenshots taken");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	
	

}
