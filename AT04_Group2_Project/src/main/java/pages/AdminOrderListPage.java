package pages;

import models.Order;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Driver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdminOrderListPage extends AdminNavigationMenu {

    // Table + rows
    private final By orderTableHeaderLocator    = By.xpath("//*[@id='view_order']//thead//tr");
    private final By orderRowsLocator           = By.xpath("//*[@id='view_order']//tbody//tr");

    // Row-level actions
    private final By viewOrderDetailsBtnLocator = By.xpath(".//td/a");
    private final By completeOrderBtnLocator    = By.className("active_order");
    private final By cancelOrderButtonLocator   = By.className("delete_order");

    // Header sort buttons
    private final By sortByOrderStatusLocator   = By.xpath("//*[@id='view_order']//thead//tr/th[4]");
    private final By sortByCreatedDateBtnLocator= By.xpath("//*[@id='view_order']//thead//tr/th[5]");

    /* ---------- Dynamic column locators ---------- */

    private List<WebElement> getHeaderCells() {
        return find(orderTableHeaderLocator).findElements(By.tagName("th"));
    }

    private List<String> columnTextList() {
        List<String> headers = new ArrayList<>();
        for (WebElement th : getHeaderCells()) {
            headers.add(th.getText().trim());
        }
        return headers;
    }

    private int getColumnIdxByName(String columnName) {
        List<String> texts = columnTextList();
        int idx = texts.indexOf(columnName);
        if (idx == -1) {
            throw new IllegalArgumentException("Column name not found: " + columnName);
        }
        return idx + 1; // 1-based for xpath
    }

    private By tableDataLocator(int columnIdx, int rowIdx) {
        return By.xpath(String.format("//*[@id='view_order']//tbody//tr[%d]//td[%d]", rowIdx, columnIdx));
    }

    public String getCellText(int rowIdx, String columnName) {
        int columnIdx = getColumnIdxByName(columnName);
        return getText(tableDataLocator(columnIdx, rowIdx));
    }

    private WebElement getHeaderCellByText(String columnName) {
        for (WebElement th : getHeaderCells()) {
            if (columnName.equals(th.getText().trim())) {
                return th;
            }
        }
        throw new IllegalArgumentException("Header not found: " + columnName);
    }

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
        sortOrdersByMostRecent();
        sortOrdersByPendingStatus("asc");

        int rowIdx = 1;

        String orderId        = getCellText(rowIdx, "ID");
        String customerEmail  = getCellText(rowIdx, "Email");
        long   totalAmount    = parsePrice(getCellText(rowIdx, "Total"));
        String orderStatus    = getCellText(rowIdx, "Active");
        LocalDateTime createdDateTime = parseOrderDateTime(getCellText(rowIdx, "Create_date"));
        return new Order(orderId, customerEmail, totalAmount, orderStatus, createdDateTime);
    }

    /* ---------- Actions on most recent row ---------- */

    public void completeOrder(String orderId) {
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

        for (int i = 0; i < rows.size(); i++) {
            int rowIdx = i + 1;
            String currentId = getCellText(rowIdx, "ID");
            if (currentId.equals(orderId)) {
                // Lấy cell status bằng tên cột "Active"
                String xpath = String.format("//*[@id='view_order']//tbody//tr[%d]//td[%d]",
                        rowIdx,
                        // tái sử dụng header locator để lấy index cột "Active"
                        columnTextList().indexOf("Active") + 1
                );
                targetStatusCell = find(By.xpath(xpath));
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
        List<WebElement> rows = getElements(orderRowsLocator);

        for (int i = 0; i < rows.size(); i++) {
            int rowIdx = i + 1;

            String status = getCellText(rowIdx, "Active");
            if (!status.contains("Đã thanh toán")) {
                break;
            }

            String orderId       = getCellText(rowIdx, "ID");
            String customerEmail = getCellText(rowIdx, "Email");
            long   totalAmount   = parsePrice(getCellText(rowIdx, "Total"));
            LocalDateTime createdDateTime =
                    parseOrderDateTime(getCellText(rowIdx, "Create_date"));

            latestPaidOrderList.add(
                    new Order(orderId, customerEmail, totalAmount, status, createdDateTime)
            );
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

    public LocalDateTime parseOrderDateTime(String dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd '|' hh:mm a", Locale.ENGLISH);
        return LocalDateTime.parse(dateTime.trim(), format);
    }
}