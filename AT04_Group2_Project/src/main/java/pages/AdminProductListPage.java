package pages;

import org.openqa.selenium.By;

public class AdminProductListPage extends BasePage {

    // Locators
    private final By searchTextbox = By.cssSelector("input[type='search'][aria-controls='view']");
    private final By addProductButton = By.cssSelector("a[href='add_product_admin.php']");

    private By getEditButtonByProductId(String productId) {
        return By.xpath("//tr[td[1][normalize-space(text())='" + productId + "']]//a[contains(@href,'edit_product_admin.php')]");
    }

    // Actions - Search
    public AdminProductListPage enterSearchText(String searchText) {
        type(searchTextbox, searchText);
        return this;
    }

    public AdminProductListPage clearSearch() {
        find(searchTextbox).clear();
        return this;
    }

    // Actions - Navigation
    public void clickAddProductButton() {
        click(addProductButton);
    }

    public void clickEditButton(String productId) {
        scrollIntoView(find(getEditButtonByProductId(productId)));
        click(getEditButtonByProductId(productId));
    }
}
