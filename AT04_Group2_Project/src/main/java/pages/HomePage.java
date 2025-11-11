package pages;

import models.Product;
import org.openqa.selenium.*;
import utils.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends HeaderSection{
    //Locators
    private final By productCardLocator = By.cssSelector(".container .thumbnail");
    private final By productNameLocator = By.cssSelector(".products-content-label a p");
    private final By productPriceLocator = By.cssSelector(".products-content-label .text-danger");
    private final By addToCartBtnLocator = By.cssSelector(".cart_class");
    private final By sectionLocator = By.xpath("//div[contains(@class,'container')]//div[contains(@class,'row')][.//div[@class='thumbnail']]");

    //Methods
    public List<Product> getAllProducts() {
        return getElements(productCardLocator)
                .stream()
                .map(el -> new Product(
                        el.findElement(productNameLocator).getText().trim(),
                        Long.parseLong(el.findElement(productPriceLocator).getText().substring(1).replaceAll("\\D+", ""))
                ))
                .collect(Collectors.toList());
    }

    private boolean isAddToCartBtnEnabled(WebElement productCard) {
        return isEnabled(productCard.findElement(addToCartBtnLocator));
    }

    public void openFirstInStockProductDetails() {
        for (WebElement card : getElements(productCardLocator)) {
            if (!isAddToCartBtnEnabled(card)) {
                continue;
            }
            WebElement nameLink = card.findElement(productNameLocator);
            scrollIntoView(nameLink);
            nameLink.click();
            return;
        }
        throw new IllegalStateException("No in-stock product on Homepage");
    }

    public Product getFirstFeaturedProduct() {
            List<WebElement> sections = Driver.getDriver().findElements(sectionLocator);
            WebElement featuredSection = sections.get(1);
            List<WebElement> products = featuredSection.findElements(By.cssSelector(".thumbnail"));
            WebElement firstProduct = products.get(0);
            scrollIntoView(firstProduct);

            String name = firstProduct.findElement(productNameLocator).getText().trim();

            String priceText = firstProduct.findElement(productPriceLocator).getText();

            long price = parsePrice(priceText);

            return new Product(name, price);
    }

    private Product getProduct(WebElement card) {
        String name = card.findElement(productNameLocator).getText().trim();
        String priceText = card.findElement(productPriceLocator).getText();
        long price = parsePrice(priceText);
        return new Product(name, price);
    }

    public List<Product> addInStockProductsToCart(int numOfProducts) {
        List<Product> added = new ArrayList<>();
        if (numOfProducts <= 0) return added;

        for (WebElement card : getElements(productCardLocator)) {
            if (added.size() >= numOfProducts) break;
            if (!isAddToCartBtnEnabled(card)) continue;

            Product product = getProduct(card);

            WebElement addBtn = card.findElement(addToCartBtnLocator);
            scrollIntoView(addBtn);
            addBtn.click();

            added.add(product);
        }
        return added;
    }
}
