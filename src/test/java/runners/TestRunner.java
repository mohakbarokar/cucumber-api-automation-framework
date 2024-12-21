//package runners;
//
//import api.utils.LoggerUtility;
//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;
//
//@CucumberOptions(
//        features = "src/test/resources/features",
//        glue = "stepdefinitions",
//        plugin = {"pretty", "json:src/test/resources/reports/json-report.json",
//                "html:src/test/resources/reports/html-report.html"}
//)
//public class TestRunner extends AbstractTestNGCucumberTests {
//}
package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        plugin = {"pretty", "json:src/test/resources/reports/cucumber.json",
                "html:src/test/resources/reports/html-report.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
