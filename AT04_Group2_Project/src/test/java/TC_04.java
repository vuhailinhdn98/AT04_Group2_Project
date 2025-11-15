import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_04 extends BaseTest {

    @Test(description = "Verify products are sorted by price from low to high")
    public void tc_01_sortByPriceLowToHigh() {

        homePage.assetMobileProductPage();
        mobileProductListPage.sortByPriceLowToHigh();

        Assert.assertTrue(mobileProductListPage.isPriceSortedAscending(),
                "Products should be sorted by price ascending");

    }
}
