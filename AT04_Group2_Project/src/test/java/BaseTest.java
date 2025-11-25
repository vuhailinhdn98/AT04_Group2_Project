import testdata.ProductDataTest;
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
import testdata.TestAccount;
import utils.Driver;

import java.lang.reflect.Method;

public class BaseTest extends TestAccount {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected HomePage homePage = new HomePage();
    protected LoginModal loginModal = new LoginModal();
    protected CheckoutModal checkoutModal = new CheckoutModal();
    protected UserPage userPage = new UserPage();
    protected OrderConfirmationModal orderConfirmationModal = new OrderConfirmationModal();
    protected ProductDataTest productDataTest = new ProductDataTest();
    protected ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    protected CartModal cartModal = new CartModal();
    protected MobileProductListPage mobileProductListPage = new MobileProductListPage();

    protected AdminDashboardPage adminDashboardPage = new AdminDashboardPage();
    protected AdminProductListPage adminProductListPage = new AdminProductListPage();
    protected AdminProductsDetailsPage adminProductsDetailsPage = new AdminProductsDetailsPage();
    protected AdminAddProductPage addProductPage = new AdminAddProductPage();
    protected AdminOrderListPage adminOrderListPage = new AdminOrderListPage();

    protected SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void setUp(Method method) {
        MDC.put("test", method.getName());              // pattern [%X{test}]
        log.info("=== START {} ===", method.getName());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
//        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        Driver.setDriver(new ChromeDriver(options));

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
