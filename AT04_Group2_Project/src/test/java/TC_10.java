import models.Order;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC_10 extends BaseTest {
    @BeforeMethod
    public void tc_10_precondition() {
        homePage.openLoginModal();
        loginModal.login(ADMIN_EMAIL, ADMIN_PASSWORD);

        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminProductListPage();

        adminProductListPage.accessAdminAddProductPage();

        productDataTest = createProductData();

        addProductPage.addProduct(productDataTest);
    }
    @Test(description = "TC10: Verify canceling the order restores stock and re-enables purchase on the store")
    public void tc_10() {
        homePage.openHomePage();

        homePage.openFirstFeaturedProductDetails();

        productDetailsPage.clickAddToCart();

        int originalQuality = productDataTest.getQuality();

        String originalName = productDataTest.getName();

        cartModal.selectQuantityForFirstProduct(originalQuality);

        cartModal.clickOrderNowBtn();

        checkoutModal.waitCheckoutModalVisible();

        checkoutModal.enterPassword(ADMIN_PASSWORD);

        checkoutModal.clickCheckoutButton();

        homePage.openHomePage();

        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminOrderListPage();

        Order beforeCompletedOrder = adminOrderListPage.getLatestPendingOrderInfo();

        adminOrderListPage.sortByStatusPending();

        adminOrderListPage.cancelOrder(beforeCompletedOrder.getOrderId());

        adminDashboardPage.accessAdminProductListPage();

        adminProductListPage.openProductDetailsByName(originalName);

        int stockAfter = adminProductsDetailsPage.getStock();

        softAssert.assertEquals(stockAfter, originalQuality,
                String.format("Product quality remain original quality value: %d when admin cancel order",
                        originalQuality));
        softAssert.assertAll();
    }
}