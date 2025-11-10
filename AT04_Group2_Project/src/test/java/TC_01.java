import org.testng.annotations.Test;
import models.Product;
import pages.*;

public class TC_01 extends BaseTest {

    @Test(
            description = "Verify admin can add a new product and it appears in the featured products on homepage"
    )
    public void tc_01() {

        homePage.openLoginModal();
        loginModal.login("tranthang212@gmail.com","123123",false);

        homePage.goToAdminControlPanel();

        adminMenu.clickProductsMenu();//rename
        adminProductList.clickAddProductButton();//rename

        // Step 6: Nhập thông tin sản phẩm
        String productName = "Test Product";
        int productPrice = 12_900_000;
        int productQuality = 5;
        String productSale = "10";
        String manufacturer = "1";
        String imagePath = "C:\\Users\\ASUS\\Downloads\\chipktest.jpg";
        String specification = "Sản phẩm test tự động - chipk";

        addProductPage.addProduct(
                productName,
                productPrice,
                productQuality,
                productSale,
                manufacturer,
                imagePath,
                specification
        );

        homePage.openHomePage();

        Product featuredProduct = homePage.getFirstFeaturedProduct();


        softAssert.assertEquals(
                featuredProduct.getName().trim().toLowerCase(), productName.trim().toLowerCase(),
                "Name product does not match created product."
        );
        softAssert.assertEquals(featuredProduct.getPrice(),productPrice, "Price product does not match created product.");
        softAssert.assertAll();
    }
}
