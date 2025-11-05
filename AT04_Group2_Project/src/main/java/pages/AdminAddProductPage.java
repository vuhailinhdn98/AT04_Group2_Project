package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import utils.Driver;

public class AdminAddProductPage extends BasePage {

    private final By nameInput = By.name("name");
    private final By priceInput = By.name("price");
    private final By qualityInput = By.name("quality");
    private final By saleInput = By.name("sale");
    private final By manufacturersDropdown = By.name("manufactures");
    private final By imageInput = By.name("image");
    private final By saveButton = By.xpath("//button[@type='submit' and contains(@class, 'btn-success')]");

    public AdminAddProductPage enterProductName(String name) {
        type(nameInput, name);
        return this;
    }

    public AdminAddProductPage enterPrice(String price) {
        type(priceInput, price);
        return this;
    }

    public AdminAddProductPage enterQuality(String quality) {
        type(qualityInput, quality);
        return this;
    }

    public AdminAddProductPage enterSale(String sale) {
        type(saleInput, sale);
        return this;
    }

    public AdminAddProductPage selectManufacturerByValue(String manufacturer) {
        new Select(find(manufacturersDropdown)).selectByValue(manufacturer);
        return this;
    }

    public AdminAddProductPage uploadImage(String filePath) {
        find(imageInput).sendKeys(filePath);
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
        click(saveButton);
    }
}
