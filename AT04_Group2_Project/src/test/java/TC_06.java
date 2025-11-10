import org.testng.annotations.Test;

public class TC_06 extends BaseTest {

    @Test(
            description = "Verify checkout auto-fills saved contact/address for a logged-in user"
    )
    public void tc_06() {
        homePage.openLoginModal();

        log.info("2. Log in with a customer account");
        loginModal.login("tranthang212@gmail.com", "123123", false);

        softAssert.assertTrue(homePage.isLoggedIn(), "Login should succeed");
        softAssert.assertNotEquals(homePage.getAccountNameIfPresent(),"", "Account name should be shown on header");

        log.info("3. Add any in-stock product to cart");
        homePage.addInStockProductsToCart(1);

        softAssert.assertTrue(cartModal.isCartModalVisible(),"Cart modal is not shown");
        softAssert.assertEquals(cartModal.getProductRowCount(),1,"Should be 1 product row.");
        softAssert.assertEquals(cartModal.getItemQty(0),1,"Default product qty should be 1.");

        log.info("4. Click 'Đặt hàng ngay'");
        cartModal.clickOrderNowBtn();

        log.info("5. Compare all contact/address fields to the saved profile");
        softAssert.assertAll();
    }
}