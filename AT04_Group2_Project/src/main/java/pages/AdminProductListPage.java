package pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminProductListPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(AdminProductListPage.class);

    // Locators
    private final By searchTextbox = By.cssSelector("input[type='search'][aria-controls='view']");
    private final By addProductButton = By.cssSelector("a[href='add_product_admin.php']");
    private By getEditButtonByProductId(String productId) {
        return By.xpath("//tr[td[1][text()='" + productId + "']]//a[contains(@href,'edit_product_admin.php')]");
    }
    // Actions - Search
    public AdminProductListPage enterSearchText(String searchText) {
        logger.info("Searching for product: {}", searchText);
        type(searchTextbox, searchText);
        return this;
    }
    public AdminProductListPage clearSearch() {
        logger.info("Clearing search");
        find(searchTextbox).clear();
        return this;
    }
    // Actions - Navigation
    public void clickAddProductButton() {
        logger.info("Clicking Add Product button");
        click(addProductButton);
    }
    public void clickEditButton(String productId) {
        logger.info("Clicking Edit button for product ID: {}", productId);
        click(getEditButtonByProductId(productId));
    }
}