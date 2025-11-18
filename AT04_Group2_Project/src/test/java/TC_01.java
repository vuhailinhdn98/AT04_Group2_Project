import models.Product;
import org.testng.annotations.Test;

public class TC_01 extends BaseTest {

    @Test(description = "Verify admin can add a new product and it appears in the featured products on homepage")
    public void tc_01() {
        log.info("2. Login as admin");
        homePage.openLoginModal();
        loginModal.login("tranthang212@gmail.com", "123123");

        log.info("3. Navigate to Admin Product page");
        homePage.goToAdminControlPanel();
        adminDashboardPage.accessAdminProductListPage();
        adminProductListPage.accessAdminAddProductPage();

        log.info("4. Add new product: {}", productDataTest);
        addProductPage.addProduct(productDataTest);

        log.info("5. Go back to homepage");
        homePage.openHomePage();

        log.info("6. Get first featured product from homepage");
        Product featuredProduct = homePage.getFirstFeaturedProduct();  // ← DI CHUYỂN XUỐNG ĐÂY

        log.info("Featured product: Name='{}', Price={}",
                featuredProduct.getName(), featuredProduct.getPrice());
        log.info("Created product: Name='{}', Price={}",
                productDataTest.getName(), productDataTest.getPrice());

        log.info("7. Verify featured product matches created product");
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
        log.info("TC_01 PASSED");
    }
}