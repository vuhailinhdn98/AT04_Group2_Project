package pages;

import org.openqa.selenium.By;

public class OrderConfirmationModal extends HeaderSection{
    private final By orderSuccessMsgLocator = By.cssSelector(".text-success h1");

    public String getOrderSuccessMessage() {
//        waitOrderConfirmationModalVisible();
        return getText(orderSuccessMsgLocator);
    }
}
