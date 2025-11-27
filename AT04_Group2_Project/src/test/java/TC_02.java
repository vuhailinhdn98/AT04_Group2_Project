import models.Product;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TC_02 extends BaseTest {
    @Test(
            description = "Verify the newly added product is searchable by partial name"
    )
    public void tc_02() {
        log.info("1. Go to Home page");
        Product featuredProduct = homePage.getFirstFeaturedProduct();
        String Keyword = featuredProduct.getName();

        log.info("2. Click on search box");
        homePage.openSearchModal();
        log.info("3. Enter product name and click search icon");
        homePage.searchProduct(Keyword);
        Assert.assertTrue(homePage.hasProductWithKeyword(Keyword),
                "No products contain keyword: " + Keyword);
        softAssert.assertAll();
    }
}



