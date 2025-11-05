import org.testng.annotations.Test;
import models.Product;
import pages.*;


public class TC_01 extends BaseTest {

    @Test(
            description = "Verify admin can add a new product and it appears in the featured products on homepage"
    )
    public void tc_01() {
        HeaderSection header = new HeaderSection();
        LoginModal loginModal = new LoginModal();
        AdminNavigationMenu adminMenu = new AdminNavigationMenu();
        AdminProductListPage adminProductList = new AdminProductListPage();
        AdminAddProductPage addProductPage = new AdminAddProductPage();
        HomePage homePage = new HomePage();

        // Step 1–2: Login admin
        header.openLoginModal();
        loginModal.login("tranthang212@gmail.com", "123123");
        header.waitUntilLoggedIn();

        // Step 3: Mở tab mới Admin Panel và tự động switch qua đó
        header.goToAdminControlPanel();

        // Step 4–5: Vào danh sách sản phẩm → Add Product
        adminMenu.clickProductsMenu();
        adminProductList.clickAddProductButton();

        // Step 6: Nhập thông tin sản phẩm
        String productName = "Test Product";
        String productPrice = "12900000";
        String productQuality = "5";
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

        // Step 7: Mở tab mới và về trang chủ user
        header.openHomePage();

        // Step 8: Verify sản phẩm nổi bật
        Product featuredProduct = homePage.getFirstFeaturedProduct();

        softAssert.assertEquals(
                featuredProduct.getName().trim().toLowerCase(),
                productName.trim().toLowerCase(),
                "Tên sản phẩm hiển thị trên trang không khớp với sản phẩm vừa tạo."
        );

        long expectedPrice = Long.parseLong(productPrice);
        softAssert.assertEquals(featuredProduct.getPrice(), expectedPrice, "Giá sản phẩm không trùng khớp!");
    }
}
