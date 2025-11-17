import models.Product;
import org.testng.annotations.Test;

public class TC_01 extends BaseTest {

    @Test(
            description = "Verify admin can add a new product and it appears in the featured products on homepage"
    )
    public void tc_01() {
        Product featuredProduct = homePage.getFirstFeaturedProduct();

        homePage.openLoginModal();
        loginModal.login("tranthang212@gmail.com","123123");
        homePage.goToAdminControlPanel();
        adminDashboardPage.accessAdminProductListPage();
        adminProductListPage.accessAdminAddProductPage();
        addProductPage.addProduct(productDataTest);
        homePage.openHomePage();
        softAssert.assertEquals(
                featuredProduct.getName().trim().toLowerCase(), productDataTest.getName().trim().toLowerCase(),
                "Name product does not match created product."
        );
        softAssert.assertEquals(featuredProduct.getPrice(),productDataTest.getPrice(), "Price product does not match created product.");
        softAssert.assertAll();
    }
}
