# Healenium Demo

## What is Healenium?
[Healenium](https://healenium.io/) is an open-source test framework extension that enhances the stability of selenium-based test cases. It automatically handles the updated web and mobile elements. In practical scenarios, Web and mobile applications are updated constantly in every sprint and that may caused to locator changes. Healenium uses a type of machine-learning algorithm to analyze the current page state for changes, handle ***NoSuchElement*** test failures, and fix broken tests at runtime by replacing the failed locator with a new value that matches the best and performs an action with the new element successfully. After the test run, Healenium provides detailed reporting with the fixed locators and screenshots. In addition, it supports advanced features like a parallel test run, remote test run, iFrames, actions, selenide integration.

All of this decreases the time and effort required to write reliable Selenium tests, as well as the amount of test cases that fail due to test defects.

## How Does It Work?
Healenium has a client component that connects to test automation frameworks. It has a Tree-comparison dependence in it. An algorithm with self-healing capabilities that examines the current DOM state and generates a new CSS locator by searching for the best subsequence in the current state. It also implements Selenium WebDriver and modifies the findElement method, triggering the Tree-comparing process and initiating self-healing if the NoSuchElement exception is caught.

Healenium's backend is a server that uses a PostgreSQL database to execute interactions between the client and the database, which records old and new locator values as well as relevant information such as DOM page, method name, class name, screenshots, and so on.

There are also Maven and Gradle plugins that generate healing results reports and interact with the backend to obtain information about the healing elements.

Healenium also offers an [IntelliJ IDEA](https://www.jetbrains.com/idea/) plugin for updating the codebase with new location values.

## Demo Project
This demo project incorporates Healenium and uses Java with Maven and the Page Object Pattern.

## Pre-requisites
- [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/)

- [Docker Desktop](https://www.docker.com/products/docker-desktop)

## How to run this Demo project?
### Starting the Healenium Backend
1. First, make sure Docker is up and running.

2. Open a Command Prompt or Terminal in the "***healenium***" directory and execute:

***`docker-compose up -d`***

![image](https://user-images.githubusercontent.com/9147189/138021369-c61c225b-1d1c-4a55-940c-229d124e899f.png)

3. Wait for docker to download the images and finish the setup.

4. Using the Docker Desktop UI, check that the Healenium backend and database containers are running.

![image](https://user-images.githubusercontent.com/9147189/137907488-635861d4-f68a-4e00-83df-3fcb4fffe849.png)

Or you can execute ***`docker ps`*** and check that.

### Integrating Healenium in tests
It's as simple as writing one line of code to integrate Healenium into your framework. Wrapping the WebDriver in the new SelfHealingDriver is all that's required.

But, before that you have to import,

***`import com.epam.healenium.SelfHealingDriver;`***

**Example code:**

```java
@BeforeMethod
public void before() {
    WebDriver delegate = new ChromeDriver(); // declare delegate
    driver = SelfHealingDriver.create(delegate); // create Self-healing driver
    driver.manage().window().maximize();
    driver.navigate().to("http://automationpractice.com/");
}
```

### Running the tests
Run the tests with the proper locators at least once using ***`mvn clean test`***. After that, we make the following changes to "***index.html***" in the "***src/main/resources/checkout***" folder. (You can change any locators as you wish)

- **id="address"** to **id="address1"**
- **id="cc-name"** to **id="card-name"**
- **id="cc-number"** to **id="card-number"**
- **id="cc-expiration"** to **id="card-expiration"**
- **id="cc-cvv"** to **id="card-cvv"**
- **Continue to checkout** to **Checkout**

Normally, this would cause the locators to malfunction because they rely on exact matches. The test will pass because the self-healing driver is used, and it will heal the locators during runtime.

![image](https://user-images.githubusercontent.com/9147189/137924473-815e8470-bf57-4a8c-9d62-53fb6e62dad4.png)

The locator has been fixed, and the action has been appropriately done, as indicated by the red boxes.

Let's say you want to fine-tune Healenium to include a score cap or recovery tries. In such scenario, create a file called "***healenium.properties***" and place it in the test resources ("***src/test/resources***") directory. The following is the file's content:

```
recovery-tries = 1
score-cap = 0.5
heal-enabled = true
serverHost = localhost
serverPort = 7878
```

**recovery-tries** - The number of times the algorithm will try to discover a matched locator.

**score-cap** - The minimum matching score required for the detected locator to be accepted (50% is represented by the number 0.5).

**heal-enabled** - A toggle switch that turns healing on or off. True and false are the accepted values.

**serverHost** - The URL of the Docker Container server that we established while setting up the backend.

**serverPort** - The above-mentioned server's port

Run the tests using ***`mvn clean test`*** to generate a new report from the command line. A link to the report generated in the console will appear after a successful test run. 

![image](https://user-images.githubusercontent.com/9147189/137932747-b879f236-dbad-4db6-9f32-71922a08864d.png)

When you open the link in a browser, you'll get a list of all the locators that have been fixed, along with screenshots of the page where the locators have been fixed. On the right side, there's a switch to see if the locator has been properly resolved with the correct one.

![image](https://user-images.githubusercontent.com/9147189/137934653-59a69eb5-954e-4fee-b475-c74c34ade9fc.png)

This boilerplate also includes an Allure report as an added feature. After running ***`mvn clean test`***, run ***`allure serve target/allure-results`*** to generate the Allure report.

By installing the **Healenium** IDE plugin, you can enable automatic locator updating feature into the IntelliJ IDEA. You can right-click on the locators you want to correct and select Healing Results. You'll see a little window with a list of fixed locators to choose from, along with their corresponding score.

![image](https://user-images.githubusercontent.com/9147189/137936516-f7ef7087-06d5-44f5-8ead-feb57a63fb6f.png)
