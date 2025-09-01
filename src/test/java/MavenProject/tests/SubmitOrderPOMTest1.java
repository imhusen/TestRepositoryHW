package MavenProject.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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


//Data Provider - Array
public class SubmitOrderPOMTest1 extends BaseTest {

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(String email, String password, String productName) throws IOException {

		ProductCatelogue productCatelogue = landingPage.loginApplication(email, password);

		List<WebElement> products = productCatelogue.getProductList();
		productCatelogue.addProductToCart(productName);
		CartPage cartPage = productCatelogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(productName);
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
		return new Object[][] { { "husenmiyawaghare2212@test.com", "Husen123", "BANARSI SAREE" },
				{ "shetty@gmail.com", "Iamking@000", "IPHONE 13 PRO" } };

	}
}
