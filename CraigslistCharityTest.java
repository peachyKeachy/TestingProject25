import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.openqa.selenium.Keys.BACK_SPACE;
import static org.openqa.selenium.Keys.CONTROL;


public class CraigslistCharityTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testCgChButton() throws InterruptedException {
        driver.get("https://fortmyers.craigslist.org/");

        Thread.sleep(2000);

        WebElement link = driver.findElement(By.linkText("craigslist charitable"));
        link.click();

        Thread.sleep(2000);

    }

    @Test(priority = 2)
    public void testClickApplyButton() throws InterruptedException {

        WebElement applyBtn = driver.findElement(By.linkText("Apply"));
        applyBtn.click();
        Thread.sleep(2000);

    }

    @Test(priority = 3, dependsOnMethods = "testClickApplyButton")
    public void testClickApplyNowButton() throws InterruptedException {
        // Wait until the button is clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement applyNowBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[span[text()='Apply now']]"))
        );

        applyNowBtn.click();
        Thread.sleep(4000);
    }

    @Test(priority = 4, dependsOnMethods = "testClickApplyNowButton")
    public void testInvalidLogin() throws InterruptedException {
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys("invalid@example.com");
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("wrongpassword");
        Thread.sleep(2000);

        WebElement loginBtn = driver.findElement(By.xpath("//button[span[text()='Sign in']]"));
        loginBtn.click();
        Thread.sleep(2000);
    }


    @Test(priority = 5, dependsOnMethods = "testInvalidLogin")
    public void testValidLogin() throws InterruptedException {


        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys(CONTROL + "a", BACK_SPACE);
        Thread.sleep(2000);
        emailField.sendKeys("softwaretestingfgcu@gmail.com");
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(CONTROL + "a", BACK_SPACE);
        Thread.sleep(2000);
        passwordField.sendKeys("Craiglist@2025");
        Thread.sleep(2000);

        WebElement loginBtn = driver.findElement(By.xpath("//button[span[text()='Sign in']]"));
        loginBtn.click();
        Thread.sleep(4000);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

