package pages;

import org.openqa.selenium.By;

public class AdminNavigationMenu extends BasePage {

    // Locators
    private final By productsMenuLinkLocator = By.linkText("Products");
    private final By ordersMenuLinkLocator = By.linkText("Orders");

    // Actions
    public void assetAdminProductListPage() {
        click(productsMenuLinkLocator);
    }

    public void assetAdminOrderListPage() {
        click(ordersMenuLinkLocator);
    }
}