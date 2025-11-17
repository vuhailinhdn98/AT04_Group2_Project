import models.Product;
import org.testng.annotations.Test;

public class TC_01 extends BaseTest {

    @Test(
            description = "Verify admin can add a new product and it appears in the featured products on homepage"
    )
    public void tc_01() {

        homePage.openLoginModal();
        loginModal.login("tranthang212@gmail.com","123123",false);

        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminProductListPage();
        adminProductListPage.accessAdminAddProductPage();

        addProductPage.addProduct(addedProduct);

        homePage.openHomePage();
        Product featuredProduct = homePage.getFirstFeaturedProduct();

        softAssert.assertEquals(
                featuredProduct.getName().trim().toLowerCase(), addedProduct.getName().trim().toLowerCase(),
                "Name product does not match created product."
        );
        softAssert.assertEquals(featuredProduct.getPrice(),addedProduct.getPrice(), "Price product does not match created product.");
        softAssert.assertAll();
    }
}
