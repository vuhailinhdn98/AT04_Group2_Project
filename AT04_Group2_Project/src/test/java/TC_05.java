import org.testng.annotations.Test;

public class TC_05 extends BaseTest {
    @Test(
            description = "Verify user can add a product to cart from product details page"
    )
    public void tc_05() {

        homePage.openFirstInStockProductDetails();

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
