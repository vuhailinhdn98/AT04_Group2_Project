package pages;

import org.openqa.selenium.By;
import testdata.ProductDataTest;

public class AdminProductsDetailsPage extends AdminNavigationMenu {
    private final By stockLocator = By.name("quality");
    private final By saveButtonLocator = By.xpath("//button[@type='submit' and contains(@class, 'btn-success')]");

    public int getStock() {
        String stockText = find(stockLocator).getAttribute("value").trim();
        return parseInt(stockText);
    }

    public AdminProductsDetailsPage updateStock(int stock) {
        type(stockLocator, String.valueOf(stock));
        return this;
    }

    public void updateProduct(ProductDataTest product) {
        updateStock(product.getQuality());
        click(saveButtonLocator);
    }
}

