import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import pages.*;

import utils.Driver;

import java.lang.reflect.Method;

public class BaseTest {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected HomePage homePage = new HomePage();
    protected LoginModal loginModal = new LoginModal();
    protected ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    protected CartModal cartModal = new CartModal();
    protected AdminNavigationMenu adminMenu = new AdminNavigationMenu();
    protected AdminProductListPage adminProductList = new AdminProductListPage();
    protected AdminAddProductPage addProductPage = new AdminAddProductPage();
    protected SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void setUp(Method method) {
        MDC.put("test", method.getName());              // pattern [%X{test}]
        log.info("=== START {} ===", method.getName());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
//        options.addArguments("--headless");
        Driver.setDriver(new ChromeDriver(options));
        Driver.getDriver().manage().window().maximize();

        log.info("1. Go to Home page");
        Driver.getDriver().get("http://14.176.232.213/mobilevn/");
    }

    @AfterMethod
    public void cleanUp(ITestResult testResult) {
        log.info("=== END {} - {} ===", testResult.getMethod().getMethodName(), testResult.isSuccess() ? "PASS" : "FAIL");
        Driver.getDriver().quit();
        MDC.clear();
    }
}
