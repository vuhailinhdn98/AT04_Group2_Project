package pages;

import models.Product;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        WebElement addToCartBtn = productCard.findElement(addToCartBtnLocator);
        String btnClass = String.valueOf(addToCartBtn.getAttribute("class")).toLowerCase();
        return addToCartBtn.isDisplayed() && addToCartBtn.isEnabled() && addToCartBtn.getAttribute("disabled") == null && !btnClass.contains("disabled");
    }

    public void openFirstInStockProductDetails() {
        int size = getElements(productCardLocator).size();
        for (int i = 0; i < size; i++) {
            try {
                WebElement productCard = getElements(productCardLocator).get(i);
                if (!isAddToCartBtnEnabled(productCard)) continue;

                WebElement productName = productCard.findElement(productNameLocator);
                scrollIntoView(productName);
                try {
                    productName.click();
                } catch (ElementClickInterceptedException e) {
                    ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", productName);
                }
                return;
            } catch (StaleElementReferenceException stale) {
                i--;
            }
        }
        throw new IllegalStateException("No in-stock product on Homepage");
    }
    public Product getFirstFeaturedProduct() {
        try {
            // Đợi page load
            Driver.getWebDriverWait().until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".thumbnail"))
            );

            // Lấy tất cả sections
            List<WebElement> sections = Driver.getDriver().findElements(sectionLocator);

            if (sections.size() < 2) {
                throw new NoSuchElementException("Featured section not found");
            }
            WebElement featuredSection = sections.get(1);
            List<WebElement> products = featuredSection.findElements(By.cssSelector(".thumbnail"));

            WebElement firstProduct = products.get(0);
            scrollIntoView(firstProduct);

            // Lấy tên, giá
            String name = firstProduct.findElement(productNameLocator).getText().trim();

            String priceText = firstProduct.findElement(productPriceLocator).getText();

            long price = parsePrice(priceText);

            return new Product(name, price);

        } catch (Exception e) {
            e.printStackTrace();
            throw new NoSuchElementException("Cannot get first featured product", e);
        }
    }

    public List<Product> addInStockProductsToCart(int numOfProducts) {
        List<Product> addedProducts = new ArrayList<>();
        List<WebElement> cards = getElements(productCardLocator);

        for (WebElement card : cards) {
            if (addedProducts.size() >= numOfProducts) break;

            WebElement addToCartBtn = card.findElement(addToCartBtnLocator);
            if (!isAddToCartBtnEnabled(card)) continue;

            String name = card.findElement(productNameLocator).getText().trim();
            String priceStr = card.findElement(productPriceLocator).getText().substring(0);
            long price = parsePrice(priceStr);
            scrollIntoView(addToCartBtn);
            try {
                addToCartBtn.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", addToCartBtn);
            }

            addedProducts.add(new Product(name, price));
        }

        if (addedProducts.size() < numOfProducts) {
            System.out.println("Warning: only added " + addedProducts.size() + " products due to stock/availability.");
        }
        return addedProducts;
    }
}
