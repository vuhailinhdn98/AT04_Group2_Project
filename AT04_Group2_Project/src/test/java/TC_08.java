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

        loginModal.login("tranthang212@gmail.com", "123123", false);

        List<Product> addedProducts = homePage.addInStockProductsToCart(1);
        String firstAddedProduct = addedProducts.get(0).getName();

        softAssert.assertTrue(cartModal.isCartModalVisible(), "Cart modal should open after adding product");
        softAssert.assertEquals(cartModal.getProductRowCount(), 1, "Should be 1 product row in cart");
        softAssert.assertEquals(cartModal.getItemQty(0), 1, "Default qty should be 1");

//        List<String> addedProductName = cartModal.getAllProductsNameInCart();
        long expectedTotal = cartModal.getCartTotalAmount();

        log.info("3. Create an order");
        cartModal.clickOrderNowBtn();

        checkoutModal.proceedToCheckoutForLoggedInUser("123123");

        orderConfirmationModal.closeOrderConfirmationModal();


//        String customerEmailOfOrderBeforeComplete = adminOrderListPage.getMostRecentOrderCustomerEmail();
//        long totalAmountOfOrderBeforeComplete = adminOrderListPage.getMostRecentOrderTotalAmount();
//
//        adminOrderListPage.openMostRecentOrderDetails();
//        String
//
//        softAssert.assertEquals(customerEmailOfOrderBeforeComplete, "tranthang212@gmail.com", "Most recent order customer email should match");
//        softAssert.assertEquals(totalAmountOfOrderBeforeComplete, expectedTotal, "Most recent order total in admin should match the order total" + expectedTotal);

        log.info("4. Go to Admin Panel > Orders, capture stock of 1st product BEFORE complete and complete the newly created order");
        homePage.goToAdminControlPanel();

        adminDashboardPage.accessAdminOrderListPage();

        adminOrderListPage.accessAdminProductListPage();

        adminProductListPage.openProductDetailsByName(firstAddedProduct);

        int stockBefore = adminProductsDetailsPage.getStock();

        adminProductsDetailsPage.accessAdminOrderListPage();

        adminOrderListPage.completeMostRecentOrder();
        softAssert.assertTrue(adminOrderListPage.isMostRecentOrderCompleted(), "Most recent order should be marked as completed");

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
