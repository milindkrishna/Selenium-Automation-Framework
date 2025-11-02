package com.qaone.stepdefinitions;

import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.qaone.base.TestBase;
import com.qaone.pages.HomePage;
import com.qaone.pages.EmployeeListPage;
import com.aventstack.extentreports.ExtentTest;

public class HomePageSteps extends TestBase {
    private HomePage homePage;
    private EmployeeListPage employeeListPage;
    private ExtentTest test;

    @Given("I am on the Execute Automation home page")
    public void i_am_on_the_execute_automation_home_page() {
        test = extentReports.createTest("Home Page Test", "Verify Home Page Title");
        test.info("Test started");
        setUp();
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
    }

    @Then("I should see the page title {string}")
    public void i_should_see_the_page_title(String expectedTitle) {
        String actualTitle = homePage.getPageTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Home page title does not match");
        test.pass("Successfully verified home page title: " + actualTitle);
        test.info("Test completed");
        tearDown();
    }

    @Given("I navigate to the employee list")
    public void i_navigate_to_the_employee_list() {
        test = extentReports.createTest("Employee List Test", "Verify Employee List and Search");
        test.info("Test started");
        // initialize driver and navigate home
        setUp();
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        employeeListPage = new EmployeeListPage(driver);
        employeeListPage.goToEmployeeList();
    }

    @Then("the first row name should be {string}")
    public void the_first_row_name_should_be(String expectedName) {
        String actual = employeeListPage.getFirstRowName();
        Assert.assertEquals(actual, expectedName, "First row name did not match");
        test.pass("First row name verified: " + actual);
    }

    @When("I search for {string}")
    public void i_search_for(String term) {
        employeeListPage.searchFor(term);
    }

    @Then("the first row name should be {string} and salary should be {string}")
    public void the_first_row_name_and_salary_should_be(String expectedName, String expectedSalary) {
        String rowText = employeeListPage.getFirstRowText();
        System.out.println("First row full text: " + rowText);
        test.info("First row full text: " + rowText);
        String actualName = employeeListPage.getFirstRowName();
        String actualSalary = employeeListPage.getFirstRowSalary();
        Assert.assertEquals(actualName, expectedName, "Name after search did not match");
        Assert.assertTrue(actualSalary.contains(expectedSalary), "Salary after search did not match - actual: " + actualSalary);
        test.pass("After search verified name: " + actualName + " and salary contains: " + expectedSalary);
        test.info("Test completed");
        tearDown();
    }
}