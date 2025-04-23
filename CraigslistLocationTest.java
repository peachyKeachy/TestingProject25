import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;

import java.time.Duration;


public class CraigslistLocationTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // getting page
        driver.get("https://fortmyers.craigslist.org/");
    }

    @Test  (priority = 1)
    public void selectlocationTest() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"topban\"]/div[2]/h2/div/a")).click();
        Thread.sleep(2000);
        // zoom out
        WebElement zoomOut = driver.findElement(By.xpath("//*[@id=\"mapGoesHere\"]/div[2]/div[1]/div/a[2]"));
        zoomOut.click();
        Thread.sleep(1000);
        zoomOut.click();
        Thread.sleep(1000);
        // zoom in
        WebElement zoomIn = driver.findElement(By.xpath("//*[@id=\"mapGoesHere\"]/div[2]/div[1]/div/a[1]"));
        zoomIn.click();
        Thread.sleep(1000);
        zoomIn.click();
        Thread.sleep(1000);
        // check near location
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div[2]/div[2]/div/div[1]/div[2]/div[3]/div")).click();
        Thread.sleep(9000);
    }

    @Test (priority = 2)
    public void dragMapTest() throws InterruptedException {
        // Click to open the map
        driver.findElement(By.xpath("//*[@id=\"topban\"]/div[2]/h2/div/a")).click();
        Thread.sleep(3000);

        // Zoom in a few times to make the map draggable
        WebElement zoomIn = driver.findElement(By.xpath("//*[@id=\"mapGoesHere\"]/div[2]/div[1]/div/a[1]"));
        for (int i = 0; i < 4; i++) {
            zoomIn.click();
            Thread.sleep(500);
        }

        // Target the draggable map pane (not the tile pane)
        WebElement mapContainer = driver.findElement(By.cssSelector("#mapGoesHere .leaflet-map-pane"));

        // Optional: highlight map area to confirm
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", mapContainer);

        // Get location and size for center
        Point location = mapContainer.getLocation();
        Dimension size = mapContainer.getSize();
        int centerX = location.getX() + size.getWidth() / 2;
        int centerY = location.getY() + size.getHeight() / 2;

        // Create Actions instance
        Actions actions = new Actions(driver);

        // Perform drag from center of map
        actions.moveByOffset(centerX, centerY)
                .clickAndHold()
                .pause(Duration.ofMillis(500))
                .moveByOffset(-150, 100)  // drag direction
                .pause(Duration.ofSeconds(1))
                .release()
                .perform();
        Thread.sleep(2000); // wait to observe effect
    }

    @Test (priority = 3)
    public void mapRegionTest() throws InterruptedException {
        // Click to open the map
        driver.findElement(By.xpath("//*[@id=\"topban\"]/div[2]/h2/div/a")).click();
        Thread.sleep(2000);
        // Click region
        driver.findElement(By.name("region")).click();
        Thread.sleep(2000);
        // Dropdown menu interaction
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div[2]/div[1]/div/div[2]/div[1]/select"));
        Thread.sleep(2000);
        // click on location option
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div[2]/div[1]/div/div[2]/div[2]/div/a[3]")).click();
        Thread.sleep(2000);
    }
    //test search box by zip code
    @Test (priority = 4)
    public void chooseLocationTest() throws InterruptedException {
        // Click to open the map
        driver.findElement(By.xpath("//*[@id=\"topban\"]/div[2]/h2/div/a")).click();
        Thread.sleep(2000);
        // look for a zip code and click location
        WebElement searchBox = driver.findElement(By.cssSelector("body > div.bd-focused > div > div > div.content-area > div > div > div.tab-content > div:nth-child(2) > div > div.search-widgets > div.location-search > div > input[type=text]"));
                searchBox.sendKeys("33965");
        Thread.sleep(3000);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div[2]/div[2]/div/div[2]/button")).click();
        Thread.sleep(3000);
    }

    //test search box by place name
    @Test (priority = 5)
    public void chooseLocationTest2() throws InterruptedException {
        // Click to open the map
        driver.findElement(By.xpath("//*[@id=\"topban\"]/div[2]/h2/div/a")).click();
        Thread.sleep(2000);
        // look for a zip code and click location
        WebElement searchBox = driver.findElement(By.cssSelector("body > div.bd-focused > div > div > div.content-area > div > div > div.tab-content > div:nth-child(2) > div > div.search-widgets > div.location-search > div > input[type=text]"));
        searchBox.sendKeys("Bonita");
        Thread.sleep(3000);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div[2]/div[2]/div/div[2]/button")).click();
        Thread.sleep(3000);
    }

    //close driver
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
