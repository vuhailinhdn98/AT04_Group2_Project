import models.Product;
import org.testng.annotations.Test;

public class TC_03 extends BaseTest {

    @Test(
            description = "Verify that product information on Home Page matches with Product Details Page"
    )
    public void tc_03() {
        Product featuredProduct = homePage.getFirstFeaturedProduct();
        homePage.openFirstFeaturedProductDetails();
        Product productDetails = productDetailsPage.getProductDetails();
        softAssert.assertEquals(featuredProduct, productDetails, "Product info mismatch between Home and Details page");
        softAssert.assertAll();
    }
}

