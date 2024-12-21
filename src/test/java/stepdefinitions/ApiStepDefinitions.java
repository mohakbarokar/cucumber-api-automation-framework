package stepdefinitions;

import api.utils.DataManager;
import api.utils.LoggerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class ApiStepDefinitions {

    public static final String BASE_URL = "https://gorest.co.in";
    public static final Map<String, String> HEADERS = Map.of(
            "Authorization", "Bearer d4ecd4ac1c8729fcf00cc317e82470b4f0f130edab9d78682213b652f4ee4e7f",
            "Content-Type", "application/json"
    );

    private Response response;

    // This step definition matches the Given step in your feature file
    @Given("existing users with emails {string} and {string} are checked and deleted")
    public void existingUsersAreCheckedAndDeleted(String email1, String email2) {
        String[] emails = {email1, email2};

        for (String email : emails) {
            Response response = RestAssured.given()
                    .baseUri(BASE_URL)
                    .headers(HEADERS)
                    .queryParam("email", email)
                    .get("/public/v2/users");

            if (response.statusCode() == 200) {
                List<?> users = response.jsonPath().getList("");
                if (!users.isEmpty()) {
                    // Assuming the first user in the response list is the one we want to delete
                    Map<String, Object> user = (Map<String, Object>) users.get(0);
                    int id = (int) user.get("id");

                    // Delete the user
                    Response deleteResponse = RestAssured.given()
                            .baseUri(BASE_URL)
                            .headers(HEADERS)
                            .delete("/public/v2/users/" + id);

                    Assert.assertEquals(deleteResponse.statusCode(), 204, "Failed to delete user with email: " + email);
                }
            } else {
                System.out.println("No user found with email: " + email);
            }
        }
    }

    // Rest of the step definitions go here
    @When("a new user is created with valid details")
    public void createNewUser() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Test User");
        requestBody.put("email", "testuser04101993@testmail.coms");
        requestBody.put("gender", "male");
        requestBody.put("status", "active");

        LoggerUtility.logInfo("Request Body: " + requestBody);
        response = RestAssured.given()
                .baseUri(BASE_URL)
                .headers(HEADERS)
                .body(requestBody.toString())
                .post("/public/v2/users");


        LoggerUtility.logInfo("Response: " + response.getBody().asString());
        Assert.assertEquals(response.statusCode(), 201);
        int userId = response.jsonPath().getInt("id");
        DataManager.saveData("id", String.valueOf(userId));
    }

    @Then("the user creation is successful and an {string} is returned")
    public void validateUserCreation(String key) {
        LoggerUtility.logInfo("Validating user with " + key);
        Assert.assertNotNull(DataManager.getData(key), "User ID should not be null");
    }

    @Then("the user details match the input data")
    public void validateUserDetails() {
        Assert.assertEquals(response.jsonPath().getString("name"), "Test User");
        Assert.assertEquals(response.jsonPath().getString("email"), "testuser04101993@testmail.coms");
    }

    @When("the user is retrieved")
    public void retrieveUser() {
        response = RestAssured.given()
                .baseUri(BASE_URL)
                .headers(HEADERS)
                .get("/public/v2/users/" + DataManager.getData("id"));
        LoggerUtility.logInfo("Validating if User is present");
        Assert.assertEquals(response.statusCode(), 200);
    }
}

