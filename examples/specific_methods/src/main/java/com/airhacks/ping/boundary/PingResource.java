package com.airhacks.ping.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author airhacks.com
 */
@Path("ping")
public class PingResource {

    @GET
    public String ping() {
        return "Enjoy Java EE 8!, I don't care CSRF";
    }

    @POST
    public String important(){
        return "Your CSRF looks great!!";
    }
}
