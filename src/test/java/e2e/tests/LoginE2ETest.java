package e2e.tests;

import e2e.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LoginE2ETest - Kiem tra cac truong hop dang nhap that bai
 * Trang: https://vanphongdientu.utc.edu.vn/Login
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Kiem tra chuc nang Dang nhap - Truong hop that bai")
public class LoginE2ETest {

    private static WebDriver driver;
    private static LoginPage loginPage;

    @BeforeAll
    static void setUp() {
        File driverFile = new File("drivers/msedgedriver.exe");
        if (driverFile.exists()) {
            System.setProperty("webdriver.edge.driver", driverFile.getAbsolutePath());
        }

        EdgeOptions options = new EdgeOptions();
        String headless = System.getProperty("headless", "true");
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");

        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @BeforeEach
    void navigateToLoginPage() {
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ==========================================
    // TC01: Dang nhap voi username va password rong
    // ==========================================
    @Test
    @Order(1)
    @DisplayName("TC01 - Dang nhap voi username va password deu de trong")
    void testLoginWithEmptyUsernameAndPassword() {
        loginPage.clickLogin();

        // Sau khi nhan dang nhap voi truong rong, phai van o trang Login
        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap khi ca hai truong deu rong");
    }

    // ==========================================
    // TC02: Dang nhap voi username rong, co password
    // ==========================================
    @Test
    @Order(2)
    @DisplayName("TC02 - Dang nhap voi username de trong, chi nhap password")
    void testLoginWithEmptyUsername() {
        loginPage.enterPassword("password123");
        loginPage.clickLogin();

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap khi username rong");
    }

    // ==========================================
    // TC03: Dang nhap voi password rong, co username
    // ==========================================
    @Test
    @Order(3)
    @DisplayName("TC03 - Dang nhap voi password de trong, chi nhap username")
    void testLoginWithEmptyPassword() {
        loginPage.enterUsername("testuser");
        loginPage.clickLogin();

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap khi password rong");
    }

    // ==========================================
    // TC04: Dang nhap voi tai khoan khong ton tai
    // ==========================================
    @Test
    @Order(4)
    @DisplayName("TC04 - Dang nhap voi tai khoan khong ton tai trong he thong")
    void testLoginWithNonExistentAccount() {
        loginPage.loginExpectingFailure("taikhoankhongtontai999", "matkhau123");

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap khi tai khoan khong ton tai");
    }

    // ==========================================
    // TC05: Dang nhap voi mat khau sai
    // ==========================================
    @Test
    @Order(5)
    @DisplayName("TC05 - Dang nhap voi username hop le nhung mat khau sai")
    void testLoginWithWrongPassword() {
        loginPage.loginExpectingFailure("admin", "saimatkhau123456");

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap khi mat khau sai");
    }
}




