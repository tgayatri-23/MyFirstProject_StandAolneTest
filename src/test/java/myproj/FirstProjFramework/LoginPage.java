package myproj.FirstProjFramework;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utils.ExcelFirst;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginPage {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

	}

	@Test(dataProvider = "Authentication")
	public void loginTest(String username, String password) {
		driver.get("https://rahulshettyacademy.com/client");

		WebElement txtEmail = driver.findElement(By.id("userEmail"));
		txtEmail.clear();
		txtEmail.sendKeys(username);

		WebElement txtPwd = driver.findElement(By.id("userPassword"));
		txtPwd.clear();
		txtPwd.sendKeys(password);

		driver.findElement(By.id("login")).click();

		String exp_title = "Let's Shop";
		String act_title = driver.getTitle();
		Assert.assertEquals(act_title, exp_title, "Page title after login is not as expected");

	}

	@DataProvider(name = "Authentication")
	public String[][] getData() throws IOException {

		String path = ".\\ExcelFiles\\Credentials.xlsx";
		// access XLUtility file
		ExcelFirst excelFirst = new ExcelFirst(path);
		int totalrows = excelFirst.getRowCount("Sheet1");
		int totalcols = excelFirst.getCellCount("Sheet1", 1);

		String loginData[][] = new String[totalrows][totalcols];

		for (int r = 1; r <= totalrows; r++) // 1
		{
			for (int c = 0; c < totalcols; c++) // 0
			{
				loginData[r - 1][c] = excelFirst.getCellData("Sheet1", r, c);
			}
		}
		return loginData;

	}

	@AfterClass
	void tearDown() {
		driver.close();

	}
}