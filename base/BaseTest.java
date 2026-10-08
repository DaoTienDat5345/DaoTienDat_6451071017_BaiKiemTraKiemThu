public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator))
            .click();
    }
    
    protected void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions
        .visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }
    
    protected String getText(By locator) {
        return wait.until(ExpectedConditions
        .visibilityOfElementLocated(locator)).getText();
    }
}