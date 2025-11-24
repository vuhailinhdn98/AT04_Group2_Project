package testdata;

import com.github.javafaker.Faker;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ProductDataTest{
    private static final Faker FAKER = new Faker();
    private static final List<String> VALID_MANUFACTURERS = Arrays.asList(
            "Iphone",
            "Samsung",
            "Nokia",
            "HTC",
            "Sony",
            "Asus",
            "Apple",
            "Xiaomi"
    );// getoptions()
    private static final List<String> IMAGE_FILES = Arrays.asList(
            "imagetest1.png",
            "imagetest2.png",
            "imagetest3.png",
            "imagetest4.png",
            "imagetest5.png"
    );
    private String name;
    private long price;
    private int quality;
    private String sale;
    private String manufacturerValue;
    private String imagePath;
    private String specification;

    public ProductDataTest() {
        this.name = FAKER.commerce().productName();
        this.price = FAKER.number().numberBetween(1, 5_000) * 1000L;
        this.quality = randomQuality();
        this.sale = String.valueOf(FAKER.number().numberBetween(0, 100));
        this.manufacturerValue = getRandomManufacturer();
        this.imagePath = getRandomImagePath();
        this.specification = FAKER.lorem().sentence(100);
    }

    private static int randomQuality() {
        return FAKER.number().numberBetween(1, 10);
    }

    private static String getRandomManufacturer() {
        return VALID_MANUFACTURERS.get(
                FAKER.number().numberBetween(0, VALID_MANUFACTURERS.size() - 1)
        );
    }

    private static String getRandomImagePath() {
        String imageFile = IMAGE_FILES.get(
                FAKER.number().numberBetween(0, IMAGE_FILES.size() - 1)
        );
        //FAKER.options().nextElement(IMAGE_FILES);
        return Paths.get("src", "test", "resources", imageFile).toAbsolutePath().toString();
    }
    // Getter
    public String getName() { return name; }
    public long getPrice() { return price; }
    public int getQuality() { return quality; }
    public String getSale() { return sale; }
    public String getManufacturerValue() { return manufacturerValue; }
    public String getImagePath() { return imagePath; }
    public String getSpecification() { return specification; }

    public ProductDataTest setQuality(int quality) {
        this.quality = quality;
        return this;
    }
    public ProductDataTest setQualityRandom() {
        Faker faker = new Faker();
        this.quality = faker.number().numberBetween(1, 10);
        return this;
    }
}
