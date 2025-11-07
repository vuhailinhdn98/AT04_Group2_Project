package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Driver;

import java.util.List;

public class BasePage {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected WebElement find(By locator) {
//        log.debug("FIND {}", locator);
        return Driver.getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> getElements(By locator) {
        return Driver.getDriver().findElements(locator);
    }


    protected void click(By locator) {
//        log.info("CLICK {}", locator);
        WebElement el = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", el);
        }
    }

    protected void type(By locator, String text) {
//        String safe = locator.toString().contains("pass") ? "******" : text;
//        log.info("TYPE {} -> {}", locator, safe);
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

    protected long parsePrice(String text) {
        String digits = text.replaceAll("\\D+", "");
        return digits.isEmpty() ? 0L : Long.parseLong(digits);
    }

    protected void setCheckbox(By locator, boolean shouldBeChecked) {
        WebElement checkBox = find(locator);
        if (checkBox.isSelected() != shouldBeChecked) {
            checkBox.click();
        } else {
            checkBox.sendKeys(Keys.TAB);
        }
    }

}