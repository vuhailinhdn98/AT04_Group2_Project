import org.testng.annotations.Test;

public class TC_11 extends BaseTest {

    @Test(description = "TC11: Verify Admin restocks the product and storefront becomes buyable")
    public void tc_11() {
        homePage.openLoginModal();
        loginModal.login(ADMIN_EMAIL, ADMIN_PASSWORD);

        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminProductListPage();
        adminProductListPage.accessAdminAddProductPage();
        productDataTest.setQuality(0);
        addProductPage.addProduct(productDataTest);
        String productName = productDataTest.getName();
        adminDashboardPage.accessAdminProductListPage();
        adminProductListPage.openProductDetailsByName(productName);
        productDataTest.setQualityRandom();
        adminProductsDetailsPage.updateProduct(productDataTest);
//bổ sung alllure report
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