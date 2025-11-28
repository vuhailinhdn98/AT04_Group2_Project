import io.qameta.allure.Allure;
import models.Order;
import models.Product;
import org.testng.annotations.Test;
import testdata.TestAccount;

import java.util.List;

public class TC_08 extends BaseTest {
    @Test(
            description = "Verify product inventory decreases by ordered quantity"
    )
    public void tc_08() {
//        log.info("2. Log in and add any in-stock product to cart");
        Allure.step("2. Log in and add any in-stock product to cart");

        homePage.openLoginModal();

        loginModal.login(TestAccount.CUSTOMER_EMAIL,TestAccount.CUSTOMER_PASSWORD);

        List<Product> addedProducts = homePage.addInStockProductsToCart(1);

        softAssert.assertTrue(cartModal.isCartModalVisible(), "Cart modal should open after adding product");
        softAssert.assertEquals(cartModal.getProductRowCount(), 1, "Should be 1 product row in cart");
        softAssert.assertEquals(cartModal.getItemQty(0), 1, "Default qty should be 1");

        String firstAddedProduct = addedProducts.get(0).getName();
        long expectedTotal = cartModal.getCartTotalAmount();

//        log.info("3. Create an order");
        Allure.step("3. Create an order");
        cartModal.clickOrderNowBtn();

        checkoutModal.proceedToCheckoutForLoggedInUser(TestAccount.CUSTOMER_PASSWORD);

        softAssert.assertEquals(orderConfirmationModal.getOrderSuccessMessage(),"Đặt hàng thành công", "Order success message should appear");

        orderConfirmationModal.closeOrderConfirmationModal();

//        log.info("4. Go to Admin Panel > Orders, verify the newly created order appears");
        Allure.step("4. Go to Admin Panel > Orders, verify the newly created order appears");

        homePage.logout();

        homePage.openLoginModal();

        loginModal.login(TestAccount.ADMIN_EMAIL,TestAccount.ADMIN_PASSWORD);

        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminOrderListPage();

        Order beforeCompletedOrder = adminOrderListPage.getLatestPendingOrderInfo();

        softAssert.assertEquals(beforeCompletedOrder.getEmail(), TestAccount.CUSTOMER_EMAIL, "Most recent order customer email should match");
        softAssert.assertEquals(beforeCompletedOrder.getTotalAmount(), expectedTotal, "Most recent order total in admin should match the order total");

//        log.info("Capture stock of 1st product BEFORE complete and complete the newly created order");
        adminOrderListPage.accessAdminProductListPage();

        adminProductListPage.openProductDetailsByName(firstAddedProduct);

        int stockBefore = adminProductsDetailsPage.getStock();

        adminProductsDetailsPage.accessAdminOrderListPage();

        adminOrderListPage.sortByStatusPending();

        adminOrderListPage.completeOrder(beforeCompletedOrder.getOrderId());

        adminOrderListPage.sortByStatusPaid();

        Order afterCompletedOrder = adminOrderListPage.getOrderById(beforeCompletedOrder.getOrderId());

        softAssert.assertEquals(afterCompletedOrder.getOrderStatus(),"Đã thanh toán", "Completed order should be marked as 'Đã thanh toán'");

//        log.info("5. Go to Admin Panel > Products and check the product stock decreased to 1");
        Allure.step("5. Go to Admin Panel > Products and check the product stock decreased to 1");

        adminOrderListPage.accessAdminProductListPage();

        adminProductListPage.openProductDetailsByName(firstAddedProduct);

        int stockAfter = adminProductsDetailsPage.getStock();

        softAssert.assertEquals(stockAfter, stockBefore - 1, "Product stock should decrease by 1 after completing the order");

        softAssert.assertAll();
    }
}
