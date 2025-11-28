import io.qameta.allure.Allure;
import models.Product;
import org.testng.annotations.Test;
import testdata.ProductDataTest;

import java.util.Map;

public class TC_01 extends BaseTest {

    @Test(description = "Verify admin can add a new product and it appears in the featured products on homepage")
    public void tc_01() {
        Allure.step("Go to Admin page");
        homePage.openLoginModal();
        loginModal.login(ADMIN_EMAIL, ADMIN_PASSWORD);
        homePage.goToAdminControlPanel();

        Allure.step("Click on Products menu, then click Add Products");

        adminDashboardPage.accessAdminProductListPage();
        adminProductListPage.accessAdminAddProductPage();

        Allure.step("Fill in product information and click SAVE button");

        Map<String, String> manufacturers = addProductPage.getAllManufacturers();
        productDataTest = new ProductDataTest(manufacturers);
        addProductPage.addProduct(productDataTest);

        Allure.step("Go to storefront");

        homePage.openHomePage();

        Allure.step("Verify product displays in product list");

        Product featuredProduct = homePage.getFirstFeaturedProduct();

        softAssert.assertEquals(
                featuredProduct.getName().trim().toLowerCase(),
                productDataTest.getName().trim().toLowerCase(),
                "Name product does not match created product."
        );
        softAssert.assertEquals(
                featuredProduct.getPrice(),
                productDataTest.getPrice(),
                "Price product does not match created product."
        );

        softAssert.assertAll();
    }
}