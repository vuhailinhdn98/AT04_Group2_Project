package pages;

import org.openqa.selenium.By;

public class AdminProductListPage extends BasePage {

    // Locators
    private final By searchTextboxLocator = By.cssSelector("input[type='search'][aria-controls='view']");
    private final By addProductButtonLocator = By.cssSelector("a[href='add_product_admin.php']");

    private By getEditButtonByProductId(String productId) {
        return By.xpath("//tr[td[1][normalize-space(text())='" + productId + "']]//a[contains(@href,'edit_product_admin.php')]");
    }

    // Actions - Search
    public AdminProductListPage enterSearchText(String searchText) {
        type(searchTextboxLocator, searchText);
        return this;
    }

    public AdminProductListPage clearSearch() {
        find(searchTextboxLocator).clear();
        return this;
    }

    // Actions - Navigation
    public void clickAddProductButton() {
        click(addProductButtonLocator);
    }

    public void clickEditButton(String productId) {
        scrollIntoView(find(getEditButtonByProductId(productId)));
        click(getEditButtonByProductId(productId));
    }
}
