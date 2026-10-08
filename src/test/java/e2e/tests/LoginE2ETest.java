package e2e.tests;

import e2e.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    // ==========================================
    // TC06: Dang nhap voi ca username va password sai
    // ==========================================
    @Test
    @Order(6)
    @DisplayName("TC06 - Dang nhap voi ca username va password deu sai")
    void testLoginWithBothWrongCredentials() {
        loginPage.loginExpectingFailure("usersai123", "passsai456");

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap khi ca hai deu sai");
    }

    // ==========================================
    // TC07: Dang nhap voi ky tu dac biet trong username
    // ==========================================
    @ParameterizedTest
    @Order(7)
    @DisplayName("TC07 - Dang nhap voi ky tu dac biet trong username")
    @ValueSource(strings = {
            "!@#$%^&*()",
            "<>?/\\|{}[]",
            "user@#$name",
            "user name!",
            "~`+=;:'\""
    })
    void testLoginWithSpecialCharactersInUsername(String specialUsername) {
        loginPage.loginExpectingFailure(specialUsername, "password123");

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap voi ky tu dac biet: " + specialUsername);
    }

    // ==========================================
    // TC08: Dang nhap voi SQL Injection
    // ==========================================
    @ParameterizedTest
    @Order(8)
    @DisplayName("TC08 - Dang nhap voi SQL Injection trong username")
    @ValueSource(strings = {
            "' OR '1'='1",
            "' OR '1'='1' --",
            "' OR '1'='1' /*",
            "admin'--",
            "' UNION SELECT * FROM users --",
            "1'; DROP TABLE users; --",
            "' OR 1=1 --"
    })
    void testLoginWithSQLInjection(String sqlInjection) {
        loginPage.loginExpectingFailure(sqlInjection, "password123");

        assertTrue(loginPage.isOnLoginPage(),
                "He thong phai chong duoc SQL Injection: " + sqlInjection);
    }

    // ==========================================
    // TC09: Dang nhap voi XSS (Cross-Site Scripting)
    // ==========================================
    @ParameterizedTest
    @Order(9)
    @DisplayName("TC09 - Dang nhap voi XSS trong username")
    @ValueSource(strings = {
            "<script>alert('XSS')</script>",
            "<img src=x onerror=alert('XSS')>",
            "<svg/onload=alert('XSS')>",
            "javascript:alert('XSS')",
            "<iframe src='javascript:alert(1)'>"
    })
    void testLoginWithXSSInUsername(String xssPayload) {
        loginPage.loginExpectingFailure(xssPayload, "password123");

        // Kiem tra khong co alert XSS xuat hien
        assertFalse(loginPage.isAlertPresent(),
                "He thong phai chong duoc XSS: " + xssPayload);

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap sau khi nhap XSS");
    }

    // ==========================================
    // TC10: Dang nhap voi khoang trang (spaces)
    // ==========================================
    @Test
    @Order(10)
    @DisplayName("TC10 - Dang nhap voi username va password chi la khoang trang")
    void testLoginWithOnlySpaces() {
        loginPage.loginExpectingFailure("     ", "     ");

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap khi chi nhap khoang trang");
    }

    // ==========================================
    // TC11: Dang nhap voi chuoi qua dai
    // ==========================================
    @Test
    @Order(11)
    @DisplayName("TC11 - Dang nhap voi username va password qua dai (500 ky tu)")
    void testLoginWithExtremelyLongInput() {
        String longString = "a".repeat(500);
        loginPage.loginExpectingFailure(longString, longString);

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap khi nhap chuoi qua dai");
    }

    // ==========================================
    // TC12: Dang nhap voi ky tu Unicode / Tieng Viet
    // ==========================================
    @Test
    @Order(12)
    @DisplayName("TC12 - Dang nhap voi ky tu Unicode va tieng Viet co dau")
    void testLoginWithUnicodeCharacters() {
        loginPage.loginExpectingFailure("nguyễnvănA", "mậtkhẩu123");

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap khi nhap ky tu Unicode");
    }

    // ==========================================
    // TC13: Dang nhap voi username la so
    // ==========================================
    @Test
    @Order(13)
    @DisplayName("TC13 - Dang nhap voi username chi la so")
    void testLoginWithNumericUsername() {
        loginPage.loginExpectingFailure("123456789", "password123");

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap voi username chi la so");
    }

    // ==========================================
    // TC14: Dang nhap nhieu lan sai lien tuc (Brute Force)
    // ==========================================
    @Test
    @Order(14)
    @DisplayName("TC14 - Dang nhap sai nhieu lan lien tuc (kiem tra chong brute force)")
    void testBruteForceProtection() {
        for (int i = 1; i <= 5; i++) {
            loginPage.open();
            loginPage.loginExpectingFailure("admin", "wrongpass" + i);
        }

        // Sau nhieu lan dang nhap sai, van phai o trang login
        assertTrue(loginPage.isOnLoginPage(),
                "He thong phai van hoat dong sau nhieu lan dang nhap sai");
    }

    // ==========================================
    // TC15: Kiem tra giao dien trang Login
    // ==========================================
    @Test
    @Order(15)
    @DisplayName("TC15 - Kiem tra cac thanh phan giao dien trang dang nhap")
    void testLoginPageUIElements() {
        assertTrue(loginPage.isUsernameFieldDisplayed(),
                "Truong username phai hien thi");
        assertTrue(loginPage.isPasswordFieldDisplayed(),
                "Truong password phai hien thi");
        assertTrue(loginPage.isLoginButtonDisplayed(),
                "Nut dang nhap phai hien thi");

        assertEquals("Tên đăng nhập", loginPage.getUsernamePlaceholder(),
                "Placeholder cua username phai la 'Tên đăng nhập'");
        assertEquals("Mật khẩu", loginPage.getPasswordPlaceholder(),
                "Placeholder cua password phai la 'Mật khẩu'");
    }

    // ==========================================
    // TC16: Kiem tra title trang Login
    // ==========================================
    @Test
    @Order(16)
    @DisplayName("TC16 - Kiem tra title cua trang dang nhap")
    void testLoginPageTitle() {
        assertEquals("Đăng nhập", loginPage.getPageTitle(),
                "Title cua trang phai la 'Đăng nhập'");
    }

    // ==========================================
    // TC17: Dang nhap voi username co khoang trang dau/cuoi
    // ==========================================
    @Test
    @Order(17)
    @DisplayName("TC17 - Dang nhap voi username co khoang trang o dau va cuoi")
    void testLoginWithLeadingTrailingSpaces() {
        loginPage.loginExpectingFailure("  admin  ", "password123");

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap voi username co khoang trang thua");
    }

    // ==========================================
    // TC18: Dang nhap voi password co khoang trang dau/cuoi
    // ==========================================
    @Test
    @Order(18)
    @DisplayName("TC18 - Dang nhap voi password co khoang trang o dau va cuoi")
    void testLoginWithPasswordLeadingTrailingSpaces() {
        loginPage.loginExpectingFailure("admin", "  password123  ");

        assertTrue(loginPage.isOnLoginPage(),
                "Phai van o trang dang nhap voi password co khoang trang thua");
    }

    // ==========================================
    // TC19: Kiem tra URL sau khi dang nhap that bai
    // ==========================================
    @Test
    @Order(19)
    @DisplayName("TC19 - Kiem tra URL khong thay doi sau khi dang nhap that bai")
    void testURLAfterFailedLogin() {
        loginPage.loginExpectingFailure("wronguser", "wrongpass");

        String currentUrl = loginPage.getCurrentUrl();
        assertTrue(currentUrl.contains("Login") || currentUrl.contains("login"),
                "URL phai chua 'Login' sau khi dang nhap that bai, URL hien tai: " + currentUrl);
    }
}












