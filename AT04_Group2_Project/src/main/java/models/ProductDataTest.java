package models;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.nio.file.Paths;

public class ProductDataTest{
    private String name;
    private long price;
    private int quality;
    private String sale;
    private String manufacturerValue;
    private String imagePath;
    private String specification;

    public ProductDataTest() {
        Faker faker = new Faker();
        this.name = faker.commerce().productName();

        this.price = (long) faker.number().numberBetween(1, 5_000_000);

        this.quality = faker.number().numberBetween(1, 10);

        int saleInt = faker.number().numberBetween(0, 100);

        this.sale = String.valueOf(saleInt);

        List<String> validManufacturers = Arrays.asList("1", "2", "4", "6", "7", "8", "11", "12");
        this.manufacturerValue = validManufacturers.get(
                faker.number().numberBetween(0, validManufacturers.size())
        );
        this.imagePath = Paths.get("src", "test", "resources", "chipktest.png").toAbsolutePath().toString();

        this.specification = faker.lorem().sentence(100);
    }

    // Getter
    public String getName() { return name; }
    public long getPrice() { return price; }
    public int getQuality() { return quality; }
    public String getSale() { return sale; }
    public String getManufacturerValue() { return manufacturerValue; }
    public String getImagePath() { return imagePath; }
    public String getSpecification() { return specification; }

}
