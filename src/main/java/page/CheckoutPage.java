package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Project Name    : healenium-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 10/17/2021
 * Time            : 7:27 PM
 * Description     :
 **/

public class CheckoutPage {

    private final By FIRST_NAME_TEXT_BOX = By.id("firstName");
    private final By LAST_NAME_TEXT_BOX = By.id("lastName");
    private final By USERNAME_TEXT_BOX = By.id("username");
    private final By EMAIL_TEXT_BOX = By.id("email");
    private final By ADDRESS1_TEXT_BOX = By.id("address");
    private final By ADDRESS2_TEXT_BOX = By.id("address2");
    private final By COUNTRY_DROPDOWN = By.id("country");
    private final By STATE_DROPDOWN = By.id("state");
    private final By ZIP_TEXT_BOX = By.id("zip");
    private final By CREDIT_CARD_NAME_TEXT_BOX = By.id("cc-name");
    private final By CREDIT_CARD_NUMBER_TEXT_BOX = By.id("cc-number");
    private final By CREDIT_CARD_EXPIRATION_TEXT_BOX = By.id("cc-expiration");
    private final By CREDIT_CARD_CVV_TEXT_BOX = By.id("cc-cvv");
    private final By CHECKOUT_BUTTON = By.xpath("//button[text()='Continue to checkout']");

    private final WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public CheckoutPage inputFirstName(String firstName) {
        driver.findElement(FIRST_NAME_TEXT_BOX).sendKeys(firstName);
        return this;
    }

    public CheckoutPage inputLastName(String lastName) {
        driver.findElement(LAST_NAME_TEXT_BOX).sendKeys(lastName);
        return this;
    }

    public CheckoutPage inputUsername(String username) {
        driver.findElement(USERNAME_TEXT_BOX).sendKeys(username);
        return this;
    }

    public CheckoutPage inputEmail(String email) {
        driver.findElement(EMAIL_TEXT_BOX).sendKeys(email);
        return this;
    }

    public CheckoutPage inputAddress1(String address1) {
        driver.findElement(ADDRESS1_TEXT_BOX).sendKeys(address1);
        return this;
    }

    public CheckoutPage inputAddress2(String address2) {
        driver.findElement(ADDRESS2_TEXT_BOX).sendKeys(address2);
        return this;
    }

    public CheckoutPage selectCountry(String country) {
        new Select(driver.findElement(COUNTRY_DROPDOWN)).selectByVisibleText(country);
        return this;
    }

    public CheckoutPage selectState(String state) {
        new Select(driver.findElement(STATE_DROPDOWN)).selectByVisibleText(state);
        return this;
    }

    public CheckoutPage inputZip(String zip) {
        driver.findElement(ZIP_TEXT_BOX).sendKeys(zip);
        return this;
    }

    public CheckoutPage inputCreditCardName(String name) {
        driver.findElement(CREDIT_CARD_NAME_TEXT_BOX).sendKeys(name);
        return this;
    }

    public CheckoutPage inputCreditCardNumber(String number) {
        driver.findElement(CREDIT_CARD_NUMBER_TEXT_BOX).sendKeys(number);
        return this;
    }

    public CheckoutPage inputCreditCardExpiration(String expiration) {
        driver.findElement(CREDIT_CARD_EXPIRATION_TEXT_BOX).sendKeys(expiration);
        return this;
    }

    public CheckoutPage inputCreditCardCvv(String cvv) {
        driver.findElement(CREDIT_CARD_CVV_TEXT_BOX).sendKeys(cvv);
        return this;
    }

    public void clickCheckoutButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(CHECKOUT_BUTTON));
    }
}
