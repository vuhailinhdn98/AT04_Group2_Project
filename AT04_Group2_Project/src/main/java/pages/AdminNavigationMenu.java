package pages;

import org.openqa.selenium.By;

public class AdminNavigationMenu extends BasePage {

    // Locators
    private final By productsMenuLink = By.cssSelector("a[href='product_admin.php']");
    private final By ordersMenuLink = By.cssSelector("a[href='order_admin.php']");

    // Actions
    public void clickProductsMenu() {
        click(productsMenuLink);
    }

    public void clickOrdersMenu() {
        click(ordersMenuLink);
    }
}