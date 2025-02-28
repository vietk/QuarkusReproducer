package org.acme;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/find")
public class ResourceFinder {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String find(@QueryParam("resourcePath") String resourcePath) throws IOException {

        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        StringBuilder builder = new StringBuilder();
        for (Resource resource : resourcePatternResolver.getResources(resourcePath)) {
            builder.append(resource.getURI());
            builder.append("\n");
        }

        return builder.toString();
    }
}
