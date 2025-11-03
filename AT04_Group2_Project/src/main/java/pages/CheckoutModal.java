package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutModal extends BasePage {
    // Product list section (left side)
    private final By productLabels = By.cssSelector("#list_order label");
    private final By productPrices = By.cssSelector("#list_order .text-danger");
    //Total Price
    private final By totalPriceElement = By.xpath("//span[contains(@class, 'text-danger') and contains(text(), 'đ')]");

    // Customer information section (right side)
    private final By fullNameInput = By.id("name_od");
    private final By emailInput = By.id("email_od");
    private final By phoneInput = By.id("phone_od");
    private final By addressInput = By.id("address_od");
    private final By passwordInput = By.id("password_od");

    // Buttons
    private final By backButton = By.id("cancel_order");
    private final By checkoutButton = By.id("order_success");


    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    // Actions - Navigation
    public void clickBackButton() {
        click(backButton);
    }

    public void clickCheckoutButton() {
        click(checkoutButton);
    }

    // Getters - Form values
    public String getFullNameValue() {
        return find(fullNameInput).getAttribute("value");
    }

    public String getEmailValue() {
        return find(emailInput).getAttribute("value");
    }

    public String getPhoneValue() {
        return find(phoneInput).getAttribute("value");
    }

    public String getAddressValue() {
        return find(addressInput).getAttribute("value");
    }

    // Getters - Products
    public int getProductCount() {
        return getElements(productLabels).size();
    }

    public List<String> getProductNames() {
        List<String> names = new java.util.ArrayList<>();
        for (WebElement label : getElements(productLabels)) {
            names.add(label.getText().trim());
        }
        return names;
    }

    public String getProductName(int index) {
        List<WebElement> labels = getElements(productLabels);
        if (index < labels.size()) {
            return labels.get(index).getText().trim();
        }
        return "";
    }

    // Get total price
    public String getTotalPrice() {
        // Find the last text-danger span which usually contains total
        List<WebElement> priceElements = getElements(totalPriceElement);
        if (!priceElements.isEmpty()) {
            return priceElements.get(priceElements.size() - 1).getText().trim();
        }
        return "";
    }
}