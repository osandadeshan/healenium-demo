# Healenium Demo

## What is Healenium?
[Healenium](https://healenium.io/) is an open-source testing framework extension that improves the stability of Selenium-based test cases by handling updated web and mobile elements. Web and mobile applications are updated constantly every sprint, which may lead to a locator change. Healenium uses a type of machine-learning algorithm to analyze the current page state for changes, handle ***NoSuchElement*** test failures, and fix broken tests at runtime by replacing the failed locator with a new value that matches the best and performs an action with the new element successfully. After the test run, Healenium provides detailed reporting with the fixed locators and screenshots. In addition, it supports advanced features like a parallel test run, remote test run, iFrames, actions, selenide integration.

All of that helps to reduce the effort to create reliable Selenium tests and reduces the number of test cases that failed due to non-product issues. In addition, it changes the focus on regressions instead of test maintenance.

## How Does It Work?
Healenium consists of a client part that integrates with test automation frameworks. It includes Tree-comparing dependency. An algorithm self-healing capabilities that analyzes the current DOM state searches in the current state for the best subsequence and generates a new CSS locator. It also implements Selenium WebDriver and overrides the findElement method, and if it catches the NoSuchElement exception, it triggers the Tree-comparing algorithm and starts self-healing.

The back-end part of Healenium is a server that uses PostgreSQL database, which performs interactions between the client part, Tree-comparing, and the database that stores the old and new locator values as related information like DOM page, method name, class name, screenshots, etc.

There are also plugins for Maven and Gradle that generate reports with healing results and communicate with the back-end to get information about the healing elements.

Healenium also provides a plugin for [IntelliJ IDEA](https://www.jetbrains.com/idea/) IDE for updating the codebase with the new values of the locators.

## Demo Project
We're going to use Java with Maven to set up a simple test project using the Page Object Pattern, and we'll integrate Healenium to test its functionality.

## Pre-requisites
- [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/)

- [Docker Desktop](https://www.docker.com/products/docker-desktop) (feel free to use the Docker CLI only, we use it here because it's more user-friendly for beginners)

## How to use the Demo project?
### Starting the Healenium Backend
1. Start a CMD/Terminal in the healenium directory (make sure Docker is up and running) and type:

***`docker-compose up -d`***

2. Wait for docker to download the images and finish the setup. Verify you have healenium backend and database containers running using the Docker Desktop UI.

Or type 

***`docker ps`***

![image](https://user-images.githubusercontent.com/9147189/137907488-635861d4-f68a-4e00-83df-3fcb4fffe849.png)

### Integrating Healenium in tests
Integrating Healenium in your framework is easy as adding one line of code. All you have to do is wrap the WebDriver in the new SelfHealingDriver. 
But, before that you have to import 

***`import com.epam.healenium.SelfHealingDriver;`***

Example code:

```java
@BeforeMethod
public void before() {
    WebDriver delegate = new ChromeDriver(); // declare delegate
    driver = SelfHealingDriver.create(delegate); // create Self-healing driver
    driver.manage().window().maximize();
    driver.navigate().to("http://automationpractice.com/");
}
```

### Running the project
Run the tests at least once with the correct locators. After that, we go to the index.html located in the "***src/main/resources/checkout***" folder and make the following changes (you can try other changes if you want)

- **id="address"** to **id="address1"**
- **id="cc-name"** to **id="card-name"**
- **id="cc-number"** to **id="card-number"**
- **id="cc-expiration"** to **id="card-expiration"**
- **id="cc-cvv"** to **id="card-cvv"**
- **Continue to checkout** to **Checkout**

Normally this would break the locators as they depend on exact matches. Using the self-healing driver, though, the test will pass as the locators will be healed at runtime.
![image](https://user-images.githubusercontent.com/9147189/137924473-815e8470-bf57-4a8c-9d62-53fb6e62dad4.png)

Red boxes indicate that the locator has been fixed, and as you can see, the action has been properly executed.

Suppose you want to further configure Healenium for matching score cap or recovery tries. In that case, you have to create a file called "***healenium.properties***" and put it in the test resources directory at "***src/test/resources***". This is the content of the file:

```
recovery-tries = 1
score-cap = 0.5
heal-enabled = true
serverHost = localhost
serverPort = 7878
```

**recovery-tries** is the number of times the algorithm will try to find a matching locator

**score-cap** is the minimum matching score required for the found locator to be accepted. 0.5 stands for 50%

**heal-enabled** is an option to toggle the healing on or off. The accepted values are true and false

**serverHost** is the URL of the Docker Container server that we created while setting up the back-end

**serverPort** is the port of the server mentioned above

To generate a new report, run the tests through the command line using ***`mvn clean test`***. After a successful test run, you'll see a link to the report generated in the console: 

![image](https://user-images.githubusercontent.com/9147189/137932747-b879f236-dbad-4db6-9f32-71922a08864d.png)

After you open the link in a browser, you'll see a list of all the locators that have been fixed with their old and new values, as well as screenshots of the page on the places the locators have been fixed. There's a switch on the right side where you can check if the locator has been successfully resolved with the correct one or not.

![image](https://user-images.githubusercontent.com/9147189/137934653-59a69eb5-954e-4fee-b475-c74c34ade9fc.png)

As an extra feature, this boilerplate has inbuilt Allure report. After ***`mvn clean test`*** is executed, allure report can be generated using ***`allure serve target/allure-results`*** command.

You can further integrate it into the IntelliJ IDEA IDE by downloading the IDE plugin called **Healenium**. It would be best to go to the locators you want to fix, right-click and choose Healing Results. You'll get a small window showing you a list of fixed locators you can choose from, as well as their matching score:

![image](https://user-images.githubusercontent.com/9147189/137936516-f7ef7087-06d5-44f5-8ead-feb57a63fb6f.png)
