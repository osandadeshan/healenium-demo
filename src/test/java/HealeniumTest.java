import org.testng.annotations.Test;
import page.CheckoutPage;

import static org.testng.Assert.assertTrue;

/**
 * Project Name    : healenium-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 10/17/2021
 * Time            : 7:31 PM
 * Description     :
 **/

public class HealeniumTest extends BaseTest {

    @Test
    public void testCheckoutWithAllFields() {
        new CheckoutPage(driver)
                .inputFirstName("Osanda")
                .inputLastName("Nimalarathna")
                .inputUsername("osanda")
                .inputEmail("osanda@mailinator.com")
                .inputAddress1("Poramba")
                .inputAddress2("Ambalangoda")
                .selectCountry("United States")
                .selectState("California")
                .inputZip("90000CF")
                .inputCreditCardName("Osanda Nimalarathna")
                .inputCreditCardNumber("1234567890123456")
                .inputCreditCardExpiration("12/23")
                .inputCreditCardCvv("123")
                .clickCheckoutButton();

        assertTrue(driver.getCurrentUrl().contains("paymentMethod=on"), "Checkout failed!");
    }
}
