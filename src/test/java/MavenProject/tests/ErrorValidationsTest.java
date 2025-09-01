package MavenProject.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import MavenProject.TestComponents.BaseTest;
import MavenProject.TestComponents.Retry;
import MavenProject.pageobjects.CartPage;
import MavenProject.pageobjects.CheckoutPage;
import MavenProject.pageobjects.ConfirmationPage;
import MavenProject.pageobjects.ProductCatelogue;

public class ErrorValidationsTest extends BaseTest {

	@Test (retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException {

		String productName = "BANARSI SAREE";

		landingPage.loginApplication("husenmiyawaghare2212@test.com", "Husen1234");
		Assert.assertEquals("Incorrect email  password.", landingPage.getErrorMessage());

	}

	@Test
	public void ProductErrorValidation() throws IOException {

		String productName = "BANARSI SAREE";

		ProductCatelogue productCatelogue = landingPage.loginApplication("anshika@gmail.com", "Iamking@000");

		List<WebElement> products = productCatelogue.getProductList();
		productCatelogue.addProductToCart(productName);
		CartPage cartPage = productCatelogue.goToCartPage();
		// CartPage cartPage = new CartPage(driver);

		Boolean match = cartPage.VerifyProductDisplay("BANARSI SARI");
		Assert.assertFalse(match);

	}

}
