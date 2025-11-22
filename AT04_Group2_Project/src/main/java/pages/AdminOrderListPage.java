package pages;

import models.Order;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Driver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminOrderListPage extends AdminNavigationMenu {

    // Table + rows
    private final By orderTableHeaderLocator    = By.xpath("//*[@id='view_order']//thead//tr");
    private final By orderRowsLocator           = By.xpath("//*[@id='view_order']//tbody//tr");

    // Row-level actions
    private final By viewOrderDetailsBtnLocator = By.xpath(".//td/a");
    private final By completeOrderBtnLocator    = By.className("active_order");
    private final By cancelOrderButtonLocator   = By.className("delete_order");

    /* ---------- Dynamic column locators ---------- */

    private int getColumnIndexByName(String columnName) {
        List<WebElement> headers = find(orderTableHeaderLocator).findElements(By.tagName("th"));
        for (int i = 0; i < headers.size(); i++) {
            if (columnName.equals(headers.get(i).getText().trim())) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException("Column name not found: " + columnName);
    }

    private By orderIdLocator()         { return By.xpath(".//td[" + getColumnIndexByName("ID")          + "]"); }
    private By customerEmailLocator()   { return By.xpath(".//td[" + getColumnIndexByName("Email")       + "]"); }
    private By totalAmountLocator()     { return By.xpath(".//td[" + getColumnIndexByName("Total")       + "]"); }
    private By orderStatusLocator()     { return By.xpath(".//td[" + getColumnIndexByName("Active")      + "]"); }
    private By createdDateTimeLocator() { return By.xpath(".//td[" + getColumnIndexByName("Create_date") + "]"); }

    // Header sort buttons
    private final By sortByOrderStatusLocator   = By.xpath("//*[@id='view_order']//thead//tr/th[4]");
    private final By sortByCreatedDateBtnLocator= By.xpath("//*[@id='view_order']//thead//tr/th[5]");

    /* ---------- Sorting helpers ---------- */

public void sortOrdersByPendingStatus(String ascOrDesc) {
    for (int i = 0; i < 3; i++) {
        WebElement btn = find(sortByOrderStatusLocator);
        String currentClass = btn.getAttribute("class");
        if (currentClass.contains(ascOrDesc)) {
            return;
        }
        btn.click();
        waitToBeVisible(orderRowsLocator);
        sleep(500);
    }
}

    public void sortOrdersByMostRecent() {
        for (int i = 0; i < 3; i++) {
            WebElement btn = find(sortByCreatedDateBtnLocator);
            if (btn.getAttribute("class").contains("desc")) {
                return;
            }
            btn.click();
            waitToBeVisible(orderRowsLocator);
            sleep(500);
        }
    }
    /* ---------- Row helpers ---------- */

    private WebElement getMostRecentOrderRow() {
        sortOrdersByMostRecent();
        sortOrdersByPendingStatus("asc");
        return getElements(orderRowsLocator).get(0);
    }

    public void openMostRecentOrderDetails() {
        getMostRecentOrderRow().findElement(viewOrderDetailsBtnLocator).click();
    }

    /* ---------- Read row data ---------- */

    public Order getMostRecentOrderInfo() {
        WebElement row = getMostRecentOrderRow();
        String orderId        = row.findElement(orderIdLocator()).getText().trim();
        String customerEmail  = row.findElement(customerEmailLocator()).getText().trim();
        long   totalAmount    = parsePrice(row.findElement(totalAmountLocator()).getText().trim());
        String orderStatus    = row.findElement(orderStatusLocator()).getText().trim();
        LocalDateTime createdDateTime = parseOrderDateTime(row.findElement(createdDateTimeLocator()).getText().trim());
        return new Order(orderId, customerEmail, totalAmount, orderStatus, createdDateTime);
    }

    /* ---------- Actions on most recent row ---------- */

    public void completeMostRecentOrder(String orderId) {
        WebElement mostRecentRow = getMostRecentOrderRow();
        mostRecentRow.findElement(completeOrderBtnLocator).click();

        Driver.getDriver().switchTo().alert().accept();
        waitAlertToBePresent();
        Driver.getDriver().switchTo().alert().accept();
        waitToBeVisible(orderRowsLocator);

        sortOrdersByMostRecent();
        sortOrdersByPendingStatus("desc");

        List<WebElement> rows = getElements(orderRowsLocator);
        WebElement targetStatusCell = null;
        for (WebElement row : rows) {
            String currentId = row.findElement(orderIdLocator()).getText().trim();
            if (currentId.equals(orderId)) {
                targetStatusCell = row.findElement(orderStatusLocator());
                break;
            }
        }

        if (targetStatusCell == null) {
            throw new IllegalStateException("Order with ID " + orderId + " not found after completing payment");
        }

        Driver.getWebDriverWait().until(
                ExpectedConditions.textToBePresentInElement(targetStatusCell, "Đã thanh toán")
        );
    }

    public void cancelMostRecentOrder() {
        getMostRecentOrderRow().findElement(cancelOrderButtonLocator).click();
        Driver.getDriver().switchTo().alert().accept();
        waitAlertToBePresent();
        Driver.getDriver().switchTo().alert().accept();
        waitToBeVisible(orderRowsLocator);
    }

    /* ---------- Queries ---------- */

    public List<Order> getLatestPaidOrderList() {
        List<Order> latestPaidOrderList = new ArrayList<>();
        for (WebElement row : getElements(orderRowsLocator)) {
            String status = row.findElement(orderStatusLocator()).getText().trim();
            if (!status.contains("Đã thanh toán")) {
                break;
            }
            String orderId = row.findElement(orderIdLocator()).getText().trim();
            String customerEmail = row.findElement(customerEmailLocator()).getText().trim();
            long totalAmount = parsePrice(row.findElement(totalAmountLocator()).getText().trim());
            LocalDateTime createdDateTime = parseOrderDateTime(row.findElement(createdDateTimeLocator()).getText().trim());
            latestPaidOrderList.add(new Order(orderId, customerEmail, totalAmount, status, createdDateTime));
        }
        return latestPaidOrderList;
    }

    public Order findOrderById(List<Order> orderList, String orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
}
