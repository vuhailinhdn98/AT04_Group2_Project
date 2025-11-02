package pages;

import models.Product;
import org.openqa.selenium.*;
import utils.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage{
    //Locators
    private final By productCardLocator = By.cssSelector(".container .thumbnail");
    private final By productNameLocator = By.cssSelector(".products-content-label a");
    private final By productPriceLocator = By.cssSelector(".products-content-label .text-danger span");
    private final By addToCartBtnLocator = By.cssSelector(".cart_class");

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

    //homepage
    private boolean isAddToCartBtnEnabled(WebElement productCard) {
        WebElement addToCartBtn = productCard.findElement(addToCartBtnLocator);
        String btnClass = String.valueOf(addToCartBtn.getAttribute("class")).toLowerCase();
        return addToCartBtn.isDisplayed() && addToCartBtn.isEnabled() && addToCartBtn.getAttribute("disabled") == null && !btnClass.contains("disabled");
    }

    public void openFirstInStockProductDetails() {
        int size = getElements(productCardLocator).size();
        for (int i = 0; i < size; i++) {
            try {
                WebElement productCard = getElements(productCardLocator).get(i); // re-find mỗi vòng
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
                i--; // retry lại cùng index với phần tử mới
            }
        }
        throw new IllegalStateException("No in-stock product on Homepage");
    }

    public List<Product> addInStockProductsToCart(int numOfProducts) {
        List<Product> addedProducts = new ArrayList<>();
        List<WebElement> cards = getElements(productCardLocator);

        for (WebElement card : cards) {
            if (addedProducts.size() >= numOfProducts) break;

            // lấy nút add-to-cart trong card
            WebElement addToCartBtn = card.findElement(addToCartBtnLocator);
            if (!isAddToCartBtnEnabled(card)) continue; // skip hết hàng

            // lấy name + price từ card
            String name = card.findElement(productNameLocator).getText().trim();
            long price = Long.parseLong(card.findElement(productPriceLocator).getText().substring(1).replaceAll("\\D+", ""));

            // click add-to-cart
            scrollIntoView(addToCartBtn);
            try {
                addToCartBtn.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", addToCartBtn);
            }

            // add vào list
            addedProducts.add(new Product(name, price));
        }

        if (addedProducts.size() < numOfProducts) {
            System.out.println("Warning: only added " + addedProducts.size() + " products due to stock/availability.");
        }
        return addedProducts;
    }


}
