package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Driver;

import java.util.ArrayList;
import java.util.List;

public class CartModal extends HomePage {
    private final By productRowsLocator = By.cssSelector("#view_cart tbody tr:has(.delete_cart)");
    private final By productNameLocator = By.xpath(".//td[2]");
    private final By qtyDropdownLocator = By.className("cart_option_class");
    private final By cartTotalLocator = By.className("toal_money");
    private final By orderNowBtnLocator = By.id("order_product");

    private final By notLoginWarningMsgLocator = By.className("alert-dismissable");
    private final By loginTextLinkLocator = By.cssSelector("#error_cart [data-target='#login']");

    public int getProductRowCount() {
        return getElements(productRowsLocator).size();
    }

    private List<WebElement> getProductRows() {
        List<WebElement> rows = getElements(productRowsLocator);
        List<WebElement> products = new ArrayList<>();
        for (WebElement row : rows) {
            products.add(row);
        }
        return products;
    }
    public void waitForProductsInCart() {
        Driver.getWebDriverWait().until(driver -> {
            int count = getProductRowCount();
            log.debug("Waiting for products in cart... Current count: {}", count);
            return count > 0;
        });
        log.info("Cart has {} product(s)", getProductRowCount());
    }

    public List<String> getAllProductsNameInCart() {
        List<String> productNames = new ArrayList<>();
        for (WebElement row : getProductRows()) {
            String name = row.findElement(productNameLocator).getText().trim();
            productNames.add(name);
        }
        return productNames;
    }

    public int getItemQty(int index) {
        WebElement qty = getProductRows().get(index).findElement(qtyDropdownLocator);
        return Integer.parseInt(new Select(qty).getFirstSelectedOption().getText().trim());
    }
    public void selectQuantityByRowIndex(int rowIndex, int quantity) {
        List<WebElement> rows = getProductRows();
        WebElement productRow = rows.get(rowIndex);
        WebElement qtyDropdown = productRow.findElement(qtyDropdownLocator);
        Select select = new Select(qtyDropdown);
        select.selectByValue(String.valueOf(quantity));

        log.info("Selected quantity {} for product at row {}", quantity, rowIndex);
    }
    public void selectQuantityForFirstProduct(int quantity) {
        log.info("Selecting quantity {} for first product", quantity);
        waitForProductsInCart();
        selectQuantityByRowIndex(0, quantity);
    }

    public void clickOrderNowBtn() {
        click(orderNowBtnLocator);
    }

    public String getNotLoginWarningMsg() {
        return getText(notLoginWarningMsgLocator).substring(1).trim();
    }

    public void clickLoginTextLink() {
        click(loginTextLinkLocator);
    }

    public long getCartTotalAmount() {
        String totalText = getText(cartTotalLocator).substring(1).trim();
        return parsePrice(totalText);
    }
}
