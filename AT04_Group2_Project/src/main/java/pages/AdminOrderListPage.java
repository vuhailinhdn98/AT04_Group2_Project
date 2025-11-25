package pages;

import models.Order;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.Driver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdminOrderListPage extends AdminNavigationMenu {

    // Table + rows
    private final By orderTableHeaderLocator = By.xpath("//*[@id='view_order']//thead//tr");
    private final By orderRowsLocator = By.xpath("//*[@id='view_order']//tbody//tr");

    // Row-level actions
    private final By completeOrderBtnLocator = By.className("active_order");
    private final By cancelOrderButtonLocator = By.className("delete_order");

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

    private By sortByHeaderNameLocator(String columnName) {
        return By.xpath(String.format("//*[@id='view_order']//thead//tr//th[%d]", getColumnIdxByName(columnName)));
    }

    private By tableDataLocator(String columnName, int rowIdx) {
        int colIdx = getColumnIdxByName(columnName); // lấy index từ THEAD
        return By.xpath(String.format("//*[@id='view_order']//tbody//tr[%d]//td[%d]", rowIdx, colIdx));
    }

    public String getCellText(int rowIdx, String columnName) {
        return getText(tableDataLocator(columnName, rowIdx));
    }

    // Locator theo orderId
    private By rowByOrderIdLocator(String orderId) {
        return By.xpath(String.format("//*[@id='view_order']//tbody//tr[td[text()='%s']]", orderId));
    }

    private By cellByOrderIdAndColumnLocator(String orderId, String columnName) {
        int colIdx = getColumnIdxByName(columnName);
        return By.xpath(String.format("//*[@id='view_order']//tbody//tr[td[text()='%s']]//td[%d]", orderId, colIdx));
    }

    /* ---------- Sorting helpers ---------- */

    // sort Active sao cho "Đã thanh toán" lên trước
    public void sortByStatusPaid() {
        waitToBeVisible(orderRowsLocator);
        WebElement btn = find(sortByHeaderNameLocator("Active"));
        String cls = btn.getAttribute("class");
        if (cls != null && cls.contains("sorting_desc")) {
            return;
        }
        switch (cls) {
            case "sorting":
                btn.click();
                btn.click();
                break;
            case "sorting_asc":
                btn.click();
                break;
            default:
                break;
        }
        waitToBeVisible(orderRowsLocator);
        sleep(500);
    }

    // sort Active sao cho "Chưa thanh toán" lên trước
    public void sortByStatusPending() {
        WebElement btn = find(sortByHeaderNameLocator("Active"));
        String cls = btn.getAttribute("class");
        if (cls != null && cls.contains("sorting_asc")) {
            return;
        }
        switch (cls) {
            case "sorting":
            case "sorting_desc":
                btn.click();
                break;
            default:
                break;
        }
        waitToBeVisible(orderRowsLocator);
        sleep(500);
    }

    public void sortByCreatedDateDesc() {
        waitToBeVisible(orderRowsLocator);
        WebElement btn = find(sortByHeaderNameLocator("Create_date"));
        String cls = btn.getAttribute("class");
        if (cls != null && cls.contains("sorting_desc")) {
            return;
        }
        switch (cls) {
            case "sorting":
                btn.click();
                btn.click();
                break;
            case "sorting_asc":
                btn.click();
                break;
            default:
                break;
        }
        waitToBeVisible(orderRowsLocator);
        sleep(500);
    }

    /* ---------- Row helpers ---------- */

    private WebElement getLatestPendingOrderRow() {
        sortByCreatedDateDesc();
        sortByStatusPending();
        return getElements(orderRowsLocator).get(0);
    }

    /* ---------- Read order row data ---------- */

    public Order getOrderById(String orderId) {
        // đợi table hiển thị
        waitToBeVisible(orderRowsLocator);
        sleep(500);

        // kiểm tra có row chứa ID này không
        if (getElements(rowByOrderIdLocator(orderId)).isEmpty()) {
            throw new IllegalStateException("Order not found: " + orderId);
        }

        String customerEmail = getText(cellByOrderIdAndColumnLocator(orderId, "Email")).trim();
        long totalAmount = parsePrice(getText(cellByOrderIdAndColumnLocator(orderId, "Total")).trim());
        String orderStatus = getText(cellByOrderIdAndColumnLocator(orderId, "Active")).trim();
        LocalDateTime createdDate = parseOrderDateTime(getText(cellByOrderIdAndColumnLocator(orderId, "Create_date")).trim());

        return new Order(orderId, customerEmail, totalAmount, orderStatus, createdDate);
    }

    public Order getLatestPendingOrderInfo() {
        sortByCreatedDateDesc();
        sortByStatusPending();

        int rowIdx = 1;

        String orderId = getCellText(rowIdx, "ID");
        return getOrderById(orderId);
    }

    /* ---------- Actions ---------- */

    public void completeOrder(String orderId) {
        waitToBeVisible(orderRowsLocator);
        WebElement row = find(rowByOrderIdLocator(orderId));
        row.findElement(completeOrderBtnLocator).click();

        waitAlertToBePresent();
        Driver.getDriver().switchTo().alert().accept();
        waitAlertToBePresent();
        Driver.getDriver().switchTo().alert().accept();
        waitToBeVisible(orderRowsLocator);
    }

    public void cancelMostRecentOrder() {  //cancelOrder(int orderId)
        getLatestPendingOrderRow().findElement(cancelOrderButtonLocator).click();
        Driver.getDriver().switchTo().alert().accept();
        waitAlertToBePresent();
        Driver.getDriver().switchTo().alert().accept();
        waitToBeVisible(orderRowsLocator);
    }

    /* ---------- Helper ---------- */

    public LocalDateTime parseOrderDateTime(String dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd '|' hh:mm a", Locale.ENGLISH);
        return LocalDateTime.parse(dateTime.trim(), format);
    }
}
