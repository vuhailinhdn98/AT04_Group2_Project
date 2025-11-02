package pages;

import org.openqa.selenium.By;

public class LoginModal extends BasePage {

    private final By emailOrPhoneInput = By.id("e_p_lg");
    private final By passwordInput = By.id("pass_lg");
    private final By loginButton = By.id("submit_lg");

    public void enterEmailOrPhone(String emailOrPhone) {
        type(emailOrPhoneInput, emailOrPhone);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void login(String emailOrPhone, String password) {
        enterEmailOrPhone(emailOrPhone);
        enterPassword(password);
        clickLoginButton();
    }
}