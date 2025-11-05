package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Driver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HeaderSection extends BasePage {
    private final By loginButtonLocator = By.cssSelector(".top-header [data-target='#login']");
    private final By accountDropdownLoggedInLocator = By.xpath("//div[contains(@class,'top-header')]//button[contains(@class,'dropdown-toggle')][not(contains(normalize-space(.),'Đăng nhập'))]");
    private final By accountNameLocator = By.xpath("//div[contains(@class,'top-header')]//button[contains(@class,'dropdown-toggle')]//span[contains(@class,'header-login') and not(contains(@class,'caret'))][normalize-space()]");
    private final By logoutBtnLocator = By.xpath("//div[contains(@class,'login-top')]//a[contains(@class,'logout')]");
    private final By adminControlPanelLink = By.xpath("//a[@href='admin/admin.php' and contains(text(),'Admin Control Panel')]");

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

    public void openAccountDropdown() {
        click(accountNameLocator);
    }

    public void goToAdminControlPanel() {
        find(accountDropdownLoggedInLocator).click();

        WebElement adminLink = find(By.xpath("//a[contains(@href,'admin/admin.php')]"));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", adminLink);

        ArrayList<String> tabs = new ArrayList<>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(tabs.get(tabs.size() - 1));
    }

    public void openHomePage() {
        String homeUrl = "http://14.176.232.213/mobilevn/index.php";

        // remember existing windows
        Set<String> before = new HashSet<>(Driver.getDriver().getWindowHandles());

        // open new tab via JS
        ((JavascriptExecutor) Driver.getDriver()).executeScript("window.open(arguments[0], '_blank');", homeUrl);

        // wait for new window handle
        try {
            Driver.getWebDriverWait().until(driver -> Driver.getDriver().getWindowHandles().size() > before.size());
            ArrayList<String> tabs = new ArrayList<>(Driver.getDriver().getWindowHandles());
            String newest = tabs.get(tabs.size() - 1);
            Driver.getDriver().switchTo().window(newest);

            Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".container")));
        } catch (TimeoutException e) {
            Driver.getDriver().get(homeUrl);
            Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".container")));
        }
    }
}
