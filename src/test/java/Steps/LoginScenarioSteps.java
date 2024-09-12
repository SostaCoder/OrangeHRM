package Steps;

import dev.failsafe.internal.util.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginScenarioSteps {

    WebDriver driver;
    int initRecords;
    int afterAddRecords;
    int afterDelRecords;

    @Given("The user open the website")
    public void the_user_open_the_website() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(21000));
    }

    @When("The user fill username and password and click on login")
    public void the_user_fill_username_and_password_and_click_on_login() {
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.
                        visibilityOfElementLocated(By.className("orangehrm-login-button"))).click();
    }

    @Then("The user should navigate to home page")
    public void the_user_should_navigate_to_home_page() {
        System.out.println(driver.findElement(By.className("oxd-brand")).isDisplayed());
    }


    @Given("Click on Admin tab on the left side menu") /* Get the number of records found */
    public void click_on_admin_tab_on_the_left_side_menu() {
        driver.findElement(By.xpath("//a[@href='/web/index.php/admin/viewAdminModule']")).click();

        // Get the number of records found
        WebElement spanElement = driver.findElement(By.cssSelector("div.orangehrm-horizontal-padding .oxd-text.oxd-text--span"));
        String text = spanElement.getText();
        System.out.println("Number of Records: " + text);
        // Regular expression to find the number within parentheses
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String numberString = matcher.group(1);
            initRecords = Integer.parseInt(numberString);
        }

    }

    @Given("Click on add button")
    public void click_on_add_button() {
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[1]/button"))
                .click();
    }

    @Given("Fill the required data")
    public void fill_the_required_data() {

        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[4]/div/div[2]/input")).sendKeys("test username1");
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/input")).sendKeys("pass12345");
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/input")).sendKeys("pass12345");
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[2]/div/div[2]/div/div/input")).sendKeys("e");

        // Wait for options to appear
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Select the first option
        List<WebElement> options = driver.findElements(By.cssSelector(".oxd-autocomplete-option"));
        if (!options.isEmpty()) {
            options.get(0).click();
        }
        // Select value from "User Role" dropdown
        // Click on the dropdown to open options
        WebElement userRoleDropdown = driver.findElement(
                By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[1]/div/div[2]/div/div/div[1]")
        );
        userRoleDropdown.click();

        // Wait for the dropdown options to be visible
        // Wait for options to appear
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> userRoleOptions = driver.findElements(By.cssSelector(".oxd-select-option"));
        if (!userRoleOptions.isEmpty()) {
            userRoleOptions.get(1).click();
        }

        // Select value from "Status" dropdown
        // Click on the dropdown to open options
        WebElement statusDropdown = driver.findElement(
                By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[3]/div/div[2]/div/div/div[1]")
        );
        statusDropdown.click();

        // Wait for the dropdown options to be visible
        // Wait for options to appear
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> statusOptions = driver.findElements(By.cssSelector(".oxd-select-option"));
        if (!statusOptions.isEmpty()) {
            statusOptions.get(1).click();
        }

    }

    @Given("Click on save button") /* Verify that the number of records increased by 1 */
    public void click_on_save_button() {
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/button[2]")).click();

        //Verify that the number of records increased by 1
        WebElement spanElement = driver.findElement(By.cssSelector("div.orangehrm-horizontal-padding .oxd-text.oxd-text--span"));
        String text = spanElement.getText();
        System.out.println("Number of Records after adding: " + text);
        // Regular expression to find the number within parentheses
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String numberString = matcher.group(1);
            afterAddRecords = Integer.parseInt(numberString);
        }
        assertEquals(initRecords + 1, afterAddRecords, "The number did not increase by 1.");
    }

    @Given("Search with the username for the new user")
    public void search_with_the_username_for_the_new_user() {
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/input")).sendKeys("test username1");
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]")).click();
        // Wait for records to appear
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @When("Delete the new user")
    public void delete_the_new_user() {
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div/div/div[6]/div/button[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div/div/div[3]/button[2]")).click();

    }

    @Then("Verify that the number of records decreased by {int}")
    public void verify_that_the_number_of_records_decreased_by(Integer int1) {
        // click on reset filter
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[1]")).click();

        //Verify that the number of records decreased by 1
        WebElement spanElement = driver.findElement(By.cssSelector("div.orangehrm-horizontal-padding .oxd-text.oxd-text--span"));
        String text = spanElement.getText();
        System.out.println("Number of Records after deleting: " + text);
        // Regular expression to find the number within parentheses
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String numberString = matcher.group(1);
            afterDelRecords = Integer.parseInt(numberString);
        }

        assertEquals(afterAddRecords - 1, afterDelRecords, "The number did not decrease by 1.");

    }
}
