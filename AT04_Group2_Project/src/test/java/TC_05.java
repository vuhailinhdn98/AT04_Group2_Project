import io.qameta.allure.Allure;
import org.testng.annotations.Test;

public class TC_05 extends BaseTest {
    @Test(
            description = "Verify user can add a product to cart from product details page"
    )
    public void tc_05() {
        Allure.step("Go to the details page of an in-stock product");
        homePage.openFirstInStockProductDetails();

        Allure.step("Click 'Thêm vào giỏ hàng' button");
        productDetailsPage.clickAddToCart();

        softAssert.assertTrue(cartModal.isCartModalVisible(),"Cart modal is not shown");
        softAssert.assertEquals(cartModal.getProductRowCount(),1,"Should be 1 product row.");
        softAssert.assertEquals(cartModal.getItemQty(0),1,"Default product qty should be 1.");

        softAssert.assertAll();
    }
}
