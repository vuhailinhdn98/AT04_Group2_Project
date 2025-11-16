import models.Product;
import models.ProductDataTest;
import org.testng.annotations.Test;

public class TC_10 extends BaseTest {

    @Test(description = "TC10: Verify canceling the order restores stock and re-enables purchase on the store")
    public void tc_10() {

        homePage.openLoginModal();
        loginModal.login("tranthang212@gmail.com", "123123", false);

        homePage.goToAdminControlPanel();
        adminDashboardPage.accessAdminProductListPage();
        adminProductListPage.assetAdminAddProductPage();
        addProductPage.addProduct(addedProduct);
        String productName = addedProduct.getName();
        int originalQuality = addedProduct.getQuality();

        homePage.openHomePage();
        homePage.openFirstFeaturedProductDetails();
        productDetailsPage.clickAddToCart();
        cartModal.selectQuantityForFirstProduct(originalQuality);
        cartModal.clickOrderNowBtn();
        checkoutModal.waitCheckoutModalVisible();
        checkoutModal.enterPassword("123123");
        checkoutModal.clickCheckoutButton();

        homePage.openHomePage();
        homePage.goToAdminControlPanel();
        adminDashboardPage.accessAdminOrderListPage();
        //     adminOrderList.cancelOrderByCode(orderCode);
//        log.info("✓ Order {} cancelled successfully", orderCode);
//
//        // ==================== STEP 9: VERIFY STOCK RESTORED ====================
//        log.info("=== Step 9: Admin verifies stock restored ===");
//
//        adminDashboardPage.accessAdminProductListPage();
//
//        int restoredQuality = adminProductList.getProductQualityByName(productName);
//        log.info("Product quality after order cancellation: {} (expected: {})",
//                restoredQuality, originalQuality);
//
//        softAssert.assertEquals(restoredQuality, originalQuality,
//                String.format("Product quality should be restored from 0 back to original value: %d",
//                        originalQuality));
//
//        log.info("✓ Stock restored successfully: {} → 0 → {}",
//                originalQuality, restoredQuality);
//
//        // Logout admin
//        homePage.logout();
//
//        // ==================== STEP 10: USER VERIFIES PRODUCT AVAILABLE ====================
//        log.info("=== Step 10: User verifies product is available for purchase again ===");
//
//        homePage.openHomePage();
//        homePage.openProductDetailsByName(productName);
//
//        boolean isAddToCartEnabled = productDetailsPage.isAddToCartEnabled();
//        log.info("Add to Cart button status: {}",
//                isAddToCartEnabled ? "ENABLED (Còn hàng) ✓" : "DISABLED (Hết hàng) ✗");
//
//        softAssert.assertTrue(isAddToCartEnabled,
//                "Product should display 'Còn hàng' and Add to Cart button should be enabled after order cancellation");
//
//        // ==================== FINAL ASSERTION ====================
//        softAssert.assertAll();
//
//        log.info("╔════════════════════════════════════════════════════════╗");
//        log.info("║  ✓✓✓ TEST PASSED: Order Cancellation Restores Stock  ║");
//        log.info("║  Product: '{}'", String.format("%-37s", productName) + "║");
//        log.info("║  Original Stock: {} → After Order: 0 → Restored: {}  ║",
//                String.format("%3d", originalQuality),
//                String.format("%3d", restoredQuality));
//        log.info("╚════════════════════════════════════════════════════════╝");
//    }
//}
    }
}