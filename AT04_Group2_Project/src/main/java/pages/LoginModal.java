package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Driver;

public class LoginModal extends BasePage {

    private final By loginModalContainerLocator = By.cssSelector("#login .modal-content");
    private final By emailOrPhoneInputLocator = By.id("e_p_lg");
    private final By passwordInputLocator = By.id("pass_lg");
    private final By loginButtonLocator = By.id("submit_lg");

    public void enterEmailOrPhone(String emailOrPhone) {
        type(emailOrPhoneInputLocator, emailOrPhone);
    }

    public void enterPassword(String password) {
        type(passwordInputLocator, password);
    }

    public void clickLoginButton() {
        click(loginButtonLocator);
        find(loginModalContainerLocator);
    }

    public void login(String emailOrPhone, String password) {

        enterEmailOrPhone(emailOrPhone);
        enterPassword(password);
        WebElement loginBtn = find(loginButtonLocator);
        scrollIntoView(loginBtn);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].focus();", loginBtn);
        try {
            clickLoginButton();
        } catch (ElementClickInterceptedException ex) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", loginBtn);
        }
        Driver.getWebDriverWait().until(ExpectedConditions.or(ExpectedConditions.invisibilityOf(find(loginModalContainerLocator)), ExpectedConditions.stalenessOf(find(loginModalContainerLocator))));
    }

}