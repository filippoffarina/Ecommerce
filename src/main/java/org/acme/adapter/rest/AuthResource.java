package org.acme.adapter.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.port.AuthServicePort;

import java.util.Optional;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AuthResource {

    @Inject
    AuthServicePort authService;

    @POST
    @Path("/login")
    public Response login(@QueryParam("mail") String mail,
                          @QueryParam("password") String password) {

        Optional<String> token = authService.login(mail, password);

        return token.isPresent()
                ? Response.ok("{\"token\":\"" + token.get() + "\"}").build()
                : Response.status(Response.Status.UNAUTHORIZED)
                .entity("Mail o password errati").build();
    }

    @POST
    @Path("/logout")
    public Response logout(@QueryParam("token") String token) {

        authService.logout(token);
        return Response.ok("Logout effettuato").build();

    }
}
