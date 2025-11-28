import io.qameta.allure.Allure;
import models.Order;
import org.testng.annotations.Test;
import testdata.TestAccount;

public class TC_07 extends BaseTest {
    @Test(
            description = "Verify user can place an order successfully"
    )
    public void tc_07() {
        Allure.step("Add any in-stock product to cart");
        homePage.addInStockProductsToCart(1);

        softAssert.assertTrue(cartModal.isCartModalVisible(), "Cart modal should be visible after adding product");
        softAssert.assertEquals(cartModal.getProductRowCount(), 1, "Should be 1 product in cart");
        softAssert.assertEquals(cartModal.getItemQty(0), 1, "Default quantity should be 1");

        Allure.step("Click 'Đặt hàng ngay'");
        cartModal.clickOrderNowBtn();

        softAssert.assertEquals(cartModal.getLoginLinkText(),"Vui lòng đăng nhập mới có thể đặt hàng Click vào đây để đăng nhập", "Login link should appear in cart modal after clicking order now if not logged in");

        Allure.step("Click the login link inside cart modal");
        cartModal.clickLoginTextLink();

        softAssert.assertTrue(loginModal.isLoginModalVisible(), "Login modal should be visible after clicking login link in cart modal");

        Allure.step("Login via login modal");
        loginModal.login(TestAccount.CUSTOMER_EMAIL,TestAccount.CUSTOMER_PASSWORD);

        softAssert.assertTrue(homePage.isLoggedIn(), "Login should succeed");
        softAssert.assertNotEquals(homePage.getAccountNameIfPresent(), "", "Account name should be shown on header");

        Allure.step("Reopen cart and click 'Đặt hàng ngay'");
        homePage.openCartModal();

        cartModal.clickOrderNowBtn();

        softAssert.assertTrue(checkoutModal.isCheckoutModalVisible(), "Checkout modal should be visible after clicking order now");

        long expectedTotal = checkoutModal.getOrderTotalAmount();

        Allure.step("Enter password and place order");
        checkoutModal.enterPassword(TestAccount.CUSTOMER_PASSWORD);

        checkoutModal.clickCheckoutButton();

        softAssert.assertEquals(orderConfirmationModal.getOrderSuccessMessage(),"Đặt hàng thành công", "Order success message should appear");

        Allure.step("Go to Admin Panel > Orders, verify the newly created order appears");
        orderConfirmationModal.closeOrderConfirmationModal();

        homePage.logout();

        homePage.openLoginModal();

        loginModal.login(TestAccount.ADMIN_EMAIL,TestAccount.ADMIN_PASSWORD);

        homePage.goToAdminControlPanel();
        
        adminDashboardPage.accessAdminOrderListPage();

        Order newlyCreatedOrder = adminOrderListPage.getLatestPendingOrderInfo();

        softAssert.assertEquals(newlyCreatedOrder.getEmail(), TestAccount.CUSTOMER_EMAIL, "Most recent order customer email should match");
        softAssert.assertEquals(newlyCreatedOrder.getTotalAmount(), expectedTotal, "Most recent order total in admin should match the order total");

        softAssert.assertAll();
    }
}
