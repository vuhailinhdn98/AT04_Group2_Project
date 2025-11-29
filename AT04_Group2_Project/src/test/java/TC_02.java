import io.qameta.allure.Allure;
import models.Product;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_02 extends BaseTest {
    @Test(
            description = "Verify the newly added product is searchable by partial name"
    )
    public void tc_02() {
        Product featuredProduct = homePage.getFirstFeaturedProduct();
        String Keyword = featuredProduct.getName();

        Allure.step("Click on search box");
        homePage.openSearchModal();

        Allure.step("Enter product name and click search icon");
        homePage.searchProduct(Keyword);

        Assert.assertTrue(homePage.hasProductWithKeyword(Keyword), "No products contain keyword: " + Keyword);

        softAssert.assertAll();
    }
}



