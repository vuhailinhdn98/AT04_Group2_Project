package pages;

import org.openqa.selenium.By;

public class AdminProductListPage extends AdminNavigationMenu {

    // Locators
    private final By searchTextboxLocator = By.cssSelector("input[type='search'][aria-controls='view']");
    private final By addProductButtonLocator = By.className("btn-primary");
    private final By editProductButtonLocator = By.xpath("//table['view']//tbody//tr//td//a");


    // Actions - Search
    public AdminProductListPage enterSearchText(String searchText) {
        type(searchTextboxLocator, searchText);
        return this;
    }

    public void accessAdminAddProductPage() {
        click(addProductButtonLocator);
    }

    public void openProductDetailsByName(String addedProductName) {
        enterSearchText(addedProductName);
        getElements(editProductButtonLocator).get(0).click();
    }


}
