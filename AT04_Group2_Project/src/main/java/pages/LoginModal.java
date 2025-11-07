package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Driver;

public class LoginModal extends HomePage {

    private final By loginModalContainerLocator = By.cssSelector("#login .modal-content");
    private final By emailOrPhoneInputLocator = By.id("e_p_lg");
    private final By passwordInputLocator = By.id("pass_lg");
    private final By rememberAccountCheckboxLocator = By.id("tick_lg");
    private final By loginButtonLocator = By.id("submit_lg");

    public void enterEmailOrPhone(String emailOrPhone) {
        type(emailOrPhoneInputLocator, emailOrPhone);
    }

    public void enterPassword(String password) {
        type(passwordInputLocator, password);
    }

    public void clickLoginButton() {
        click(loginButtonLocator);
//        find(loginModalContainerLocator);
    }

    public void login(String emailOrPhone, String password , boolean rememberAccount) {
        waitToBeVisible(loginModalContainerLocator);
        enterEmailOrPhone(emailOrPhone);
        enterPassword(password);
        setCheckbox(rememberAccountCheckboxLocator, rememberAccount);
        clickLoginButton();
        waitUntilLoggedIn();
    }

}