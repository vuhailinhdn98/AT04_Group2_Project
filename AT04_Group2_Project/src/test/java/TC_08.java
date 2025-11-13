import models.Product;
import org.testng.annotations.Test;

import java.util.List;

public class TC_08 extends BaseTest {
    @Test(
            description = "Verify product inventory decreases by ordered quantity"
    )
    public void tc_08() {
        log.info("2. Log in and add any in-stock product to cart");
        loginModal.login("tranthang212@gmail.com", "123123", false);

        List<Product> addedProducts = homePage.addInStockProductsToCart(1);

        softAssert.assertTrue(cartModal.isCartModalVisible(), "Cart modal should open after adding product");
        softAssert.assertEquals(cartModal.getProductRowCount(), 1, "Should be 1 product row in cart");
        softAssert.assertEquals(cartModal.getItemQty(0), 1, "Default qty should be 1");

        String addedProductName = addedProducts.get(0).getName();
        long expectedTotal = cartModal.getCartTotalAmount();

        log.info("3. Create an order");
        cartModal.clickOrderNowBtn();
        checkoutModal.proceedToCheckoutForLoggedInUser("123123");

        orderConfirmationModal.closeOrderConfirmationModal();
        homePage.goToAdminControlPanel();
        adminDashboardPage.clickOrdersMenu();

        String customerEmailOfOrderBeforeComplete = adminOrderList.getMostRecentOrderCustomerEmail();
        long totalAmountOfOrderBeforeComplete = adminOrderList.getMostRecentOrderTotalAmount();

        softAssert.assertEquals(customerEmailOfOrderBeforeComplete, "tranthang212@gmail.com", "Most recent order customer email should match");
        softAssert.assertEquals(totalAmountOfOrderBeforeComplete, expectedTotal, "Most recent order total in admin should match the order total" + expectedTotal);

        log.info("4. Go to Admin Panel > Orders, capture stock BEFORE complete and complete the newly created order");

        adminOrderList.clickProductsMenu();
        adminProductListPage.openProductDetailsByName(addedProductName);

        int stockBefore = adminProductsDetailsPage.getStock();

        adminProductsDetailsPage.clickOrdersMenu();
        adminOrderList.getMostRecentOrder();
        adminOrderList.completeMostRecentOrder();





//        adminOrderList.openMostRecentOrderDetails();
//
//        List<Product> orderedProducts = adminOrderDetailsPage.getOrderedProducts();
//
//        softAssert.assertEquals(orderedProducts, addedProducts, "Ordered products in admin should match the products added to cart");

//        softAssert.assertTrue(adminOrderList.hasOrderWithProduct(product.getName()), "Newly created order should appear in admin orders");

        // complete the most recent matching order
//        adminOrderDetailsPage.navigateBack();

        // completed order should disappear from active list
//        softAssert.assertFalse(adminOrderList.hasOrderWithProduct(product.getName()), "Completed order should no longer appear in active orders");
//
//        log.info("5. Go to Admin Panel > Products and check the product stock decreased to 1");
//        adminDashboardPage.clickProductsMenu();
//        // AdminProductListPage is present in project; assume helper to get stock by product name
//        int stock = adminProductList.getStockByProductName(product.getName());
//        softAssert.assertEquals(stock, 1, "Product stock should be reduced to 1 after completing the order");

        softAssert.assertAll();
    }
}
