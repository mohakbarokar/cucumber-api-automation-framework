////package stepdefinitions;
//////
//////import api.utils.GeneratePemCertificate;
//////import io.cucumber.java.en.Given;
//////import io.cucumber.java.en.Then;
//////import io.cucumber.java.en.When;
//////import io.restassured.RestAssured;
//////import io.restassured.config.SSLConfig;
//////import io.restassured.response.Response;
//////import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//////import org.apache.http.conn.ssl.SSLContexts;
//////import org.apache.http.impl.client.HttpClients;
//////import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//////import org.testng.Assert;
//////
//////import javax.net.ssl.SSLContext;
//////import java.io.File;
//////import java.security.KeyStore;
//////import io.cucumber.java.en.Given;
//////import io.cucumber.java.en.Then;
//////import io.cucumber.java.en.When;
//////import io.restassured.RestAssured;
//////import io.restassured.response.Response;
//////import org.testng.Assert;
//////
//////import javax.net.ssl.KeyManagerFactory;
//////import javax.net.ssl.SSLContext;
//////import javax.net.ssl.TrustManagerFactory;
//////import java.io.FileInputStream;
//////import java.security.KeyStore;
//////import static io.restassured.config.SSLConfig.sslConfig;
//////
//////public class MTLSTestSteps {
//////    private Response response;
//////
//////    @Given("I have the client certificate and key")
//////    public void iHaveTheClientCertificateAndKey() throws Exception {
//////        // Load the PKCS12 keystore containing the client certificate and private key
//////        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//////        try (FileInputStream keyStoreInput = new FileInputStream("src/test/resources/client-keystore.p12")) {
//////            keyStore.load(keyStoreInput, "yourpassword".toCharArray());  // Replace with the keystore password
//////        }
//////
//////        // Initialize KeyManagerFactory with the keystore (for the client certificate)
//////        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//////        keyManagerFactory.init(keyStore, "yourpassword".toCharArray());  // Replace with the key password
//////
//////        // Initialize TrustManagerFactory (can use the default trust store, or use a custom one)
//////        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//////        trustManagerFactory.init(keyStore);
//////
//////        // Create and initialize SSLContext with KeyManager and TrustManager
//////        SSLContext sslContext = SSLContext.getInstance("TLS");
//////        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
//////
//////        // Create the SSLConnectionSocketFactory using the SSLContext
//////        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);
//////
//////        // Create an HttpClient with the SSLSocketFactory
//////        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
//////        HttpClients.custom()
//////                .setSSLSocketFactory(sslSocketFactory)
//////                .setConnectionManager(connectionManager)
//////                .build();
//////
//////        // Configure RestAssured to use the custom HttpClient with mTLS
//////        RestAssured.config = RestAssured.config().sslConfig(
//////                SSLConfig.sslConfig().sslSocketFactory(sslSocketFactory)
//////        );
//////    }
//////
////////    public void iHaveTheClientCertificateAndKey() throws Exception {
////////        // Load the PKCS12 keystore containing the client certificate and private key
////////        KeyStore keyStore = KeyStore.getInstance("PKCS12");
////////        try (FileInputStream keyStoreInput = new FileInputStream("src/test/resources/client-keystore.p12")) {
////////            keyStore.load(keyStoreInput, "yourpassword".toCharArray());  // Replace with the keystore password
////////        }
////////
////////        // Initialize KeyManagerFactory with the keystore (for the client certificate)
////////        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
////////        keyManagerFactory.init(keyStore, "yourpassword".toCharArray());  // Replace with the key password
////////
////////        // Initialize TrustManagerFactory (can use the default trust store, or use a custom one)
////////        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
////////        trustManagerFactory.init(keyStore);
////////
////////        // Create and initialize SSLContext with KeyManager and TrustManager
////////        SSLContext sslContext = SSLContext.getInstance("TLS");
////////        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
////////
////////        // Configure RestAssured to use this SSLContext
////////        RestAssured.config = RestAssured.config().sslConfig(
////////                io.restassured.config.SSLConfig.sslConfig().sslSocketFactory(sslContext.getSocketFactory())
////////        );
////////    }
//////
////////    @Given("I have the client certificate and key")
////////    public void iHaveTheClientCertificateAndKey() throws Exception {
////////        // Load the client certificate and private key from a keystore
////////        File keyStoreFile = new File("src/test/resources/keystore.jks");
////////        char[] keyStorePassword = "changeit".toCharArray();
////////
////////        KeyStore keyStore = KeyStore.getInstance("JKS");
////////        keyStore.load(new java.io.FileInputStream(keyStoreFile), keyStorePassword);
////////
////////        // Configure SSL context with client certificate
////////        SSLContext sslContext = SSLContexts.custom()
////////                .loadKeyMaterial(keyStore, keyStorePassword)
////////                .build();
////////
////////        RestAssured.config = RestAssured.config().sslConfig(sslConfig().sslSocketFactory(sslContext.getSocketFactory()));
//////////        GeneratePemCertificate.generateDummyCertificate("src/test/resources/dummy-cert.pem","src/test/resources/dummy-key.pem");
//////////
////////    }
//////
//////    @When("I make a GET request to the secured endpoint")
//////    public void iMakeAGETRequestToTheSecuredEndpoint() {
//////        response = RestAssured
//////                .given()
//////                .baseUri("https://secured-api.example.com") // Replace with your mTLS endpoint
//////                .get("/secured-endpoint");
//////    }
//////
//////    @Then("I should receive a {int} status code")
//////    public void iShouldReceiveAStatusCode(int expectedStatusCode) {
//////        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
//////    }
//////}
////
////
////import io.cucumber.java.en.Given;
////import io.cucumber.java.en.Then;
////import io.cucumber.java.en.When;
////import io.restassured.RestAssured;
////import io.restassured.config.SSLConfig;
////import io.restassured.response.Response;
////import org.apache.http.conn.ssl.SSLSocketFactory;
////import org.apache.http.impl.client.HttpClients;
////import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
////import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
////import org.apache.http.HttpVersion;
////import org.apache.http.impl.client.HttpClientBuilder;
////import org.testng.Assert;
////
////import javax.net.ssl.KeyManagerFactory;
////import javax.net.ssl.SSLContext;
////import javax.net.ssl.TrustManagerFactory;
////import java.io.FileInputStream;
////import java.security.KeyStore;
////
////public class MTLSTestSteps {
////    private Response response;
////
////    @Given("I have the client certificate and key")
////    public void iHaveTheClientCertificateAndKey() throws Exception {
////        // Load the PKCS12 keystore containing the client certificate and private key
////        KeyStore keyStore = KeyStore.getInstance("PKCS12");
////        try (FileInputStream keyStoreInput = new FileInputStream("src/test/resources/client-keystore.p12")) {
////            keyStore.load(keyStoreInput, "yourpassword".toCharArray());  // Replace with the keystore password
////        }
////
////        // Initialize KeyManagerFactory with the keystore (for the client certificate)
////        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
////        keyManagerFactory.init(keyStore, "yourpassword".toCharArray());  // Replace with the key password
////
////        // Initialize TrustManagerFactory (can use the default trust store, or use a custom one)
////        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
////        trustManagerFactory.init(keyStore);
////
////        // Create and initialize SSLContext with KeyManager and TrustManager
////        SSLContext sslContext = SSLContext.getInstance("TLS");
////        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
////
////        // Create SSLSocketFactory using the SSLContext
////        SSLSocketFactory sslSocketFactory = new SSLSocketFactory(sslContext);
////
////        // Create HttpClient with SSLSocketFactory
////        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
////        HttpClientBuilder httpClientBuilder = HttpClients.custom()
////                .setSSLSocketFactory(sslSocketFactory)
////                .setConnectionManager(connectionManager)
////                .setDefaultHttpVersion(HttpVersion.HTTP_1_1);
////
////        // Configure RestAssured to use this custom HttpClient with mTLS
////        RestAssured.config = RestAssured.config().sslConfig(
////                SSLConfig.sslConfig().sslSocketFactory(sslSocketFactory)
////        );
////    }
////
////    @When("I make a GET request to the secured endpoint")
////    public void iMakeAGETRequestToTheSecuredEndpoint() {
////        response = RestAssured
////                .given()
////                .baseUri("https://secured-api.example.com")  // Replace with your mTLS endpoint
////                .get("/secured-endpoint");
////    }
////
////    @Then("I should receive a {int} status code")
////    public void iShouldReceiveAStatusCode(int expectedStatusCode) {
////        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
////    }
////}
//package stepdefinitions;
//
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import io.restassured.RestAssured;
//import io.restassured.config.SSLConfig;
//import io.restassured.response.Response;
//import org.testng.Assert;
//
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManagerFactory;
//import java.io.FileInputStream;
//import java.security.KeyStore;
//
//public class MTLSTestSteps {
//    private Response response;
//
//    @Given("I have the client certificate and key")
//    public void iHaveTheClientCertificateAndKey() throws Exception {
//        // Load the PKCS12 keystore containing the client certificate and private key
//        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        try (FileInputStream keyStoreInput = new FileInputStream("src/test/resources/client-keystore.p12")) {
//            keyStore.load(keyStoreInput, "yourpassword".toCharArray());  // Replace with the keystore password
//        }
//
//        // Initialize KeyManagerFactory with the keystore (for the client certificate)
//        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        keyManagerFactory.init(keyStore, "yourpassword".toCharArray());  // Replace with the key password
//
//        // Initialize TrustManagerFactory (can use the default trust store, or use a custom one)
//        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        trustManagerFactory.init(keyStore);
//
//        // Create and initialize SSLContext with KeyManager and TrustManager
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
//
//        // Configure RestAssured to use this custom SSLContext
//        RestAssured.config = RestAssured.config().sslConfig(
//                SSLConfig.sslConfig().sslSocketFactory(sslContext.getSocketFactory())
//        );
//    }
//
//    @When("I make a GET request to the secured endpoint")
//    public void iMakeAGETRequestToTheSecuredEndpoint() {
//        response = RestAssured
//                .given()
//                .baseUri("https://secured-api.example.com")  // Replace with your mTLS endpoint
//                .get("/secured-endpoint");
//    }
//
//    @Then("I should receive a {int} status code")
//    public void iShouldReceiveAStatusCode(int expectedStatusCode) {
//        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
//    }
//}

