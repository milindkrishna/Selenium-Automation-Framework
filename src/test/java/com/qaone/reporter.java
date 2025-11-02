package com.qaone;

import java.io.File;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class reporter {
    protected static ExtentReports extentReports;
    protected static ExtentSparkReporter extentSparkReporter;
    private static final String REPORT_PATH = "target/reports/ExtentReport.html";

    @BeforeSuite
    public void setupReporter() {
        // Create reports directory if it doesn't exist
        new File("target/reports").mkdirs();
        
        extentReports = new ExtentReports();
        extentSparkReporter = new ExtentSparkReporter(REPORT_PATH);
        
        // Configure the report
        extentSparkReporter.config().setDocumentTitle("Automation Test Report");
        extentSparkReporter.config().setReportName("QA Automation Test Results");
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        
        extentReports.attachReporter(extentSparkReporter);
        
        // Set system info
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Browser", "Chrome");
    }

    @AfterSuite
    public void tearDownReporter() {
        if (extentReports != null) {
            extentReports.flush();
            System.out.println("ExtentReport generated at: " + new File(REPORT_PATH).getAbsolutePath());
        }
    }
}
