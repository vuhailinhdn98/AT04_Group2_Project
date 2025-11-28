import io.qameta.allure.Allure;
import models.Product;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testdata.ProductDataTest;
import testdata.TestAccount;

import java.util.List;
import java.util.Map;

public class TC_09 extends BaseTest {
    @BeforeMethod
    public void tc_09_precondition() {
        Allure.step("Pre-condition: Log in to Admin Panel and add a new product with quantity = 0");
        homePage.openLoginModal();

        loginModal.login(TestAccount.ADMIN_EMAIL, TestAccount.ADMIN_PASSWORD);

        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminProductListPage();

        adminProductListPage.accessAdminAddProductPage();

        List<String> manufacturers = addProductPage.getAllManufacturers();
        productDataTest = new ProductDataTest(manufacturers);
        addProductPage.addProduct(productDataTest.setQuality(0));
    }

    @Test(
            description = "Verify Out-of-stock label is displayed and add to cart is disabled when stock = 0"
    )
    public void tc_09() {
        homePage.openHomePage();

        Product outOfStockProduct = homePage.getFirstFeaturedProduct();

        softAssert.assertEquals(
                outOfStockProduct.getName().trim(), productDataTest.getName().trim(),
                "Name product does not match created product."
        );
        softAssert.assertEquals(outOfStockProduct.getPrice(),productDataTest.getPrice(), "Price product does not match created product.");

        Allure.step("Verify disabled add to cart button for Out-of-stock product on homepage");

        softAssert.assertFalse(homePage.isAddToCartButtonEnabled(outOfStockProduct), "Add to cart button should be disabled for product with qty = 0");

        Allure.step("Go to the details page of the out-of-stock product");
        homePage.openProductDetailsByName(outOfStockProduct.getName());

        softAssert.assertEquals(productDetailsPage.getStockStatus(), "hết hàng", "Out-of-stock label is not displayed for product with qty = 0");
        softAssert.assertFalse(productDetailsPage.isAddToCartEnabled(), "Add to cart button should be disabled for product with qty = 0");

        softAssert.assertAll();
    }
}