package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

public class MTLSTestSteps {
    private Response response;

    @Given("I have the client certificate and key")
    public void iHaveTheClientCertificateAndKey() throws Exception {
        // Load the PKCS12 keystore containing the client certificate and private key
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream keyStoreInput = new FileInputStream("src/test/resources/certificates/1693731657.p12")) {
            keyStore.load(keyStoreInput, "test".toCharArray());  // Replace with the keystore password
        }

        // Initialize KeyManagerFactory with the keystore (for the client certificate)
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, "test".toCharArray());  // Replace with the key password

        // Initialize TrustManagerFactory (can use the default trust store, or use a custom one)
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        // Create and initialize SSLContext with KeyManager and TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        // Create Apache HttpClient's SSLSocketFactory using the SSLContext
        SSLSocketFactory sslSocketFactory = new SSLSocketFactory(sslContext);

        // Create HttpClient with SSLSocketFactory
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .setConnectionManager(connectionManager);

        // Configure RestAssured to use this custom HttpClient with mTLS
        RestAssured.config = RestAssured.config().sslConfig(
                SSLConfig.sslConfig().sslSocketFactory(sslSocketFactory)
        );
    }

    @When("I make a GET request to the secured endpoint")
    public void iMakeAGETRequestToTheSecuredEndpoint() {
        response = RestAssured
                .given()
                .baseUri("https://certauth.cryptomix.com")  // Replace with your mTLS endpoint
                .get("/json/");
    }

    @Then("I should receive a {int} status code")
    public void iShouldReceiveAStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    }
}
