package pages;

import org.openqa.selenium.By;

public class AdminProductsDetailsPage extends AdminNavigationMenu{
    private final By stockLocator = By.name("quality");

    public int getStock() {
        String stockText = find(stockLocator).getAttribute("value").trim();
        return Integer.parseInt(stockText);
    }
}
