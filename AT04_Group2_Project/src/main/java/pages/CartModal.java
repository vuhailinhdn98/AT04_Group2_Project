package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class CartModal extends HomePage {
    private final By productRowsLocator = By.cssSelector("#view_cart tbody tr:has(.delete_cart)");
    private final By qtyDropdownLocator = By.className("cart_option_class");
    private final By orderNowBtnLocator = By.id("order_product");

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

    public int getItemQty(int index) {
        WebElement qty = getProductRows().get(index).findElement(qtyDropdownLocator);
        return Integer.parseInt(new Select(qty).getFirstSelectedOption().getText().trim());
    }

    public void clickOrderNowBtn() {
        click(orderNowBtnLocator);
//        waitCheckoutModalVisible();
    }
}
