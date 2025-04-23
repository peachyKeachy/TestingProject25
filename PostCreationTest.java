import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class PostCreationTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test (priority = 1)
    public void loginCorrectly() throws InterruptedException {
        driver.get("https://accounts.craigslist.org/login"); //correct login
        driver.findElement(By.id("inputEmailHandle")).sendKeys("softwaretestingfgcu@gmail.com");
        Thread.sleep(3000);
        driver.findElement(By.id("inputPassword")).sendKeys("Craiglist@2025");
        Thread.sleep(3000);
        driver.findElement(By.id("login")).click();
        Thread.sleep(3000);

        // Locate the link by visible text
        WebElement link = driver.findElement(By.linkText("craigslist"));
        link.click();
        Thread.sleep(2000);
    }

    @Test(priority = 2, dependsOnMethods = {"loginCorrectly"})
    public void postButton() throws InterruptedException {
        Thread.sleep(3000);
        // Assumes you are now logged in and on the account home page
        WebElement postButton = driver.findElement(By.xpath("//a[contains(@class, 'cl-goto-post')]"));
        postButton.click();
        Thread.sleep(2000);

        // Click the skip button
        WebElement skipButton = driver.findElement(By.cssSelector("button.brand_new_post"));
        skipButton.click();
        Thread.sleep(2000);

        // Click the continue button
        WebElement goButton = driver.findElement(By.cssSelector("button.go"));
        goButton.click();
        Thread.sleep(2000);
    }

    @Test(priority = 3, dependsOnMethods = {"postButton"})
    public void firstSelection() throws InterruptedException {
        // Click Lee County radio button (value="1")
        WebElement firstRadio = driver.findElement(By.xpath("//input[@name='n' and @value='1']"));
        firstRadio.click();
        Thread.sleep(2000);

    }

    @Test(priority = 4, dependsOnMethods = {"firstSelection"})
    public void postType() throws InterruptedException {
        //Click event/class posting type
        WebElement eventRadio = driver.findElement(By.xpath("//input[@name='id' and @value='e']"));
        eventRadio.click();
        Thread.sleep(2000);

    }

    @Test(priority = 5, dependsOnMethods = {"postType"})
    public void applicationType() throws InterruptedException {
        //Click event/class posting type
        WebElement otherRadio = driver.findElement(By.xpath("//input[@name='id' and @value='eve']"));
        otherRadio.click();
        Thread.sleep(2000);
    }

    @Test(priority = 6, dependsOnMethods = {"applicationType"})
    public void postFormFilled() throws InterruptedException {
        // Fill in the title
        WebElement titleField = driver.findElement(By.name("PostingTitle"));
        titleField.sendKeys("Selenium Test Post");
        Thread.sleep(2000);

        // Fill in the specific location
        WebElement locationField = driver.findElement(By.name("geographic_area"));
        locationField.sendKeys("Fort Myers");
        Thread.sleep(2000);

        // Fill in the postal code
        WebElement postalCodeField = driver.findElement(By.name("postal"));
        postalCodeField.sendKeys("33901");
        Thread.sleep(2000);

        // Fill in the body/description
        WebElement descriptionField = driver.findElement(By.name("PostingBody"));
        descriptionField.sendKeys("This is an automated test post for demonstration purposes only.");
        Thread.sleep(2000);

        //Fill in the date
        WebElement eventDate = driver.findElement(By.cssSelector("input[data-date-input-name='eventStart']"));
        eventDate.sendKeys("Thu, 10 Apr 2025"); // format may vary by Craigslist region
        Thread.sleep(2000);

        //Fill in the street
        WebElement streetField = driver.findElement(By.name("xstreet0"));
        streetField.sendKeys("10501 FGCU Blvd. S.");
        Thread.sleep(2000);

        //Fill in the city
        WebElement cityLowerField = driver.findElement(By.name("city"));
        cityLowerField.sendKeys("Fort Myers");
        Thread.sleep(2000);

        // Click continue button
        WebElement continueButton = driver.findElement(By.cssSelector("button[type='submit']"));
        continueButton.click();
        Thread.sleep(3000);

    }

    @Test(priority = 7, dependsOnMethods = {"postFormFilled"})
    public void postSubmission() throws InterruptedException {
        // Click the continue button
        WebElement final1Button = driver.findElement(By.cssSelector("button.continue.bigbutton"));
        final1Button.click();
        Thread.sleep(2000);

        //click "done with images" button
        WebElement imageButton = driver.findElement(By.cssSelector("button.done"));
        imageButton.click();
        Thread.sleep(2000);

        //click publish button
        WebElement publishButton = driver.findElement(By.cssSelector("button.button[name='go']"));
        publishButton.click();
        Thread.sleep(2000);
    }
    //close driver
    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
