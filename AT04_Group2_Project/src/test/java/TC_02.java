import models.Product;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HeaderSection;

import java.util.List;

public class TC_02 extends BaseTest {
    @Test(
            description = "Verify the newly added product is searchable by partial name"
    )
    public void tc_02() {

        HeaderSection header = new HeaderSection();

        //Sản phẩm đã add trước đó (từ TC_01)
        String Keyword = "Test Product";

        header.openSearchModal();
        header.searchProduct(Keyword);

        //Lấy danh sách sản phẩm hiển thị trong kết quả
        List<Product> results = header.getSearchResultsAsProducts();

        //Xác nhận có ít nhất 1 sản phẩm trả về
        Assert.assertTrue(results.size() > 0,
                "No products displayed for keyword: " + Keyword);

        //Kiểm tra sản phẩm vừa thêm có xuất hiện trong kết quả
        boolean targetFound = results.stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(Keyword));
        Assert.assertTrue(targetFound,
                "Target product not found in search results. Expected: " + Keyword);

    }
}


