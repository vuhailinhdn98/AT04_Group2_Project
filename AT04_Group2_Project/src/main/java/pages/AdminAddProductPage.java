package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import utils.Driver;

public class AdminAddProductPage extends BasePage {

    private final By nameInputLocator = By.name("name");
    private final By priceInputLocator = By.name("price");
    private final By qualityInputLocator = By.name("quality");
    private final By saleInputLocator = By.name("sale");
    private final By manufacturersDropdownLocator = By.name("manufactures");
    private final By imageInputLocator = By.name("image");
    private final By saveButtonLocator = By.xpath("//button[@type='submit' and contains(@class, 'btn-success')]");

    public AdminAddProductPage enterProductName(String name) {
        type(nameInputLocator, name);
        return this;
    }

    public AdminAddProductPage enterPrice(String price) {
        type(priceInputLocator, price);
        return this;
    }

    public AdminAddProductPage enterQuality(String quality) {
        type(qualityInputLocator, quality);
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
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("CKEDITOR.instances['specification'].setData(arguments[0]);", specification);
        return this;
    }

    public void addProduct(String name, String price, String quality,
                           String sale, String manufacturer,
                           String imagePath, String specification) {
        if (name != null) enterProductName(name);
        if (price != null) enterPrice(price);
        if (quality != null) enterQuality(quality);
        if (sale != null) enterSale(sale);
        if (manufacturer != null) selectManufacturerByValue(manufacturer);
        if (imagePath != null) uploadImage(imagePath);
        if (specification != null) enterSpecification(specification);
        click(saveButtonLocator);
    }
}
