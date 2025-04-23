import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;

public class CheckForHelpandAvoidScamsTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://fortmyers.craigslist.org/");
    }

    @BeforeMethod
    public void pauseBetweenTests() throws InterruptedException {
        Thread.sleep(3000);
    }

    @Test(priority = 1)
    public void testClickHelpFaqLegal() throws InterruptedException {
        WebElement helpLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("help, faq, abuse, legal")));
        helpLink.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/about/help"));
        System.out.println("Clicked on 'help, faq, abuse, legal'");
    }

    @Test(priority = 2, dependsOnMethods = "testClickHelpFaqLegal")
    public void testClickLegal() throws InterruptedException {
        WebElement legalLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("legal")));
        legalLink.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/about/help/legal"));
        System.out.println("Clicked on 'legal'");
    }

    @Test(priority = 3, dependsOnMethods = "testClickLegal")
    public void testClickTermsOfUse() throws InterruptedException {
        WebElement termsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("terms of use")));
        termsLink.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/about/terms.of.use"));
        System.out.println("Clicked on 'terms of use'");
    }

    @Test(priority = 4, dependsOnMethods = "testClickTermsOfUse")
    public void testClickDmcaAndScreenshot() throws Exception {
        WebElement dmcaLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("cl.com/about/dmca")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dmcaLink);
        Thread.sleep(1000); // scroll delay
        dmcaLink.click();
        Thread.sleep(3000); // wait for page to load

        // Take screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("dmca_screenshot.png");
        if (!screenshot.renameTo(dest)) {
            throw new RuntimeException("Failed to save screenshot.");
        }
        System.out.println("Screenshot saved: " + dest.getAbsolutePath());
        Assert.assertTrue(driver.getCurrentUrl().contains("/about/dmca"));
    }

    @Test(priority = 5, dependsOnMethods = "testClickDmcaAndScreenshot")
    public void testScrollUpToTop() throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        System.out.println("Scrolled to top of the page.");
    }

    @Test(priority = 6, dependsOnMethods = "testScrollUpToTop")
    public void testClickCraigslistLink() throws InterruptedException {
        WebElement craigslistLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.header-logo[name='logoLink']")));
        craigslistLink.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("craigslist.org"));
        System.out.println("Clicked on Craigslist logo and returned to the homepage.");
    }

    @Test(priority = 7, dependsOnMethods = "testClickCraigslistLink")
    public void testClickAvoidScamsFraud() throws InterruptedException {
        WebElement scamsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("avoid scams & fraud")));
        scamsLink.click();
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking 'avoid scams & fraud': " + currentUrl);
        Assert.assertTrue(currentUrl.contains("/about/help/safety/scams"),
                "URL does not contain expected '/about/help/safety/scams'");
    }

    @Test(priority = 8, dependsOnMethods = "testClickAvoidScamsFraud")
    public void testClickReportingScams() throws InterruptedException {
        WebElement reportingLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("reporting scams")));
        reportingLink.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/about/help/safety/scams/reporting"));
        System.out.println("Clicked on 'reporting scams'");
    }

    @Test(priority = 9, dependsOnMethods = "testClickReportingScams")
    public void testClickFbiIc3Link() throws InterruptedException {
        WebElement fbiLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("FBI's Internet Crime Complaint Center")));
        fbiLink.click();
        System.out.println("Clicked on FBI IC3 link (should open in new tab or redirect externally)");

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        System.out.println("Test completed and browser closed.");
    }
}