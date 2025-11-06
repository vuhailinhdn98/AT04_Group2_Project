package pages;

import org.openqa.selenium.By;

public class AdminNavigationMenu extends BasePage {

    // Locators
    private final By productsMenuLinkLocator = By.cssSelector("a[href='product_admin.php']");
    private final By ordersMenuLinkLocator = By.cssSelector("a[href='order_admin.php']");

    // Actions
    public void clickProductsMenu() {
        click(productsMenuLinkLocator);
    }

    public void clickOrdersMenu() {
        click(ordersMenuLinkLocator);
    }
}