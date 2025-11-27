import io.qameta.allure.Allure;
import models.Product;
import org.testng.annotations.Test;

public class TC_03 extends BaseTest {

    @Test(
            description = "Verify that product information on Home Page matches with Product Details Page"
    )
    public void tc_03() {
        log.info("1. Go to Home page");
        Allure.step("1. Go to Home page");
        Product featuredProduct = homePage.getFirstFeaturedProduct();

        log.info("2. Locate product in list & 3. Click on product name");
        Allure.step("2. Locate product in list & 3. Click on product name");
        homePage.openFirstFeaturedProductDetails();
        Product productDetails = productDetailsPage.getProductDetails();
        softAssert.assertEquals(featuredProduct, productDetails, "Product info mismatch between Home and Details page");
        softAssert.assertAll();
    }
}

