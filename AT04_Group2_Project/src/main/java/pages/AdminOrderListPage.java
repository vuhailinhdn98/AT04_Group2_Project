package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AdminOrderListPage extends AdminNavigationMenu {
    private final By orderRowsLocator = By.xpath("//*[@id='view_order']//tbody//tr");
    private final By viewOrderDetailsBtnLocator = By.xpath(".//td/a");
    private final By sortByCreatedDateBtnLocator = By.xpath("//*[@id='view_order']//thead//tr/th[5]");
    private final By customerEmailLocator = By.xpath(".//td[2]");
    private final By totalAmountLocator = By.xpath(".//td[3]");

    private void sortOrdersByMostRecent() {
        while (!find(sortByCreatedDateBtnLocator).getAttribute("class").contains("desc")) {
            find(sortByCreatedDateBtnLocator).click();
        }
    }

    private WebElement getMostRecentOrderRow() {
        sortOrdersByMostRecent();
        return getElements(orderRowsLocator).get(0);
    }

    public String getMostRecentOrderCustomerEmail() {
        return getMostRecentOrderRow().findElement(customerEmailLocator).getText().trim();
    }

    public long getMostRecentOrderTotalAmount() {
        String totalText = getMostRecentOrderRow().findElement(totalAmountLocator).getText().trim();
        return parsePrice(totalText);
    }
}
