// java
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

        softAssert.assertEquals(cartModal.getNotLoginWarningMsg(),"Vui lòng đăng nhập mới có thể đặt hàng Click vào đây để đăng nhập", "Warning message should appear in cart modal for not logged-in user");

        log.info("4. Click the login link inside checkout warning");
        cartModal.clickLoginTextLink();
        softAssert.assertTrue(loginModal.isLoginModalVisible(), "Login modal should be visible after clicking login link in cart modal");

        log.info("5. Login via login modal");
        loginModal.login("tranthang212@gmail.com", "123123", false);

        softAssert.assertTrue(homePage.isLoggedIn(), "Login should succeed");
        softAssert.assertNotEquals(homePage.getAccountNameIfPresent(), "", "Account name should be shown on header");

        log.info("6. Reopen cart and proceed to checkout");
        homePage.openCartModal();
        cartModal.clickOrderNowBtn();

        softAssert.assertTrue(checkoutModal.isCheckoutModalVisible(), "Checkout modal should be visible after clicking order now");

        log.info("7. Fill contact/address if not available, enter password and place order");

        checkoutModal.enterPassword("123123");
        checkoutModal.clickCheckoutButton();

        softAssert.assertEquals(orderConfirmationModal.getOrderSuccessMessage(),"Đặt hàng thành công", "Order success message should appear");

        softAssert.assertAll();
    }
}
