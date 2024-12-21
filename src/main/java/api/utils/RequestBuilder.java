package api.utils;

import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

public class RequestBuilder {
    public static RequestSpecification buildRequest(String baseUri) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType("application/json")
                .auth().oauth2("Bearer d4ecd4ac1c8729fcf00cc317e82470b4f0f130edab9d78682213b652f4ee4e7f");
    }
}

//HEADERS: {
//        'Accept': 'application/json',
//        'Content-Type': 'application/json',
//        'Authorization': 'Bearer d4ecd4ac1c8729fcf00cc317e82470b4f0f130edab9d78682213b652f4ee4e7f'
//        }
