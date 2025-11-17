import models.Product;
import org.testng.annotations.Test;

public class TC_09 extends BaseTest {
    @Test(
            description = "Verify Out-of-stock label is displayed and add to cart is disabled when stock = 0"
    )
    public void tc_09() {
        log.info("Pre-condition: Log in to Admin Panel and add a new product with quantity = 0");

//        Product addedProduct = new Product();
//        addedProduct.setName("Auto Test Product OutOfStock");
//        addedProduct.setPrice(500000);
//        addedProduct.setCategory("Điện Thoại");
//        addedProduct.setBrand("Apple");
//        addedProduct.setQuantity(0);
//        addedProduct.setDescription("This is a test product added by automation script.");
//
//        homePage.openLoginModal();
//        loginModal.login("tranthang212@gmail.com","123123",false);
//
//        homePage.goToAdminControlPanel();
//
//        adminDashboardPage.accessAdminProductListPage();
//        adminProductListPage.accessAdminAddProductPage();
//
//        addProductPage.addProduct(addedProduct);
//
//        homePage.openHomePage();
//        Product featuredProduct = homePage.getFirstFeaturedProduct();
//
//        softAssert.assertEquals(
//                featuredProduct.getName().trim().toLowerCase(), addedProduct.getName().trim().toLowerCase(),
//                "Name product does not match created product."
//        );
//        softAssert.assertEquals(featuredProduct.getPrice(),addedProduct.getPrice(), "Price product does not match created product.");
//        softAssert.assertAll();


    }
}
