package org.example.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
//        features = {"src/test/resources/features"},
//        features = {"src/test/resources/features/postAndPutDashboard.feature"},
        features = {"src/test/resources/features/getAndDeleteDashboard.feature"},
        glue = {"org.example.cucumber.definitionSteps"})
public class RunCucumberTestTestng extends AbstractTestNGCucumberTests {
}

