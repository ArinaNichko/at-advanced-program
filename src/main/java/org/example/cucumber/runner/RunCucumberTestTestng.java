package org.example.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/resources/features/"},
        glue = {"org.example.cucumber.definitionSteps"})
public class RunCucumberTestTestng extends AbstractTestNGCucumberTests {
}

