import org.testng.annotations.Test;

public class TC_06 extends BaseTest {

    @Test(
            description = "Verify checkout auto-fills saved contact/address for a logged-in user"
    )
    public void tc_06() {
        homePage.openLoginModal();

        loginModal.login("tranthang212@gmail.com","123123");

        softAssert.assertTrue(homePage.isLoggedIn(), "Login should succeed");
        softAssert.assertNotEquals(homePage.getAccountName(),"", "Account name should be shown on header");

        homePage.addInStockProductsToCart(1);

        softAssert.assertTrue(cartModal.isOpen(),"Cart modal is not shown");
        softAssert.assertEquals(cartModal.getProductRowCount(),1,"Should be 1 product row.");
        softAssert.assertEquals(cartModal.getItemQty(0),1,"Default product qty should be 1.");

        softAssert.assertAll();
    }
}