package pages;

import io.qameta.allure.Allure;
import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

    private By addToCartBtnLocator(String productName) {
        return By.xpath(String.format("//div[@class='products-content-label'][a/p[text()='%s']]//button", productName));
    }
    //Methods
    public List<Product> getAllProducts() {
        return getElements(productCardLocator)
                .stream()
                .map(this::getProduct)
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

    private WebElement featuredFirstProduct() {
        return Driver.getWebDriverWait().until(d -> {
            var sections = d.findElements(sectionLocator);
            if (sections.size() <= 1) return null;
            var thumbs = sections.get(1).findElements(By.cssSelector(".thumbnail"));
            return thumbs.isEmpty() ? null : thumbs.get(0);
        });
    }
    public Product getFirstFeaturedProduct() {
        WebElement firstProduct = featuredFirstProduct();
        scrollIntoView(firstProduct);
        return getProduct(firstProduct);
    }
    public void openFirstFeaturedProductDetails() {
        WebElement p = featuredFirstProduct();
        scrollIntoView(p);
        p.findElement(productNameLocator).click();
    }

    private Product getProduct(WebElement card) {
        String name = card.findElement(productNameLocator).getText().trim();
        String priceText = card.findElement(productPriceLocator).getText();
        long price = parsePrice(priceText);

        Allure.step("Get product from card: "+ name + " - " + price);

        return new Product(name, price);
    }

    public List<Product> addInStockProductsToCart(int numOfProducts) {
        Allure.step("Add " + numOfProducts + " in-stock products to cart");

        List<Product> added = new ArrayList<>();
        if (numOfProducts <= 0) return added;

        for (WebElement card : getElements(productCardLocator)) {
            if (added.size() >= numOfProducts) break;
            if (!isAddToCartBtnEnabled(card)) continue;

            Product product = getProduct(card);
            WebElement addBtn = card.findElement(addToCartBtnLocator);
            scrollIntoView(addBtn);
            addBtn.click();
            waitCartModalVisible();

            added.add(product);
            if (added.size() < numOfProducts) {
                closeCartModal();
            }
        }
        return added;
    }

    public boolean isAddToCartButtonEnabled(Product outOfStockProduct) {
        Allure.step("Check if add to cart button is enabled for product: " + outOfStockProduct.getName() + " with qty = 0");
        WebElement addToCartButton = find(addToCartBtnLocator(outOfStockProduct.getName()));
        boolean isEnabled = isEnabled(addToCartButton);
        if (isEnabled) {
            throw new IllegalStateException(
                    "Add to cart button should be disabled for product with qty = 0, but it is enabled for product: "
                            + outOfStockProduct.getName()
            );
        }
        return isEnabled;
    }

    public void openProductDetailsByName(String name) {
        Allure.step("Open product details by name: " + name);
        for (WebElement card : getElements(productCardLocator)) {
            Product product = getProduct(card);
            if (product.getName().equals(name)) {
                WebElement nameLink = card.findElement(productNameLocator);
                scrollIntoView(nameLink);
                nameLink.click();
                break;
            }
        }
    }
}
