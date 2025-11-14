package pages;

import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MobileProductListPage extends BasePage {
    private final By viewByDropdownLocator = By.cssSelector("button.dropdown-toggle");
    private final By priceLowToHighLocator = By.linkText("Giá từ thấp đến cao");
    private final By productItemsLocator = By.cssSelector(".thumbnail");
    private final By productNameLocator = By.cssSelector(".products-content-label p:first-of-type a");
    private final By productPriceLocator = By.cssSelector("p.text-danger");

    public void openViewByDropdown() {
        click(viewByDropdownLocator);
    }

    public void sortByPriceLowToHigh() {
        openViewByDropdown();
        click(priceLowToHighLocator);
    }

    public List<Long> getAllPrices() {
        List<Long> prices = new ArrayList<>();
        List<WebElement> priceElements = getElements(productPriceLocator);

        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText();
            long price = parsePrice(priceText);
            prices.add(price);
        }

        log.info("Extracted {} prices", prices.size());
        return prices;
    }

    public List<String> getAllProductNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> nameElements = getElements(productNameLocator);

        for (WebElement nameElement : nameElements) {
            names.add(nameElement.getText().trim());
        }

        log.info("Extracted {} product names", names.size());
        return names;
    }

    public boolean isPriceSortedAscending() {
        List<Long> prices = getAllPrices();
        List<Long> sortedPrices = prices.stream()
                .sorted()
                .collect(Collectors.toList());

        boolean isSorted = prices.equals(sortedPrices);

        log.info("Prices sorted ascending: {}", isSorted);
        return isSorted;
    }
    }
}

