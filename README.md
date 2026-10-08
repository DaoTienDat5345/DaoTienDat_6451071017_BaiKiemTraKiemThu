# Bài Kiểm Tra Kiểm Thử Phần Mềm - Automation Testing

**Dự án:** Kiểm thử tự động tính năng Đăng nhập (End-to-End Negative Testing)  
**Website kiểm thử:** [Văn phòng điện tử - Trường Đại học Giao thông Vận tải](https://vanphongdientu.utc.edu.vn/Login)  
**Sinh viên thực hiện:** Đào Tiến Đạt  
**Mã sinh viên:** 6451071017  

---

## 📌 1. Giới thiệu dự án

Dự án này xây dựng kịch bản kiểm thử tự động (Automation Test) cho trang Đăng nhập hệ thống Văn phòng điện tử UTC, tập trung vào **các trường hợp đăng nhập thất bại (Negative Testing)** nhằm đảm bảo tính bảo mật, tính toàn vẹn dữ liệu và độ ổn định của hệ thống trước các thao tác nhập liệu không hợp lệ hoặc các cuộc tấn công thông dụng.

### Công nghệ sử dụng:
- **Ngôn ngữ:** Java 17+
- **Kiểm thử tự động:** Selenium WebDriver (v4.27.0)
- **Framework kiểm thử:** JUnit 5 (JUnit Jupiter v5.11.4)
- **Công cụ quản lý & Build:** Apache Maven (sử dụng sẵn Maven Wrapper `mvnw.cmd`)
- **Mô hình thiết kế:** Page Object Model (POM)
- **Trình duyệt thực thi:** Microsoft Edge (sử dụng Microsoft Edge WebDriver)

---

## 📁 2. Cấu trúc thư mục

```text
DaoTienDat_6451071017_BaiKiemTraKiemThu/
│
├── .mvn/                     # Cấu hình Maven Wrapper
├── drivers/                  # Chứa msedgedriver.exe (khớp phiên bản Microsoft Edge)
├── src/test/java/e2e/        # Mã nguồn kiểm thử chuẩn Maven
│   ├── base/
│   │   └── BasePage.java     # Lớp cha BasePage đóng gói các thao tác Selenium cơ bản
│   ├── pages/
│   │   └── LoginPage.java    # Page Object đại diện cho trang Đăng nhập UTC
│   └── tests/
│       └── LoginE2ETest.java # Kịch bản kiểm thử các ca đăng nhập thất bại
├── base/                     # File BasePage dự phòng
├── pages/                    # File LoginPage dự phòng
├── tests/                    # Thư mục tests kịch bản
├── pom.xml                   # Cấu hình dependencies và plugins của Maven
├── mvnw.cmd                  # Maven Wrapper thực thi lệnh trên Windows
└── README.md                 # Tài liệu hướng dẫn dự án
```

---

## 📋 3. Danh mục các ca kiểm thử (Negative Test Cases)

| Mã TC | Tên ca kiểm thử | Mô tả chi tiết | Phân loại |
|:-----:|:---|:---|:---:|
| **TC01** | Bỏ trống Username & Password | Nhấn Đăng nhập khi không nhập cả 2 trường | Validation |
| **TC02** | Bỏ trống Username | Chỉ nhập Password, để trống Username | Validation |
| **TC03** | Bỏ trống Password | Chỉ nhập Username, để trống Password | Validation |
| **TC04** | Tài khoản không tồn tại | Nhập Username không có trong hệ thống | Functional |
| **TC05** | Mật khẩu sai | Nhập đúng định dạng Username nhưng sai mật khẩu | Functional |
| **TC06** | Cả hai đều sai | Cả tài khoản và mật khẩu đều không đúng | Functional |
| **TC07** | Ký tự đặc biệt trong Username | Thử nghiệm với các chuỗi: `!@#$%^&*()`, `<>?/\|{}[]`,... | Security |
| **TC08** | SQL Injection | Thử nghiệm các payload: `' OR '1'='1`, `admin'--`,... | Security |
| **TC09** | Tấn công XSS | Thử nghiệm script: `<script>alert('XSS')</script>`, `<img>`,... | Security |
| **TC10** | Chỉ chứa khoảng trắng | Nhập toàn khoảng trắng (spaces) vào các trường | Validation |
| **TC11** | Chuỗi quá dài (Boundary) | Nhập chuỗi 500 ký tự vào Username và Password | Boundary |
| **TC12** | Ký tự Unicode / Tiếng Việt | Nhập ký tự có dấu (`nguyễnvănA`, `mậtkhẩu123`) | Boundary |
| **TC13** | Username chỉ toàn số | Kiểm tra định dạng tài khoản chỉ gồm chữ số | Boundary |
| **TC14** | Brute Force Protection | Thử đăng nhập sai liên tiếp nhiều lần | Security |
| **TC15** | Kiểm tra giao diện UI | Kiểm tra sự hiển thị của các input và nút bấm | UI |
| **TC16** | Kiểm tra Page Title | Xác minh tiêu đề trang là "Đăng nhập" | UI |
| **TC17** | Khoảng trắng đầu/cuối Username | Nhập `"  admin  "` để kiểm tra xử lý khoảng trắng | Boundary |
| **TC18** | Khoảng trắng đầu/cuối Password | Nhập `"  password123  "` | Boundary |
| **TC19** | Kiểm tra URL sau thất bại | Đảm bảo URL vẫn giữ ở trang đăng nhập `/Login` | Functional |
| **TC20** | Ký tự Tab và Escape | Nhập ký tự đặc biệt tab và chuỗi escape | Boundary |

---

## 🚀 4. Yêu cầu môi trường

Để chạy dự án, máy tính cần cài đặt:
1. **Java Development Kit (JDK):** Phiên bản 17 trở lên.  
   - Kiểm tra bằng lệnh: `java -version`
2. **Trình duyệt Microsoft Edge:** Đã được cài đặt sẵn trên Windows.
3. **Git:** Để clone dự án về máy.

---

## 🛠️ 5. Hướng dẫn chạy kiểm thử chi tiết

Mở terminal (PowerShell hoặc Command Prompt) tại thư mục gốc của dự án:

### Cách 1: Chạy toàn bộ Test Cases chế độ ngầm (Headless)
> Chế độ này chạy nhanh nhất, không bật giao diện trình duyệt để tránh chiếm màn hình làm việc:
```powershell
.\mvnw.cmd test
```

### Cách 2: Chạy có hiển thị giao diện trình duyệt (UI Mode)
> Trình duyệt Edge sẽ tự động bật lên để bạn quan sát từng thao tác gõ phím, nhấn nút:
```powershell
.\mvnw.cmd test -Dheadless=false
```

### Cách 3: Chạy một Test Case cụ thể

- **Chạy Test Case 1 (Để trống Username & Password):**
  ```powershell
  .\mvnw.cmd test -Dtest=LoginE2ETest#testLoginWithEmptyUsernameAndPassword
  ```

- **Chạy Test Case 2 (Để trống Username):**
  ```powershell
  .\mvnw.cmd test -Dtest=LoginE2ETest#testLoginWithEmptyUsername
  ```

- **Chạy Test Case 8 (SQL Injection) và hiển thị trình duyệt:**
  ```powershell
  .\mvnw.cmd test -Dtest=LoginE2ETest#testLoginWithSQLInjection -Dheadless=false
  ```

---

## 📊 6. Xem báo cáo kết quả kiểm thử

Sau khi chạy xong lệnh `test`, kết quả chi tiết được tự động lưu tại:
- Báo cáo định dạng Text: `target/surefire-reports/e2e.tests.LoginE2ETest.txt`
- Báo cáo định dạng XML: `target/surefire-reports/TEST-e2e.tests.LoginE2ETest.xml`
- Hoặc quan sát trực tiếp dòng tóm tắt trên Terminal:
  ```text
  [INFO] Tests run: ..., Failures: 0, Errors: 0, Skipped: 0
  [INFO] BUILD SUCCESS
  ```
