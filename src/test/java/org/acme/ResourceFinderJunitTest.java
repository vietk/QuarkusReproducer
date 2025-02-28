package org.acme;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceFinderJunitTest {

  @Test
  public void testFindEndpoint() throws IOException {

    ResourceFinder resource = new ResourceFinder();
    String resources = resource.find("classpath*:org/acme/**/*.class");
    Assertions.assertTrue(resources.contains("org/acme/ResourceFinder.class"));
  }
}