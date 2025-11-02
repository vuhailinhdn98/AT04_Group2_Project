import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.Driver;

public class BaseTest {
    HomePage homePage = new HomePage();
    ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    CartModal cartModal = new CartModal();

    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        Driver.setDriver(new ChromeDriver(options));
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().get("http://14.176.232.213/mobilevn/");
    }

    @AfterMethod
    public void cleanUp() {
        Driver.getDriver().quit();
    }
}
