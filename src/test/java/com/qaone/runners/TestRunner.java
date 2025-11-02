package com.qaone.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import com.qaone.reporter;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.qaone.stepdefinitions"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/cucumber.json"
    }
)
public class TestRunner extends AbstractTestNGCucumberTests {
    private reporter report;

    @BeforeClass
    public void setup() {
        report = new reporter();
        report.setupReporter();
    }

    @AfterClass
    public void teardown() {
        if (report != null) {
            report.tearDownReporter();
        }
    }
}