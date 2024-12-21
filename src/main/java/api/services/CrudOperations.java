package api.services;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CrudOperations {

//    static {
//        // Configure RestAssured to relax HTTPS validation globally
//        RestAssured.config = RestAssured.config()
//                .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
//    }

//    public static Response create(RequestSpecification requestSpec, String endpoint, String body) {
//        return requestSpec.body(body).post(endpoint);
//    }
//
//    public static Response read(RequestSpecification requestSpec, String endpoint) {
//        return requestSpec.get(endpoint);
//    }
//
//    public static Response update(RequestSpecification requestSpec, String endpoint, String body) {
//        return requestSpec.body(body).put(endpoint);
//    }
//
//    public static Response delete(RequestSpecification requestSpec, String endpoint) {
//        return requestSpec.delete(endpoint);
//    }
}
