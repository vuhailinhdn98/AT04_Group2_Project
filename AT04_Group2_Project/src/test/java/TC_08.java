import models.Product;
import org.testng.annotations.Test;
import pages.AdminOrderListPage;

import java.util.List;

public class TC_08 extends BaseTest {
    @Test(
            description = "Verify product inventory decreases by ordered quantity"
    )
    public void tc_08() {
        log.info("2. Log in and add any in-stock product to cart");
        loginModal.login("tranthang212@gmail.com", "123123", false);

        List<Product> added = homePage.addInStockProductsToCart(1);

        softAssert.assertTrue(cartModal.isCartModalVisible(), "Cart modal should open after adding product");
        softAssert.assertEquals(cartModal.getProductRowCount(), 1, "Should be 1 product row in cart");
        softAssert.assertEquals(cartModal.getItemQty(0), 1, "Default qty should be 1");

        log.info("3. Create an order");
        cartModal.clickOrderNowBtn();
        checkoutModal.proceedToCheckoutForLoggedInUser("123123");

        softAssert.assertEquals(orderConfirmationModal.getOrderSuccessMessage(), "Đặt hàng thành công", "Order should succeed");

        log.info("4. Go to Admin Panel > Orders and complete the newly created order");
        homePage.goToAdminControlPanel();
        adminMenu.clickOrdersMenu();

        softAssert.assertTrue(adminOrderList.hasOrderWithProduct(product.getName()), "Newly created order should appear in admin orders");

        // complete the most recent matching order
        adminOrderList.completeMostRecentOrderForProduct(product.getName());

        // completed order should disappear from active list
        softAssert.assertFalse(adminOrderList.hasOrderWithProduct(product.getName()), "Completed order should no longer appear in active orders");

        log.info("5. Go to Admin Panel > Products and check the product stock decreased to 1");
        adminMenu.clickProductsMenu();
        // AdminProductListPage is present in project; assume helper to get stock by product name
        int stock = adminProductList.getStockByProductName(product.getName());
        softAssert.assertEquals(stock, 1, "Product stock should be reduced to 1 after completing the order");

        softAssert.assertAll();
    }
}
