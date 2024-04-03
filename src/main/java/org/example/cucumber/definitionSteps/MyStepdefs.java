package org.example.cucumber.definitionSteps;

import io.cucumber.java.en.Given;
import org.example.setUp.testng.BaseTest;
import org.openqa.selenium.JavascriptExecutor;

public class MyStepdefs extends BaseTest {
    @Given("something")
    public void something() {


        // Navigate to the desired web page
        driver.get("https://reportportal.epam.com/");

        // Execute JavaScript code to set Authorization header
        String token = "Zg3XqEDeYWM9pVGiNnThawaBTBbv5bpY_SS0aSFIaR126ykBZXKxc86JfAw-DA9sThbHMUpcMojSoiA0cnXY4iHhz8eMefX1G";
        ((JavascriptExecutor) driver).executeScript(
                "window.localStorage.setItem('Authorization', 'Bearer " + token + "');"
        );

        // Refresh the page to apply the Authorization header
        driver.navigate().refresh();

        System.out.println("Done");
    }
}
