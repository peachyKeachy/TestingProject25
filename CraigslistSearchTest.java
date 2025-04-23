import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CraigslistSearchTest {
    private WebDriver driver;

    public void searchCraigslist() throws InterruptedException {
        // search for bike
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"leftbar\"]/div[1]/div/input"));
        searchBox.sendKeys("bike");
        Thread.sleep(2000);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
    }

    // Setup before each test
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://fortmyers.craigslist.org/");
    }

    // Test that the search input field is visible
    @Test (priority = 1)
    public void testSearchFieldVisible() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"leftbar\"]/div[1]/div/input"));
        assert searchBox.isDisplayed();
        Thread.sleep(3000);
    }

    // Test performing a search and checking that results appear, click on a search result
    @Test  (priority = 2)
    public void testSearchResultsDisplayed() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"leftbar\"]/div[1]/div/input"));
        searchBox.sendKeys("bike");
        Thread.sleep(2000);
        searchBox.sendKeys(Keys.ENTER);
        // Wait briefly and click on a result
        Thread.sleep(4000);
        driver.findElement(By.xpath("//*[@id=\"search-results-1\"]/div[2]/div/a/span")).click();
        Thread.sleep(3000);
    }

    //test price filter
    @Test (priority = 3)
    public void testPriceFilter() throws InterruptedException {
        driver.get("https://fortmyers.craigslist.org/search/sss#search=2~gallery~0");
        Thread.sleep(2000);
        // Locate the min and max price input fields
        WebElement minPrice = driver.findElement(By.name("min_price"));
        WebElement maxPrice = driver.findElement(By.name("max_price"));
        // Set a price range
        minPrice.sendKeys("100");
        maxPrice.sendKeys("500");
        Thread.sleep(3000);
        // Submit prices
        maxPrice.submit();
        // Verify that the URL contains the price parameters
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("min_price=100"));
        Assert.assertTrue(currentUrl.contains("max_price=500"));
        Thread.sleep(4000);
    }

    @Test  (priority = 4)
    public void testCategoryLink() throws InterruptedException {
        searchCraigslist();
        //scroll sidebar down
        WebElement sidebar = driver.findElement(By.xpath("/html/body/div[1]/main/form/div[2]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollTop += 400", sidebar);  // scroll down 300px
        Thread.sleep(2000);
        // Locate a specific category link within the sidebar: motor category
        driver.findElement(By.xpath("/html/body/div[1]/main/form/div[2]/div[2]/div[11]/button/span[1]")).click();
        Thread.sleep(2000);
        //checkbox click: motor
        WebElement checkbox1 = driver.findElement(By.xpath("/html/body/div[1]/main/form/div[2]/div[2]/div[11]/ul/li[1]/input"));
        checkbox1.click();
        Thread.sleep(2000);
        checkbox1.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        // Verify that the URL corresponds to the selected category
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("motorcycle_motor_type"));
    }

    // test gallery view
    @Test (priority = 5)
    public void testGalleryView() throws InterruptedException {
        //search for bike
        searchCraigslist();
        //click gallery view
        WebElement galleryView = driver.findElement(By.xpath("/html/body/div[1]/main/div[1]/div[4]/div[1]/div[1]/div[1]/span"));
        galleryView.click();
        Thread.sleep(2000);
        //click list view
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/button[1]")).click();
        Thread.sleep(1000);
        // click thumb view
        galleryView.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/button[2]/span[2]")).click();
        Thread.sleep(1000);
        //click preview
        galleryView.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/button[3]/span[2]")).click();
        Thread.sleep(1000);
        //click grid
        galleryView.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/button[4]/span[2]")).click();
        Thread.sleep(1000);
        //click gallery
        galleryView.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/button[5]/span[2]")).click();
        Thread.sleep(1000);
        //click map
        galleryView.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/button[6]/span[2]")).click();
        Thread.sleep(1000);
    }

    // count all web elements on bike search page
    @Test (priority = 6)
    public void countAllElementsOnPage() throws InterruptedException {
        driver.get("https://fortmyers.craigslist.org/search/sss?query=bike#search=2~gallery~0");
        Thread.sleep(3000);
        // This will find ALL elements on the page
        List<WebElement> allElements = driver.findElements(By.xpath("//*"));
        System.out.println("Total number of elements on the page for bike: " + allElements.size());
    }

    // Test and count how many results appear on the dropdown for search bar for specific element
    @Test  (priority = 7)
    public void testClickFirstResult() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"leftbar\"]/div[1]/div/input"));
        searchBox.sendKeys("laptop");
        searchBox.sendKeys(Keys.ENTER); // Submit the search
        Thread.sleep(5000); // Wait for results to load
        // Grab result postings
        List<WebElement> results = driver.findElements(By.cssSelector("li.cl-static-search-result"));
        System.out.println("Number of search results for 'laptop': " + results.size());
    }

        // Tear down after each test
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
