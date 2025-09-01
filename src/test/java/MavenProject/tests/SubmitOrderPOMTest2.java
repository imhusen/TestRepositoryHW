package MavenProject.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import MavenProject.AbstractComponents.OrderPage;
import MavenProject.TestComponents.BaseTest;
import MavenProject.pageobjects.CartPage;
import MavenProject.pageobjects.CheckoutPage;
import MavenProject.pageobjects.ConfirmationPage;
import MavenProject.pageobjects.LandingPage;
import MavenProject.pageobjects.ProductCatelogue;
import io.github.bonigarcia.wdm.WebDriverManager;

//Data Provider - HashMap - If the number of parameters are in large in number

public class SubmitOrderPOMTest2 extends BaseTest {

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException {

		ProductCatelogue productCatelogue = landingPage.loginApplication(input.get("email"), input.get("password"));

		List<WebElement> products = productCatelogue.getProductList();
		productCatelogue.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatelogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();

		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	@Test(dependsOnMethods = { "submitOrder" })

	public void OrderHistoryTest() {
		String productName = "BANARSI SAREE";
		ProductCatelogue productCatelogue = landingPage.loginApplication("husenmiyawaghare2212@test.com", "Husen123");
		OrderPage ordersPage = productCatelogue.goToOrderPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));

	}

	@DataProvider
	public Object[][] getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "husenmiyawaghare2212@test.com");
		map.put("password", "Husen123");
		map.put("product", "BANARSI SAREE");

		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "shetty@gmail.com");
		map1.put("password", "Iamking@000");
		map1.put("product", "IPHONE 13 PRO");

		return new Object[][] { { map }, { map1 } };

	}
}
