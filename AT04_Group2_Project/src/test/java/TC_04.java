import models.Product;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HeaderSection;
import pages.HomePage;
import pages.MobileProductListPage;

import java.util.List;

public class TC_04 extends BaseTest {

    @Test(description = "Verify products are sorted by price from low to high")
    public void tc_01_sortByPriceLowToHigh() {

        homePage.assetMobileProductPage();
        mobileproductPage.sortByPriceLowToHigh();

        Assert.assertTrue(mobileproductPage.isPriceSortedDescending(),
                "Products should be sorted by price ascending");

    }
}
