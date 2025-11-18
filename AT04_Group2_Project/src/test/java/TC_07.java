// java
import models.Order;
import org.testng.annotations.Test;

public class TC_07 extends BaseTest {
    @Test(
            description = "Verify user can place an order successfully"
    )
    public void tc_07() {
        log.info("2. Add any in-stock product to cart");
        homePage.addInStockProductsToCart(1);

        softAssert.assertTrue(cartModal.isCartModalVisible(), "Cart modal should be visible after adding product");
        softAssert.assertEquals(cartModal.getProductRowCount(), 1, "Should be 1 product in cart");
        softAssert.assertEquals(cartModal.getItemQty(0), 1, "Default quantity should be 1");

        log.info("3. Click 'Đặt hàng ngay'");
        cartModal.clickOrderNowBtn();

        softAssert.assertEquals(cartModal.getLoginLinkText(),"Vui lòng đăng nhập mới có thể đặt hàng Click vào đây để đăng nhập", "Login link should appear in cart modal after clicking order now if not logged in");
        //change method name

        log.info("4. Click the login link inside cart modal");
        cartModal.clickLoginTextLink();
        softAssert.assertTrue(loginModal.isLoginModalVisible(), "Login modal should be visible after clicking login link in cart modal");

        log.info("5. Login via login modal");
        loginModal.login("tranthang212@gmail.com", "123123");

        softAssert.assertTrue(homePage.isLoggedIn(), "Login should succeed");
        softAssert.assertNotEquals(homePage.getAccountNameIfPresent(), "", "Account name should be shown on header");

        log.info("6. Reopen cart and click 'Đặt hàng ngay'");
        homePage.openCartModal();
        cartModal.clickOrderNowBtn();

        softAssert.assertTrue(checkoutModal.isCheckoutModalVisible(), "Checkout modal should be visible after clicking order now");

        long expectedTotal = checkoutModal.getOrderTotalAmount();

        log.info("7. Fill contact/address if not available, enter password and place order");
        checkoutModal.enterPassword("123123");
        checkoutModal.clickCheckoutButton();

        softAssert.assertEquals(orderConfirmationModal.getOrderSuccessMessage(),"Đặt hàng thành công", "Order success message should appear");

        log.info("8. Go to Admin Panel > Orders, verify the newly created order appears");
        orderConfirmationModal.closeOrderConfirmationModal();
        homePage.goToAdminControlPanel();
        adminDashboardPage.accessAdminOrderListPage();

        Order newlyCreatedOrder = adminOrderListPage.getMostRecentOrderInfo();

        softAssert.assertEquals(newlyCreatedOrder.getEmail(), "tranthang212@gmail.com", "Most recent order customer email should match");
        softAssert.assertEquals(newlyCreatedOrder.getTotalAmount(), expectedTotal, "Most recent order total in admin should match the order total");

        softAssert.assertAll();
    }
}
