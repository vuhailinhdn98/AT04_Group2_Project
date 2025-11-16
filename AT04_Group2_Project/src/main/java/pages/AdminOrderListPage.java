package pages;

import models.Order;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.Driver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AdminOrderListPage extends AdminNavigationMenu {
    private final By orderRowsLocator = By.xpath("//*[@id='view_order']//tbody//tr");
    private final By viewOrderDetailsBtnLocator = By.xpath(".//td/a");
    private final By sortByOrderStatusLocator = By.xpath("//*[@id='view_order']//thead//tr/th[4]");
    private final By sortByCreatedDateBtnLocator = By.xpath("//*[@id='view_order']//thead//tr/th[5]");
    private final By orderIdLocator = By.xpath(".//td[1]");
    private final By customerEmailLocator = By.xpath(".//td[2]");
    private final By totalAmountLocator = By.xpath(".//td[3]");
    private final By orderStatusLocator = By.xpath(".//td[4]");
    private final By createdDateTimeLocator = By.xpath(".//td[5]");
    private final By completeOrderBtnLocator = By.className("active_order");

    public void sortOrdersByPendingStatus(String ascOrDesc) {
        while (!find(sortByOrderStatusLocator).getAttribute("class").contains(ascOrDesc)) {
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
        sortOrdersByPendingStatus("asc");
        return getElements(orderRowsLocator).get(0);
    }

    public Order getMostRecentOrderInfo() {
        WebElement row = getMostRecentOrderRow();
        String orderId = row.findElement(orderIdLocator).getText().trim();
        String customerEmail = row.findElement(customerEmailLocator).getText().trim();
        long totalAmount = parsePrice(row.findElement(totalAmountLocator).getText().trim());
        String orderStatus = row.findElement(orderStatusLocator).getText().trim();
        LocalDateTime createdDateTime = parseOrderDateTime(row.findElement(createdDateTimeLocator).getText().trim());
        return new Order(orderId, customerEmail, totalAmount, orderStatus, createdDateTime);
    }

    public void openMostRecentOrderDetails() {
        getMostRecentOrderRow().findElement(viewOrderDetailsBtnLocator).click();
    }

    public void completeMostRecentOrder() {
        getMostRecentOrderRow().findElement(completeOrderBtnLocator).click();
        Driver.getDriver().switchTo().alert().accept();
        Driver.getDriver().switchTo().alert().accept();
    }

    public List<Order> getLatestPaidOrderList() {
        sortOrdersByMostRecent();
        sortOrdersByPendingStatus("desc");
        return getElements(orderRowsLocator)
                .stream()
                .map(el -> {
                    String orderId = el.findElement(orderIdLocator).getText().trim();
                    String customerEmail = el.findElement(customerEmailLocator).getText().trim();
                    long totalAmount = parsePrice(el.findElement(totalAmountLocator).getText().trim());
                    String orderStatus = el.findElement(orderStatusLocator).getText().trim();
                    LocalDateTime createdDateTime = parseOrderDateTime(el.findElement(createdDateTimeLocator).getText().trim());
                    return new Order(orderId, customerEmail, totalAmount, orderStatus, createdDateTime);
                }).collect(Collectors.toList());
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
