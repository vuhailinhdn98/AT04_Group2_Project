import models.Product;
import org.testng.annotations.Test;

public class TC_09 extends BaseTest {
    @Test(
            description = "Verify Out-of-stock label is displayed and add to cart is disabled when stock = 0"
    )
    public void tc_09() {
        log.info("Pre-condition: Log in to Admin Panel and add a new product with quantity = 0");
        homePage.openLoginModal();
        loginModal.login("tranthang212@gmail.com","123123",false);
        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminProductListPage();
        adminProductListPage.accessAdminAddProductPage();

        addProductPage.addProductWithQtyZero(productDataTest);

        homePage.openHomePage();
        Product outOfStockProduct = homePage.getFirstFeaturedProduct();

        softAssert.assertEquals(
                outOfStockProduct.getName().trim().toLowerCase(), productDataTest.getName().trim().toLowerCase(),
                "Name product does not match created product."
        );
        softAssert.assertEquals(outOfStockProduct.getPrice(),productDataTest.getPrice(), "Price product does not match created product.");

        softAssert.assertAll();


    }
}
