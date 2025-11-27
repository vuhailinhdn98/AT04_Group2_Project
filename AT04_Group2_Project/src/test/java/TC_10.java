import org.testng.annotations.Test;

public class TC_10 extends BaseTest {

    @Test(description = "TC10: Verify canceling the order restores stock and re-enables purchase on the store")
    public void tc_10() {
        homePage.openLoginModal();
        loginModal.login(ADMIN_EMAIL, ADMIN_PASSWORD);

        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminProductListPage();

        adminProductListPage.accessAdminAddProductPage();

        productDataTest = createProductData();

        addProductPage.addProduct(productDataTest);

        int originalQuality = productDataTest.getQuality();

        String originalName = productDataTest.getName();

        homePage.openHomePage();

        homePage.openFirstFeaturedProductDetails();

        productDetailsPage.clickAddToCart();

        cartModal.selectQuantityForFirstProduct(originalQuality);

        cartModal.clickOrderNowBtn();

        checkoutModal.waitCheckoutModalVisible();

        checkoutModal.enterPassword(ADMIN_PASSWORD);

        checkoutModal.clickCheckoutButton();

        homePage.openHomePage();

        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminOrderListPage();

        adminOrderListPage.cancelOrder("");

        adminDashboardPage.accessAdminProductListPage();

        adminProductListPage.openProductDetailsByName(originalName);

        int stockAfter = adminProductsDetailsPage.getStock();

        softAssert.assertEquals(stockAfter, originalQuality,
                String.format("Product quality remain original quality value: %d when admin cancel order",
                        originalQuality));
        softAssert.assertAll();
    }
}