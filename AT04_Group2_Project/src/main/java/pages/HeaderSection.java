package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HeaderSection extends BasePage {
    private final By loginButtonLocator = By.cssSelector(".top-header [data-target='#login']");
    private final By accountDropdownLoggedInLocator = By.xpath("//div[contains(@class,'top-header')]//button[contains(@class,'dropdown-toggle')][not(contains(normalize-space(.),'Đăng nhập'))]");
    private final By accountNameLocator = By.xpath("//div[contains(@class,'top-header')]//button[contains(@class,'dropdown-toggle')]//span[contains(@class,'header-login') and not(contains(@class,'caret'))][normalize-space()]");
    private final By logoutBtnLocator = By.xpath("//div[contains(@class,'login-top')]//a[contains(@class,'logout')]");
    private final By adminControlPanelLinkLocator = By.xpath("//a[@href='admin/admin.php' and contains(text(),'Admin Control Panel')]");
    private final By headerSearchBoxLocator = By.cssSelector("input[data-target='#search_modal'][type='search']");
    private final By popupSearchInputLocator = By.cssSelector("input#search[name='search']");
    private final By searchRowLocator = By.cssSelector("#search_show .row.search_main");

    private final By rowNameLinkLocator = By.cssSelector(".search_col label a");
    private final By rowPriceSpanLocator = By.cssSelector(".search_col .text-danger");

    //modal
    private final By cartModalContainerLocator = By.cssSelector(".modal-content #view_cart");
    private final By checkoutModalContainerLocator = By.cssSelector(".modal-content #order_modal");

    public void openLoginModal() {
        find(loginButtonLocator).click();
    }

    public void waitUntilLoggedIn() {
        Driver.getWebDriverWait().until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(accountNameLocator), ExpectedConditions.invisibilityOfElementLocated(loginButtonLocator)));
    }

    public boolean isLoggedIn() {
        return !getElements(accountDropdownLoggedInLocator).isEmpty() || !getElements(logoutBtnLocator).isEmpty();
    }

    public String getAccountNameIfPresent() {
        List<WebElement> accountName = getElements(accountNameLocator);
        return accountName.get(0).getAttribute("textContent").trim();
    }

    public boolean isCartModalVisible() {
        try {
            waitToBeVisible(cartModalContainerLocator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //checkout modal
    public void waitCheckoutModalVisible() {
            waitToBeVisible(checkoutModalContainerLocator);
    }

    protected WebElement getCheckoutModalElements() {
        return find(checkoutModalContainerLocator);
    }

    public void openAccountDropdown() {
        click(accountNameLocator);
    }

    public void goToAdminControlPanel() {
        find(accountDropdownLoggedInLocator).click();
        find(adminControlPanelLinkLocator).click();
        ArrayList<String> tabs = new ArrayList<>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(tabs.get(tabs.size() - 1));
    }

    public void openHomePage() {
        String homeUrl = "http://14.176.232.213/mobilevn/index.php";
        Driver.getDriver().get(homeUrl);
    }

    public void openSearchModal() {
        WebElement searchBox = Driver.getWebDriverWait().until(
                ExpectedConditions.elementToBeClickable(headerSearchBoxLocator)
        );
        searchBox.click();
    }

    public void searchProduct(String keyword) {
        WebElement input = Driver.getWebDriverWait().until(
                ExpectedConditions.elementToBeClickable(popupSearchInputLocator)
        );
        input.sendKeys(keyword);
    }

    public List<String> getSearchResultByProductNames() {
        Driver.getWebDriverWait().until(
                ExpectedConditions.presenceOfElementLocated(rowNameLinkLocator)
        );

        return Driver.getDriver().findElements(rowNameLinkLocator).stream()
                .map(el -> el.getText().trim())
                .collect(Collectors.toList());
    }

    public boolean hasProductWithKeyword(String keyword) {
        List<String> names = getSearchResultByProductNames();
        return names.stream()
                .anyMatch(name -> name.toLowerCase().contains(keyword.toLowerCase()));
    }
}
