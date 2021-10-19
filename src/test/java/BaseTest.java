import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import util.TestExecutionListener;
import util.driver.DriverFactory;

import static util.driver.DriverHolder.setDriver;

/**
 * Project Name    : healenium-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 10/17/2021
 * Time            : 7:20 PM
 * Description     :
 **/

@Listeners(TestExecutionListener.class)
public class BaseTest {

    public WebDriver driver;

    @BeforeMethod
    public void before() {
        WebDriver delegate = DriverFactory.getNewInstance("chrome");
        setDriver(delegate);
        driver = SelfHealingDriver.create(delegate);
        driver.manage().window().maximize();
        driver.navigate().to(getClass().getClassLoader().getResource("checkout/index.html"));
    }

    @AfterMethod
    public void after() {
        driver.quit();
    }
}
