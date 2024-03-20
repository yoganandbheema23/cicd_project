package demo;

import java.io.File;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;



public class democlass {

	static WebDriver driver = null;
	static String parent_windowId = null;

	static String landingPageProductName = null;

	static String offerPageProductName = null;
	
	@Test
	public void greencart_Product() {
		System.setProperty("webdriver.chrome.driver","./mydrivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		// browser open
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement search_Item = driver.findElement(By.xpath("//input[@type=\"search\"]"));
		search_Item.sendKeys("Tom");

		parent_windowId = driver.getWindowHandle();
		System.out.println(parent_windowId);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// 1 kg - tomato
		WebElement display_product = driver.findElement(By.xpath("//h4[contains(text(),'Tomato - 1 Kg')]"));
		// Tomato
		// Tomato [0]--index pos
		// 1 Kg [1]
		offerPageProductName = display_product.getText().split("-")[0].trim();// Tomato

		WebElement top_deal = driver.findElement(By.xpath("//a[contains(text(),'Top Deals')]"));
		top_deal.click();

		// getWindows_code("Tom");
		getWindows_code();

		WebElement product_name = driver.findElement(By.xpath("//td[contains(text(),'Tomato')]"));
		landingPageProductName = product_name.getText();

		// getScreenshot();

		Assert.assertEquals(offerPageProductName, landingPageProductName);

	}

	public void getScreenshot() {
		try {
			try {

				// format setting the filename Sat_Apr_18_16-50-21_IST_2020
				String fileName = (new Date()).toString().replace(" ", "_").replace(":", "-").trim() + ".png";//
				// new Date() -->Tue Mar 08 10:16:06 IST 2022.png
				// Tue_Mar_08_10-16-06_IST_2022.png

				String destinationFilePath = System.getProperty("user.dir") + File.separator + "yoganand/" + fileName;

				System.out.println(destinationFilePath);
				try {
					// javaScriptExecutor jse=(javaScriptExecutor)driver;
					TakesScreenshot ts = (TakesScreenshot) driver;
					File source = ts.getScreenshotAs(OutputType.FILE); // dynamic screenshots //WIndows+Shift+S
					File destination = new File(destinationFilePath);
					FileUtils.copyFile(source, destination);
				} catch (Exception e) {

					System.out.println("Exception while taking screenshot " + e.getMessage());
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// driver.close();

		} finally {

		}
	}

	public static void getWindows_code() {
		Set<String> windows = driver.getWindowHandles();

		Iterator<String> itr = windows.iterator();
		while (itr.hasNext()) {
			String childWindowId = itr.next();// local variable

			if (!parent_windowId.equalsIgnoreCase(childWindowId)) {
				driver.switchTo().window(childWindowId);
				WebElement search_element = driver.findElement(By.xpath("//input[@id=\"search-field\"]"));
				search_element.sendKeys("Tom");
			}
		}
	}

}
