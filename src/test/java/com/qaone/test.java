package com.qaone;

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;
import com.qaone.base.TestBase;
import com.qaone.pages.HomePage;

public class test extends TestBase {
    
    @Test
    public void verifyHomePageTitle() {
        ExtentTest test = extentReports.createTest("Home Page Test", "Verify Home Page Title");
        test.info("Test started");

        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        
        String actualTitle = homePage.getPageTitle();
        String expectedTitle = "Home - Execute Automation Employee App";
        
        Assert.assertEquals(actualTitle, expectedTitle, "Home page title does not match");
        test.pass("Successfully verified home page title: " + actualTitle);
        
        test.info("Test completed");
    }
}
