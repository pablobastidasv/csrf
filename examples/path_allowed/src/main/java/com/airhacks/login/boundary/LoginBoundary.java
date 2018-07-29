package com.airhacks.login.boundary;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginBoundary {
    @POST
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ){

        if("admin".equalsIgnoreCase(username)
            && "admin".equals(password)){
            return Response.ok(tokenResponse()).build();
        }

        return Response.status(401).build();
    }

    private JsonObject tokenResponse() {
        return Json.createObjectBuilder()
                .add("token", "my_super_secure_token")
                .build();
    }
}
