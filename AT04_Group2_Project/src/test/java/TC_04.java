import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_04 extends BaseTest {

    @Test(description = "Verify products are sorted by price from low to high")
    public void tc_04() {
        log.info("2. Click \"Điện thoại\" button on menu bar");
        homePage.accessMobileProductPage();

        log.info("3. Click \"Xem theo\" dropdown list & 4. Click \"giá từ thấp đến cao\"");
        mobileProductListPage.sortByPriceLowToHigh();
        Assert.assertTrue(mobileProductListPage.isPriceSortedAscending(),
                "Products should be sorted by price ascending");
        softAssert.assertAll();
    }
}
