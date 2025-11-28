package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
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
    @Step("Sort order by status paid")
    public void sortByStatusPaid() {
        sortByCreatedDateDesc();
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
    }

    // sort Active sao cho "Chưa thanh toán" lên trước
    @Step("Sort order by status pending")
    public void sortByStatusPending() {
        sortByCreatedDateDesc();
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
    }

    /* ---------- Read order row data ---------- */

    public Order getOrderById(String orderId) {
        Allure.step("Get order by id: " + orderId);
        // đợi table hiển thị
        waitToBeVisible(orderRowsLocator);

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

    @Step("Complete order: {orderId}")
    public void completeOrder(String orderId) {
        actionOrder(orderId, OrderAction.COMPLETE);
    }

    @Step("Cancel order: {orderId}")
    public void cancelOrder(String orderId) {
        actionOrder(orderId, OrderAction.CANCEL);
    }

    /* ---------- Helper ---------- */

    protected LocalDateTime parseOrderDateTime(String dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd '|' hh:mm a", Locale.ENGLISH);
        return LocalDateTime.parse(dateTime.trim(), format);
    }

    enum OrderAction {
        COMPLETE,
        CANCEL
    }

    private void actionOrder(String orderId, OrderAction action) {
        waitToBeVisible(orderRowsLocator);
        WebElement row = find(rowByOrderIdLocator(orderId));

        switch (action) {
            case COMPLETE:
                row.findElement(completeOrderBtnLocator).click();
                break;
            case CANCEL:
                row.findElement(cancelOrderButtonLocator).click();
                break;
            default:
                throw new IllegalArgumentException("Invalid order action: " + action);
        }

        waitAlertToBePresent();
        Driver.getDriver().switchTo().alert().accept();
        waitAlertToBePresent();
        Driver.getDriver().switchTo().alert().accept();

        waitToBeInvisible(rowByOrderIdLocator(orderId));
    }

}
