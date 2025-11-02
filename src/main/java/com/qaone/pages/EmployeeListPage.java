package com.qaone.pages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeListPage extends BasePage {

    private WebDriverWait wait;

    public EmployeeListPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToEmployeeList() {
        // try a few possible link locators
        List<By> linkLocators = Arrays.asList(
                By.linkText("Employee List"),
                By.xpath("//a[contains(text(),'Employee')]")
        );
        for (By loc : linkLocators) {
            try {
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(loc));
                el.click();
                // wait for table to appear
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
                return;
            } catch (Exception e) {
                // try next
            }
        }
        // if not found, try navigating directly by URL segment
        driver.get("http://eaapp.somee.com/Employee/List");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
    }

    private WebElement findFirstRowCell(int colIndex) {
        By[] possible = new By[] {
                By.xpath("//table//tbody/tr[1]/td["+colIndex+"]"),
                By.xpath("(//table//tr)[2]/td["+colIndex+"]")
        };
        for (By b : possible) {
            try {
                WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(b));
                return el;
            } catch (Exception e) {
                // continue
            }
        }
        throw new RuntimeException("Could not locate first row column " + colIndex);
    }

    public String getFirstRowName() {
        // assume name is in column 1
        try {
            String direct = findFirstRowCell(1).getText().trim();
            if (direct != null && direct.matches(".*[A-Za-z].*")) {
                return direct;
            }
            // fallback: inspect all cells and pick the one that looks like a name
            List<WebElement> cells = getFirstRowCells();
            for (WebElement c : cells) {
                String t = c.getText().trim();
                if (t.matches(".*[A-Za-z].*") && !t.matches("\\d+")) {
                    return t;
                }
            }
            return direct;
        } catch (Exception e) {
            // try column 2 as fallback
            return findFirstRowCell(2).getText().trim();
        }
    }

    public String getFirstRowSalary() {
        // Prefer a numeric value with at least 3 digits (salary), fallback to other heuristics
        try {
            List<WebElement> cells = getFirstRowCells();
            // first try to find a cell with 3+ digits (likely salary)
            for (WebElement c : cells) {
                String t = c.getText().trim();
                if (t.matches(".*\\d{3,}.*")) {
                    return t;
                }
            }
            // then try 2+ digits
            for (WebElement c : cells) {
                String t = c.getText().trim();
                if (t.matches(".*\\d{2,}.*")) {
                    return t;
                }
            }
            // fallback to specific columns
            return findFirstRowCell(3).getText().trim();
        } catch (Exception e) {
            return findFirstRowCell(4).getText().trim();
        }
    }

    public List<WebElement> getFirstRowCells() {
        By[] possible = new By[] {
                By.xpath("//table//tbody/tr[1]/td"),
                By.xpath("(//table//tr)[2]/td")
        };
        for (By b : possible) {
            try {
                List<WebElement> els = driver.findElements(b);
                if (els != null && !els.isEmpty()) {
                    return els;
                }
            } catch (Exception e) {
                // continue
            }
        }
        throw new RuntimeException("Could not locate first row cells");
    }

    public String getFirstRowText() {
        try {
            List<WebElement> cells = getFirstRowCells();
            StringBuilder sb = new StringBuilder();
            for (WebElement c : cells) {
                if (sb.length() > 0) sb.append(" | ");
                sb.append(c.getText().trim());
            }
            return sb.toString();
        } catch (Exception e) {
            try {
                WebElement row = driver.findElement(By.xpath("//table//tbody/tr[1]"));
                return row.getText().trim();
            } catch (Exception ex) {
                return "";
            }
        }
    }

    public void searchFor(String term) {
        // try typical search input locators
        List<By> inputLocators = Arrays.asList(
                By.xpath("//input[@type='search']"),
                By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]") ,
                By.id("searchTerm"),
                By.name("search")
        );

        for (By b : inputLocators) {
            try {
                WebElement input = wait.until(ExpectedConditions.elementToBeClickable(b));
                input.clear();
                input.sendKeys(term);
                input.sendKeys(Keys.ENTER);
                // wait for the table to reload
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody/tr[1]")));
                return;
            } catch (Exception e) {
                // try next
            }
        }

        // fallback: try searching by filling any input on the page
        try {
            WebElement anyInput = driver.findElement(By.tagName("input"));
            anyInput.clear();
            anyInput.sendKeys(term);
            anyInput.sendKeys(Keys.ENTER);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody/tr[1]")));
        } catch (Exception e) {
            throw new RuntimeException("Could not perform search for term: " + term);
        }
    }
}
