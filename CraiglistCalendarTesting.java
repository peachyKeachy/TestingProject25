import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CraiglistCalendarTesting {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://fortmyers.craigslist.org/");
    }

    @Test(priority = 1)
    public void testClickEventCalendar() throws InterruptedException {
        WebElement eventCalendarLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("event calendar")));
        eventCalendarLink.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/search/eee"));
        System.out.println("Navigated to Event Calendar.");
    }

    @Test(priority = 2, dependsOnMethods = "testClickEventCalendar")
    public void testCheckFoodDrinkBox() throws InterruptedException {
        WebElement foodDrinkCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.name("event_food")));
        foodDrinkCheckbox.click();
        Thread.sleep(3000);
        Assert.assertTrue(foodDrinkCheckbox.isSelected());
        System.out.println("Food/Drink checkbox selected.");
    }

    @Test(priority = 3, dependsOnMethods = "testCheckFoodDrinkBox")
    public void testClickApplyButton() throws InterruptedException {
        Thread.sleep(3000);

        WebElement applyNowBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[translate(span/text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='apply']")
                )
        );

        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", applyNowBtn);
        Thread.sleep(1000);

        applyNowBtn.click();
        Thread.sleep(3000);
        System.out.println("Apply button clicked.");
    }

    @Test(priority = 4, dependsOnMethods = "testClickApplyButton")
    public void testFilterOldestFirst() throws InterruptedException {
        Thread.sleep(3000);
        WebElement sortButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'cl-search-sort-mode')]")
        ));
        sortButton.click();
        Thread.sleep(3000);
        System.out.println("Sort dropdown opened.");
    }

    @Test(priority = 5, dependsOnMethods = "testFilterOldestFirst")
    public void testClickOlder() throws InterruptedException {
        Thread.sleep(3000);
        WebElement oldestBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class,'cl-search-sort-mode-oldest')]")
        ));
        oldestBtn.click();
        Thread.sleep(3000);
        System.out.println("Filtered by Oldest First.");
    }

    @Test(priority = 6, dependsOnMethods = "testClickOlder")
    public void testClickFirstEvent() throws InterruptedException {
        Thread.sleep(3000);
        WebElement firstEvent = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),\"Comedian Eric D'Alessandro\")]")
        ));
        firstEvent.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/eve/"));
        System.out.println("First event opened.");
    }

    @Test(priority = 7, dependsOnMethods = "testClickFirstEvent")
    public void testClickNextButton() throws InterruptedException {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.next")
        ));
        nextButton.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/eve/"));
        System.out.println("Navigated to next event.");
    }

    @Test(priority = 8, dependsOnMethods = "testClickNextButton")
    public void testClickFavoriteButton() throws InterruptedException {
        WebElement favoriteLabel = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[text()='favorite']")
        ));
        favoriteLabel.click();
        Thread.sleep(3000);
        System.out.println("Event marked as favorite.");
    }

    @Test(priority = 9, dependsOnMethods = "testClickFavoriteButton")
    public void testBackToSearch() throws InterruptedException {
        WebElement backToSearch = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.backup[title='back to search']")));
        backToSearch.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("search/eee"));
        System.out.println("Returned to search results.");
    }

    @Test(priority = 10, dependsOnMethods = "testBackToSearch")
    public void testClickFaves() throws InterruptedException {
        WebElement favesLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("faves")));
        favesLink.click();
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking 'faves': " + currentUrl);
        Assert.assertTrue(currentUrl.contains("favorites"), "URL does not contain 'favorites'. Check if click worked.");
    }

    @Test(priority = 11, dependsOnMethods = "testClickFaves")
    public void testClickHiddenLink() throws InterruptedException {
        WebElement hiddenLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@class,'cl-goto-banished')]//span[text()='hidden']")
        ));
        hiddenLink.click();
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking 'hidden': " + currentUrl);
        Assert.assertTrue(currentUrl.contains("favorites"), "URL does not contain 'favorites' after clicking 'hidden'.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        System.out.println("Browser closed. Test session completed.");
    }
}


