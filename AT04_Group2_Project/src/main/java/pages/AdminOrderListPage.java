package pages;

import org.openqa.selenium.By;

public class AdminOrderListPage extends AdminNavigationMenu {
    private final By orderRowsLocator = By.xpath("//*[@id='view_order']//tbody//tr");
    private final By viewOrderDetailsBtnLocator = By.xpath(".//td/a");

    private void getMostRecentOrder() {
        getElements(orderRowsLocator).get(0);
    }


}
