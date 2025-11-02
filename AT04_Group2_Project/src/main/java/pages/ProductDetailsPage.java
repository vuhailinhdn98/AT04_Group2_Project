package pages;

import org.openqa.selenium.By;

public class ProductDetailsPage extends BasePage{
    private final By productNameLocator = By.cssSelector(".name .text-primary");
    private final By productPriceLocator = By.cssSelector("");
    private final By addToCartBtnLocator = By.cssSelector(".products .promotion+button");

    public String getProductName()  {
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
}
