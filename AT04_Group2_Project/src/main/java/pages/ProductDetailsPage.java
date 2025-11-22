package pages;

import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Driver;

public class ProductDetailsPage extends BasePage {
    private final By productNameLocator = By.cssSelector(".name .text-primary");
    private final By productPriceLocator = By.cssSelector(".price-old");
    private By stockStatusLocator = By.cssSelector(".name .text-danger");
    private final By addToCartBtnLocator = By.cssSelector(".products .promotion+button");

    public String getProductName() {
        return getText(productNameLocator);
    }

    public String getProductPrice() {
        return getText(productPriceLocator);
    }

    public boolean isAddToCartEnabled() {
        return find(addToCartBtnLocator).isEnabled();
    }

    public String getStockStatus() {
        WebDriverWait wait = Driver.getWebDriverWait();
        WebElement stockElement = wait.until(ExpectedConditions.visibilityOfElementLocated(stockStatusLocator));
        String stockText = stockElement.getText().toLowerCase().trim();
        stockText = stockText.replaceAll("[()]", "");
        return stockText;
    }

    public void clickAddToCart() {
        click(addToCartBtnLocator);
    }

    protected long convertPriceToLong(String priceText) {
        return Long.parseLong(priceText.replaceAll("[^0-9]", ""));
    }

    public Product getProductDetails() {
        String name = getText(productNameLocator).trim();
        long price = convertPriceToLong(getText(productPriceLocator));
        return new Product(name, price);
    }
}


