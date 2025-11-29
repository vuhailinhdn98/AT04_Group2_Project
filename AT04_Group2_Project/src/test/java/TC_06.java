import io.qameta.allure.Allure;
import org.testng.annotations.Test;
import testdata.TestAccount;

import java.util.List;

public class TC_06 extends BaseTest {

    @Test(
            description = "Verify checkout auto-fills saved contact/address for a logged-in user"
    )
    public void tc_06() {
        Allure.step("Log in with a customer account");
        homePage.openLoginModal();

        loginModal.login(TestAccount.CUSTOMER_EMAIL,TestAccount.CUSTOMER_PASSWORD);

        softAssert.assertTrue(homePage.isLoggedIn(), "Login should succeed");
        softAssert.assertNotEquals(homePage.getAccountNameIfPresent(),"", "Account name should be shown on header");

        Allure.step("Get saved contact/address info from user profile");
        homePage.openUserInfo();

        List<String> expectedUserInfo = userPage.getUserInfo();

        Allure.step("Add any in-stock product to cart");
        homePage.openHomePage();

        homePage.addInStockProductsToCart(1);

        softAssert.assertTrue(cartModal.isCartModalVisible(),"Cart modal is not shown");
        softAssert.assertEquals(cartModal.getProductRowCount(),1,"Should be 1 product row.");
        softAssert.assertEquals(cartModal.getItemQty(0),1,"Default product qty should be 1.");

        Allure.step("Click 'Đặt hàng ngay'");
        cartModal.clickOrderNowBtn();

        softAssert.assertTrue(checkoutModal.isCheckoutModalVisible(),"Checkout modal is not shown");

        Allure.step("Compare all contact/address fields to the saved profile");
        List<String> actualUserInfo = checkoutModal.getUserInfo();

        checkoutModal.closeCheckoutModal();

        softAssert.assertEquals(actualUserInfo, expectedUserInfo, "Contact/Address info in checkout modal does not match the saved profile.");

        softAssert.assertAll();
    }
}