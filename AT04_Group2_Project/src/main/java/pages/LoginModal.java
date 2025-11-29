package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class LoginModal extends HomePage {

    private final By loginModalContainerLocator = By.cssSelector("#login .modal-content");
    private final By emailOrPhoneInputLocator = By.id("e_p_lg");
    private final By emailOrPhoneSuccessValidationLocator = By.cssSelector("#e_p_e_lg .text-success");
    private final By passwordInputLocator = By.id("pass_lg");
    private final By passwordSuccessValidationLocator = By.cssSelector("#pass_e_lg .text-success");
    private final By loginButtonLocator = By.id("submit_lg");

    public void enterEmailOrPhone(String emailOrPhone) {
        type(emailOrPhoneInputLocator, emailOrPhone);
        find(emailOrPhoneInputLocator).sendKeys(Keys.TAB);
        waitToBeVisible(emailOrPhoneSuccessValidationLocator);
    }

    public void enterPassword(String password) {
        type(passwordInputLocator, password);
        find(passwordInputLocator).sendKeys(Keys.TAB);
        waitToBeVisible(passwordSuccessValidationLocator);
    }

    public void clickLoginButton() {
        click(loginButtonLocator);
    }

    @Step("Login with email/phone: {emailOrPhone} and password: {password}")
    public void login(String emailOrPhone, String password) {
        waitToBeVisible(loginModalContainerLocator);
        enterEmailOrPhone(emailOrPhone);
        enterPassword(password);
        clickLoginButton();
        waitUntilLoggedIn();
    }

}