package com.github.me;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@RunWith(JUnitPlatform.class)
public class ServiceTest {

  @TestHTTPResource("/test/service")
  String serviceUri;

  @Test
  public void testService() {
    get(serviceUri).then().statusCode(200).body(Matchers.is("hello"));
  }
}
