package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Driver;

public class HeaderSection extends BasePage{
    private final By loginButtonLocator = By.cssSelector(".top-header [data-target='#login']");
    private final By accountDropdownLoggedInLocator = By.xpath("//div[contains(@class,'top-header')]//button[contains(@class,'dropdown-toggle')][not(contains(normalize-space(.),'Đăng nhập'))]");
    private final By accountNameLocator = By.xpath("//div[contains(@class,'top-header')]//button[contains(@class,'dropdown-toggle')]//span[contains(@class,'header-login') and not(contains(@class,'caret'))][normalize-space()]");
    private final By logoutBtnLocator = By.xpath("//div[contains(@class,'login-top')]//a[contains(@class,'logout')]");

    public void openLoginModal() {
        find(loginButtonLocator);
        click(loginButtonLocator);
    }
    public void waitUntilLoggedIn() {
        Driver.getWebDriverWait().until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(accountNameLocator), ExpectedConditions.invisibilityOfElementLocated(loginButtonLocator)));
    }

    public boolean isLoggedIn() {
        return !getElements(accountDropdownLoggedInLocator).isEmpty() || !getElements(logoutBtnLocator).isEmpty();
    }

    public String getAccountName() {
        return isLoggedIn() ? getText(accountNameLocator) : "";
    }
}
