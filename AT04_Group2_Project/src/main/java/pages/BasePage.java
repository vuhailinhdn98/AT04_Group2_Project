package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Driver;

public class BasePage {

    protected WebElement find(By locator) {
        return Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement el = find(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        return find(locator).getText().trim();
    }
}