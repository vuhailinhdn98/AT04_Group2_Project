package pages;

import org.openqa.selenium.By;

public class AdminProductListPage extends AdminNavigationMenu {

    // Locators
    private final By searchTextboxLocator = By.cssSelector("input[type='search'][aria-controls='view']");
    private final By addProductButtonLocator = By.className("btn-primary");
    private final By editProductButtonLocator = By.xpath("//table['view']//tbody//tr//td//a");

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
    public void assetAdminAddProductPage() {
        click(addProductButtonLocator);
    }

    public void clickEditButton(String productId) {
        scrollIntoView(find(getEditButtonByProductId(productId)));
        click(getEditButtonByProductId(productId));
    }


    public void openProductDetailsByName(String addedProductName) {
        enterSearchText(addedProductName);
        getElements(editProductButtonLocator).get(0).click();
    }
}
