Feature: Home Page Verification
  As a user of the Execute Automation App
  I want to verify the home page functionality
  So that I can ensure the application is working correctly

  Scenario: Verify Home Page Title
    Given I am on the Execute Automation home page
    Then I should see the page title "Home - Execute Automation Employee App"

  Scenario: Verify Employee list and search
    Given I navigate to the employee list
    Then the first row name should be "Updated Karthikedited"
    When I search for "abhi"
    Then the first row name should be "abhi" and salary should be "1200"