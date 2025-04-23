import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.Assert;

public class CraigslistLoginTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://accounts.craigslist.org/login");
        Thread.sleep(2000);
    }

    // test correctness of page title
    @Test (priority = 1)
    public void testPageTitle() throws InterruptedException {
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("craigslist"), "Page title does not contain 'craigslist'");
        Thread.sleep(3000);
    }

    // test email box is there
    @Test (priority = 2)
    public void testEmailFieldIsPresent() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("inputEmailHandle"));
        Assert.assertTrue(emailField.isDisplayed(), "Email field is not displayed");
        Thread.sleep(3000);
    }

    // test password box is there
    @Test (priority = 3)
    public void testPasswordFieldIsPresent() throws InterruptedException {
        WebElement passwordField = driver.findElement(By.id("inputPassword"));
        Assert.assertTrue(passwordField.isDisplayed(), "Password field is not displayed");
        Thread.sleep(3000);
    }

    // test a correct email and password
    @Test (priority = 4)
    public void testCorrectLoginAttempt() throws InterruptedException {
        driver.findElement(By.id("inputEmailHandle")).sendKeys("testemailexamplecariglist@gmail.com");
        Thread.sleep(3000);
        driver.findElement(By.id("inputPassword")).sendKeys("PasswordCraiglist@testNG");
        Thread.sleep(3000);
        driver.findElement(By.id("login")).click();
        Thread.sleep(3000);
        //log out
        driver.findElement(By.xpath("//*[@id=\"ef\"]/a[1]")).click();
    }

    // test a wrong email and password
    @Test (priority = 5)
    public void testWrongLoginAttempt() throws InterruptedException {
        driver.findElement(By.id("inputEmailHandle")).sendKeys("test@example.com");
        Thread.sleep(3000);
        driver.findElement(By.id("inputPassword")).sendKeys("wrongpassword");
        Thread.sleep(3000);
        driver.findElement(By.id("login")).click();
        Thread.sleep(3000);
    }

    // reset password/ Forgot password link
    @Test (priority = 6)
    public void testForgotPassword() throws InterruptedException {
        driver.findElement(By.xpath("/html/body/section/section/div/div[1]/form/div[2]/label/a")).click();
        Thread.sleep(2000);
        //account help link
        driver.findElement(By.xpath("/html/body/section/section/div/form/div[2]/a")).click();
        Thread.sleep(2000);
    }

    //close driver
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
