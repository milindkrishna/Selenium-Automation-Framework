package com.qaone.pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private static final String PAGE_URL = "http://eaapp.somee.com";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToHomePage() {
        driver.get(PAGE_URL);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}