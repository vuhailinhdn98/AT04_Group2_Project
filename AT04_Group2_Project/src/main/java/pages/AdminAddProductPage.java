package pages;

import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utils.Driver;

public class AdminAddProductPage extends BasePage {

    private final By nameInputLocator = By.name("name");
    private final By priceInputLocator = By.name("price");
    private final By qualityInputLocator = By.name("quality");
    private final By saleInputLocator = By.name("sale");
    private final By manufacturersDropdownLocator = By.name("manufactures");
    private final By imageInputLocator = By.name("image");
    private final By specificationLocator = By.cssSelector("iframe.cke_wysiwyg_frame[title*='specification']");

    private final By saveButtonLocator = By.xpath("//button[@type='submit' and contains(@class, 'btn-success')]");


    public AdminAddProductPage enterProductName(String name) {
        type(nameInputLocator, name);
        return this;
    }

    public AdminAddProductPage enterPrice(int price) {
        type(priceInputLocator, String.valueOf(price));
        return this;
    }

    public AdminAddProductPage enterQuality(int quality) {
        type(qualityInputLocator, String.valueOf(quality));
        return this;
    }

    public AdminAddProductPage enterSale(String sale) {
        type(saleInputLocator, sale);
        return this;
    }

    public AdminAddProductPage selectManufacturerByValue(String manufacturer) {
        new Select(find(manufacturersDropdownLocator)).selectByValue(manufacturer);
        return this;
    }

    public AdminAddProductPage uploadImage(String filePath) {
        find(imageInputLocator).sendKeys(filePath);
        return this;
    }

    public AdminAddProductPage enterSpecification(String specification) {
        Driver.getWebDriverWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(specificationLocator));
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("document.body.innerHTML = arguments[0];", specification);
        Driver.getDriver().switchTo().defaultContent();
        return this;
    }


    public void addProduct(String name, int price, int quality, String sale, String manufacturer, String filePath, String specification) {
            enterProductName(name);
            enterPrice(price);
            enterQuality(quality);
            enterSale(sale);
            selectManufacturerByValue(manufacturer);
            uploadImage(filePath);
            enterSpecification(specification);
        click(saveButtonLocator);
    }
}

