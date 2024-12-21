package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import api.utils.LoggerUtility;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Hooks {
    // This method will run before all tests (used to delete existing users)
    @BeforeAll
    public static void deleteExistingUsers() {
        LoggerUtility.logInfo("Deleting Users if any already present in backend");
        String[] emails = {"testuser04101993@testmail.coms", "testuser04101993ups@testmail.coms"};
        LoggerUtility.logInfo("Emails : " + Arrays.toString(emails));
        for (String email : emails) {
            Response response = RestAssured.given()
                    .baseUri(ApiStepDefinitions.BASE_URL)
                    .headers(ApiStepDefinitions.HEADERS)
                    .queryParam("email", email)
                    .get("/public/v2/users");

            if (response.statusCode() == 200) {
                List<?> users = response.jsonPath().getList("");
                if (!users.isEmpty()) {
                    LoggerUtility.logInfo("User List in not empty");
                    // Assuming the first user in the response list is the one we want to delete
                    Map<String, Object> user = (Map<String, Object>) users.get(0);
                    int id = (int) user.get("id");
                    LoggerUtility.logInfo("User present : " + user + ", Deleting the user");
                    // Delete the user

                    Response deleteResponse = RestAssured.given()
                            .baseUri(ApiStepDefinitions.BASE_URL)
                            .headers(ApiStepDefinitions.HEADERS)
                            .delete("/public/v2/users/" + id);

                    Assert.assertEquals(deleteResponse.statusCode(), 204, "Failed to delete user with email: " + email);
                    LoggerUtility.logInfo("User Deleted : " + id);
                }
            } else {
                System.out.println("No user found with email: " + email);
            }
        }
    }

    @Before
    public void setUp() {
        LoggerUtility.logInfo("Starting Test...");
    }

    @After
    public void tearDown() {
        LoggerUtility.logInfo("Test Execution Finished.");
    }
}
