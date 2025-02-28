package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ResourceFinderTest {
  @Test
  void testFindEndpoint() throws IOException {
    String path = "classpath*:org/acme/**/*.class";
    String response = given()
        .queryParam("resourcePath", path)
        .when().get("/find/")
        .then()
        .statusCode(200)
        .extract().asString();

    Assertions.assertTrue(response.contains("org/acme/ResourceFinder.class"));
  }
}