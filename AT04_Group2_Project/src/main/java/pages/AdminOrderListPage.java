package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.Driver;

public class AdminOrderListPage extends AdminNavigationMenu {
    private final By orderRowsLocator = By.xpath("//*[@id='view_order']//tbody//tr");
    private final By viewOrderDetailsBtnLocator = By.xpath(".//td/a");
    private final By sortByOrderStatusLocator = By.xpath("//*[@id='view_order']//thead//tr/th[4]");
    private final By sortByCreatedDateBtnLocator = By.xpath("//*[@id='view_order']//thead//tr/th[5]");
    private final By orderIdLocator = By.xpath(".//td[1]");
    private final By customerEmailLocator = By.xpath(".//td[2]");
    private final By totalAmountLocator = By.xpath(".//td[3]");
    private final By orderStatusLocator = By.xpath(".//td[4]");
    private final By completeOrderBtnLocator = By.className("active_order");

    public void sortOrdersByPendingStatus() {
        while (!find(sortByOrderStatusLocator).getAttribute("class").contains("asc")) {
            find(sortByOrderStatusLocator).click();
        }
    }

    public void sortOrdersByMostRecent() {
        while (!find(sortByCreatedDateBtnLocator).getAttribute("class").contains("desc")) {
            find(sortByCreatedDateBtnLocator).click();
        }
    }

    private WebElement getMostRecentOrderRow() {
        sortOrdersByMostRecent();
        sortOrdersByPendingStatus();
        return getElements(orderRowsLocator).get(0);
    }

    public long getMostRecentOrderId() {
        String orderIdText = getMostRecentOrderRow().findElement(By.xpath(".//td[1]")).getText().trim();
        return Long.parseLong(orderIdText);
    }

    public String getMostRecentOrderCustomerEmail() {
        return getMostRecentOrderRow().findElement(customerEmailLocator).getText().trim();
    }

    public long getMostRecentOrderTotalAmount() {
        String totalText = getMostRecentOrderRow().findElement(totalAmountLocator).getText().trim();
        return parsePrice(totalText);
    }

    public void openMostRecentOrderDetails() {
        getMostRecentOrderRow().findElement(viewOrderDetailsBtnLocator).click();
    }

    public void completeMostRecentOrder() {
        sortOrdersByMostRecent();
        sortOrdersByPendingStatus();
        getElements(completeOrderBtnLocator).get(0).click();
        Driver.getDriver().switchTo().alert().accept();
        Driver.getDriver().switchTo().alert().accept();
    }
}
