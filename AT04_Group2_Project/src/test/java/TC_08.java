import models.Order;
import models.Product;
import org.testng.annotations.Test;

import java.util.List;

public class TC_08 extends BaseTest {
    @Test(
            description = "Verify product inventory decreases by ordered quantity"
    )
    public void tc_08() {
        log.info("2. Log in and add any in-stock product to cart");
        homePage.openLoginModal();

        loginModal.login("tranthang212@gmail.com", "123123");

        List<Product> addedProducts = homePage.addInStockProductsToCart(1);

        softAssert.assertTrue(cartModal.isCartModalVisible(), "Cart modal should open after adding product");
        softAssert.assertEquals(cartModal.getProductRowCount(), 1, "Should be 1 product row in cart");
        softAssert.assertEquals(cartModal.getItemQty(0), 1, "Default qty should be 1");

        String firstAddedProduct = addedProducts.get(0).getName();
        long expectedTotal = cartModal.getCartTotalAmount();

        log.info("3. Create an order");
        cartModal.clickOrderNowBtn();

        checkoutModal.proceedToCheckoutForLoggedInUser("123123");

        softAssert.assertEquals(orderConfirmationModal.getOrderSuccessMessage(),"Đặt hàng thành công", "Order success message should appear");

        orderConfirmationModal.closeOrderConfirmationModal();
        // open customer acc
        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminOrderListPage();

        Order beforeCompletedOrder = adminOrderListPage.getMostRecentOrderInfo();

        softAssert.assertEquals(beforeCompletedOrder.getEmail(), "tranthang212@gmail.com", "Most recent order customer email should match");
        softAssert.assertEquals(beforeCompletedOrder.getTotalAmount(), expectedTotal, "Most recent order total in admin should match the order total");

        log.info("4. Go to Admin Panel > Orders, capture stock of 1st product BEFORE complete and complete the newly created order");
        adminOrderListPage.accessAdminProductListPage();

        adminProductListPage.openProductDetailsByName(firstAddedProduct);

        int stockBefore = adminProductsDetailsPage.getStock();

        adminProductsDetailsPage.accessAdminOrderListPage();

        adminOrderListPage.completeMostRecentOrder();

        List<Order> latestPaidOrderList = adminOrderListPage.getLatestPaidOrderList();
        Order afterCompletedOrder = adminOrderListPage.findOrderById(latestPaidOrderList, beforeCompletedOrder.getOrderId());

        softAssert.assertEquals(afterCompletedOrder.getOrderStatus(),"Đã thanh toán", "Most recent order should be marked as completed");

        log.info("5. Go to Admin Panel > Products and check the product stock decreased to 1");
        adminOrderListPage.accessAdminProductListPage();

        adminProductListPage.openProductDetailsByName(firstAddedProduct);

        int stockAfter = adminProductsDetailsPage.getStock();

        log.info("Stock of product '{}' before completing the order: {}", firstAddedProduct, stockBefore);
        log.info("Stock of product '{}' after completing the order: {}", firstAddedProduct, stockAfter);
        softAssert.assertEquals(stockAfter, stockBefore - 1, "Product stock should decrease by 1 after completing the order");

        softAssert.assertAll();
    }
}
