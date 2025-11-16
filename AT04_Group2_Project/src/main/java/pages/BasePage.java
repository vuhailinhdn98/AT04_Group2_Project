package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Driver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class BasePage {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected WebElement find(By locator) {
//        log.debug("FIND {}", locator);
        return Driver.getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitToBeVisible(By locator) {
        Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitToBeInvisible(By locator) {
        Driver.getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected boolean isEnabled(WebElement el) {
        return el.isEnabled() && el.getAttribute("disabled") == null;
    }

    protected boolean isInvisible(By locator) {
        try {
            waitToBeInvisible(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
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
        }
    }

    protected void clickAt(By locator, int offsetX, int offsetY) {
        WebElement el = find(locator);
        new Actions(Driver.getDriver())
                .moveToElement(el, offsetX, offsetY)
                .click()
                .perform();
    }

    protected int parseInt(String text) {
        String digits = text.replaceAll("\\D+", "");
        return digits.isEmpty() ? 0 : Integer.parseInt(digits);
    }

    protected LocalDateTime parseOrderDateTime(String dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd '|' hh:mm a", Locale.ENGLISH);
        return LocalDateTime.parse(dateTime.trim(), format);
    }
}