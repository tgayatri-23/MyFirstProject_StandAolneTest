package myproj.FirstProjFramework;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAlone {

	public static void main(String[] args) {
        
		String productName = "ZARA COAT 3";
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://rahulshettyacademy.com/client");

		driver.findElement(By.id("userEmail")).sendKeys("jack123@yahoo.com");
		driver.findElement(By.id("userPassword")).sendKeys("Gayatri@123");
		driver.findElement(By.id("login")).click();
		// declare global level this wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		// put a wait before product search, it's a smarter idead, sometimes page is
		// taking time to load
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		// select a common locator and grab all into a list
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

		// Product.findelement search only product level, if it's xpath //b elese if it
		// css selector b
		//if you want to get total weblement then use product.findelement
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst()
				.orElse(null);

		// parent to child traverse, use pord.findelement, search scope will be limited

		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		// loading icon and toast message locators, use explicitwait

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-container")));

		// if you developer will use this type of loading icon then ask him what is the
		// locator for it
		// ng-animating

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
			

		// now click on the cart button
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
        
		
		//parent to child traverse //if you want match condition then use .anyMatch()
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct->
		cartProduct.getText().equals(productName));
		
		Assert.assertTrue(match); //to validate productName
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("//button[contains(@class, 'ta-item')][2]")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		//selenium look code on the screen, notin the html that's why here i am taking screen's message
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.quit();
		
		
		
		
		
		
	}

}
