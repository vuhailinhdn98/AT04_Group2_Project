import models.Product;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HeaderSection;


public class TC_02 extends BaseTest {
    @Test(
            description = "Verify the newly added product is searchable by partial name"
    )
    public void tc_02() {
        String Keyword = "Test Product";//lấy 1 sản phẩm từ trang chủ để search

        homePage.openSearchModal();
        homePage.searchProduct(Keyword);

        Assert.assertTrue(header.hasProductWithKeyword(Keyword),
                "No products contain keyword: " + Keyword);
    }
}



