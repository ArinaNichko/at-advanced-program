package org.example.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/features/"},
        glue = {"org.example.cucumber.definitionSteps"})
public class RunCucumberTestTestNg extends AbstractTestNGCucumberTests {
}

