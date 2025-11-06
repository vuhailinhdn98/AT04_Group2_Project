package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutModal extends BasePage {
    // Product list section (left side)
    private final By productLabelsLocator = By.cssSelector("#list_order label");
    private final By productPricesLocator = By.cssSelector("#list_order .text-danger");
    //Total Price
    private final By totalPriceElementLocator = By.xpath("//span[contains(@class, 'text-danger') and contains(text(), 'đ')]");

    // Customer information section (right side)
    private final By fullNameInputLocator = By.id("name_od");
    private final By emailInputLocator = By.id("email_od");
    private final By phoneInputLocator = By.id("phone_od");
    private final By addressInputLocator = By.id("address_od");
    private final By passwordInputLocator = By.id("password_od");

    // Buttons
    private final By backButtonLocator = By.id("cancel_order");
    private final By checkoutButtonLocator = By.id("order_success");


    public void enterPassword(String password) {
        type(passwordInputLocator, password);
    }

    // Actions - Navigation
    public void clickBackButton() {
        click(backButtonLocator);
    }

    public void clickCheckoutButton() {
        click(checkoutButtonLocator);
    }

    // Getters - Form values
    public String getFullNameValue() {
        return find(fullNameInputLocator).getAttribute("value");
    }

    public String getEmailValue() {
        return find(emailInputLocator).getAttribute("value");
    }

    public String getPhoneValue() {
        return find(phoneInputLocator).getAttribute("value");
    }

    public String getAddressValue() {
        return find(addressInputLocator).getAttribute("value");
    }

    // Getters - Products
    public int getProductCount() {
        return getElements(productLabelsLocator).size();
    }

    public List<String> getProductNames() {
        List<String> names = new java.util.ArrayList<>();
        for (WebElement label : getElements(productLabelsLocator)) {
            names.add(label.getText().trim());
        }
        return names;
    }

    public String getProductName(int index) {
        List<WebElement> labels = getElements(productLabelsLocator);
        if (index < labels.size()) {
            return labels.get(index).getText().trim();
        }
        return "";
    }

    // Get total price
    public String getTotalPrice() {
        // Find the last text-danger span which usually contains total
        List<WebElement> priceElements = getElements(totalPriceElementLocator);
        if (!priceElements.isEmpty()) {
            return priceElements.get(priceElements.size() - 1).getText().trim();
        }
        return "";
    }
}