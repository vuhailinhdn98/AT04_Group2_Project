package pages;

import models.Product;
import org.openqa.selenium.By;

public class ProductDetailsPage extends BasePage {
    private final By productNameLocator = By.cssSelector(".name .text-primary");
    private final By productPriceLocator = By.cssSelector(".price-old");
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


