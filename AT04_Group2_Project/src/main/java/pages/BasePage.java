package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Driver;

import java.util.List;

public class BasePage {

    protected WebElement find(By locator) {
        return Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> getElements(By locator) {
        return Driver.getDriver().findElements(locator);
    }


    protected void click(By locator) {
        WebElement el = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", el);
        }
    }

    protected void type(By locator, String text) {
        WebElement el = find(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        return find(locator).getText().trim();
    }

    protected void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    private long parsePrice(String text) {
        String digits = text.replaceAll("\\D+", "");
        return digits.isEmpty() ? 0L : Long.parseLong(digits);
    }

}