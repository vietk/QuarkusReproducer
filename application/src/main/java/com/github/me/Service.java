package com.github.me;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.google.common.collect.FluentIterable;

@Path("/test")
@RunWith(JUnitPlatform.class)
public class Service {


  private final static String[] HELLO_ARRAY = {"hello"};

  @GET
  @Path("service")
  @Produces(MediaType.TEXT_PLAIN)
  public String service() {
    return FluentIterable.of(HELLO_ARRAY).get(0);
  }
}
