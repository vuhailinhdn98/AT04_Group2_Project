package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import testdata.ProductDataTest;
import utils.Driver;

import java.util.List;
import java.util.stream.Collectors;

public class AdminAddProductPage extends AdminNavigationMenu {

    private final By nameInputLocator = By.name("name");
    private final By priceInputLocator = By.name("price");
    private final By qualityInputLocator = By.name("quality");
    private final By saleInputLocator = By.name("sale");
    private final By manufacturersDropdownLocator = By.name("manufactures");
    private final By imageInputLocator = By.name("image");
    private final By saveButtonLocator = By.xpath("//button[@type='submit' and contains(@class, 'btn-success')]");

    public List<String> getAllManufacturers() {
        return new Select(find(manufacturersDropdownLocator))
                .getOptions()
                .stream()
                .map(o -> o.getText().trim())
                .filter(t -> !"Select".equalsIgnoreCase(t))
                .collect(Collectors.toList());
    }


    public AdminAddProductPage enterProductName(String name) {
        type(nameInputLocator, name);
        return this;
    }

    public AdminAddProductPage enterPrice(long price) {
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

    public AdminAddProductPage selectManufacturerByValue(String manufacturerValue) {
        new Select(find(manufacturersDropdownLocator)).selectByValue(manufacturerValue);
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


    public void addProduct(ProductDataTest product) {
        enterProductName(product.getName());
        enterPrice(product.getPrice());
        enterQuality(product.getQuality());
        enterSale(product.getSale());
        selectManufacturerByValue(product.getManufacturer());
        uploadImage(product.getImagePath());
        enterSpecification(product.getSpecification());
        click(saveButtonLocator);
    }
}

