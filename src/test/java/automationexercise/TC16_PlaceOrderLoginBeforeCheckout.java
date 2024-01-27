package automationexercise;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

import static com.myfirstproject.utilities.TestBase.waitFor;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TC16_PlaceOrderLoginBeforeCheckout {
    WebDriver driver;
    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://automationexercise.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }
    @AfterEach
    public void close(){
//        driver.close();
    }
    @Test
    public void test(){
//        3. Verify that home page is visible successfully
        assertTrue(driver.getCurrentUrl().contains("https://automationexercise.com/"));
//        4. Click 'Signup / Login' button
        driver.findElement(By.xpath("//a[@href='/login']")).click();
//        5. Fill all details in Signup and create account
        //driver.findElement(By.xpath("//input[@name='name']")).sendKeys("ahmet");
        driver.findElement(By.xpath("(//input[@name='email'])")).sendKeys("ah159@gmail.com");
        driver.findElement(By.xpath("//input[@PLACEHOLDER='Password']")).sendKeys("123");
//        click login button
        driver.findElement(By.xpath("//button[.='Login']")).click();
//        6. Verify 'Logged in as username' at top
        System.out.println("username : " + driver.findElement(By.xpath("//b")).getText());
        assertTrue(driver.findElement(By.xpath("//b")).getText().contains("ahmet"));
//        7. Add products to cart
        driver.navigate().refresh();
        driver.findElement(By.xpath("//a[@href='/products']")).click();
        waitFor(3);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//div[@class='productinfo text-center']/p")));
        waitFor(1);
        driver.findElement(By.xpath("//a[.='Add to cart']")).click();
//        8. Click 'Cart' button
        driver.findElement(By.linkText("View Cart")).click();
//        9. Verify that cart page is displayed
        assertTrue(driver.findElement(By.xpath("//a[.='Proceed To Checkout']")).isDisplayed());
//        10. Click Proceed To Checkout
        driver.findElement(By.xpath("//a[.='Proceed To Checkout']")).click();
//        11. Verify Address Details and Review Your Order
        assertTrue(driver.findElement(By.xpath("//li[@class='address_firstname address_lastname']")).getText().contains("ahmet"));
//        12. Enter description in comment text area and click 'Place Order'
        Faker faker = new Faker();
        driver.findElement(By.xpath("//textarea[@name='message']")).sendKeys(faker.lorem().sentence());
        driver.findElement(By.xpath("//a[.='Place Order']")).click();
        driver.navigate().refresh();
//        13. Enter payment details: Name on Card, Card Number, CVC, Expiration date
        driver.findElement(By.xpath("//input[@name='name_on_card']")).sendKeys(faker.name().fullName());
        driver.findElement(By.xpath("//input[@name='card_number']")).sendKeys("1234 1234 1234 1234");
        driver.findElement(By.xpath("//input[@name='cvc']")).sendKeys("999");
        driver.findElement(By.xpath("//input[@name='expiry_month']")).sendKeys("10");
        driver.findElement(By.xpath("//input[@name='expiry_year']")).sendKeys("2025");
//        14. Click 'Pay and Confirm Order' button
        driver.findElement(By.xpath("//button")).click();
//        15. Verify success message 'Your order has been placed successfully!'
        assertTrue(driver.findElement(By.xpath("//p[.='Congratulations! Your order has been confirmed!']")).getText().contains("Congratulations!"));
//        16. Click 'Delete Account' button
        driver.findElement(By.xpath("//a[@href='/delete_account']")).click();
//        17. Verify 'ACCOUNT DELETED!' and click 'Continue' button
        assertTrue(driver.findElement(By.xpath("//h2[@data-qa='account-deleted']")).getText().contains("DELETED"));
    }
}
