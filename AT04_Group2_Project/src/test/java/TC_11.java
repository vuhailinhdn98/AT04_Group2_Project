import io.qameta.allure.Allure;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testdata.ProductDataTest;

import java.util.List;
import java.util.Map;

public class TC_11 extends BaseTest {
    @BeforeMethod
    public void tc_11_precondition() {
        Allure.step("Pre-condition: Log in to Admin Panel and add a new product with quantity = 0");
        homePage.openLoginModal();
        loginModal.login(ADMIN_EMAIL, ADMIN_PASSWORD);

        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminProductListPage();

        adminProductListPage.accessAdminAddProductPage();

        List<String> manufacturers = addProductPage.getAllManufacturers();
        productDataTest = new ProductDataTest(manufacturers);
        addProductPage.addProduct(productDataTest.setQuality(0));
    }

    @Test(description = "Verify Admin restocks the product and storefront becomes buyable")
    public void tc_11() {

        String productName = productDataTest.getName();

        adminDashboardPage.accessAdminProductListPage();

        adminProductListPage.openProductDetailsByName(productName);

        adminProductsDetailsPage.updateProduct(productDataTest.setQualityRandom());

        homePage.openHomePage();

        homePage.openFirstFeaturedProductDetails();

        String stockStatusAfter = productDetailsPage.getStockStatus();
        softAssert.assertEquals(stockStatusAfter, "còn hàng",
                "Product should show 'còn hàng' after restocking");
        softAssert.assertTrue(productDetailsPage.isAddToCartEnabled(),
                "Add to cart button should be enabled after restocking");

        productDetailsPage.clickAddToCart();
        softAssert.assertTrue(cartModal.isCartModalVisible(),
                "Cart modal should open successfully after clicking add to cart");

        softAssert.assertAll();
    }
}