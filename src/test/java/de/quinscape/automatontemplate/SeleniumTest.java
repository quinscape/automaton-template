package de.quinscape.automatontemplate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Example of a selenium integration-test with gecko-driver
 */
@Tag("integration")
@EnabledIfSystemProperty(named = "webdriver.gecko.driver", matches = ".+")
public class SeleniumTest
{
    private static FirefoxDriver driver;

    @BeforeAll
    public static void initDriver()
    {
        final FirefoxOptions options = new FirefoxOptions();
        options.setCapability("marionette", true);
        driver = new FirefoxDriver(options);
    }

    @AfterAll
    public static void deinitDriver()
    {
        driver.close();
    }

    @Test
    public void testSelenium()
    {

        driver.navigate().to("http://localhost:8080/shipping/fk-test");


        login(driver, "admin");

        final WebElement titleElem = driver.findElementByXPath("//h1[text()=\"[CRUD List]\"]");
        assertThat(titleElem, is(notNullValue()));
    }


    private void login(FirefoxDriver driver, String name)
    {
        final WebElement usernameField = driver.findElementByName("username");
        usernameField.sendKeys(name);
        final WebElement passwordField = driver.findElementByName("password");
        passwordField.sendKeys(name);

        driver.findElementByXPath("//button[text()=\"Submit\"]").click();
    }
}
