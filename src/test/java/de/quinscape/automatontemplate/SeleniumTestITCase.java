package de.quinscape.automatontemplate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * The inclusion of this test into the integration tests happens by following the "*ITCase.java" naming convention
 */
public class SeleniumTestITCase
{
    private static FirefoxDriver driver;

    @BeforeClass
    public static void initDriver()
    {
        final FirefoxOptions options = new FirefoxOptions();
        options.setCapability("marionette", true);
        driver = new FirefoxDriver(options);
    }

    @AfterClass
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
