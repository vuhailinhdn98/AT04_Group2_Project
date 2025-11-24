import models.Product;
import org.testng.annotations.Test;

public class TC_01 extends BaseTest {

    @Test(description = "Verify admin can add a new product and it appears in the featured products on homepage")
    public void tc_01() {
        log.info("1.  Go to Admin page");
        homePage.openLoginModal();
        loginModal.login(ADMIN_EMAIL, ADMIN_PASSWORD);
        homePage.goToAdminControlPanel();

        log.info("2. Click on Products menu, then click Add Products");

        adminDashboardPage.accessAdminProductListPage();
        adminProductListPage.accessAdminAddProductPage();

        log.info("3. Fill in product information and click SAVE button");
        addProductPage.addProduct(productDataTest);

        log.info("4. Go to storefront");
        homePage.openHomePage();

        log.info("5. Verify product displays in product list");
        Product featuredProduct = homePage.getFirstFeaturedProduct();

        softAssert.assertEquals(
                featuredProduct.getName().trim().toLowerCase(),
                productDataTest.getName().trim().toLowerCase(),
                "Name product does not match created product."
        );
        softAssert.assertEquals(
                featuredProduct.getPrice(),
                productDataTest.getPrice(),
                "Price product does not match created product."
        );

        softAssert.assertAll();
    }
}