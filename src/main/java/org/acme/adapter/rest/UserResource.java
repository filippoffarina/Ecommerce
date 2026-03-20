package org.acme.adapter.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.UserRequest;
import org.acme.domain.entity.RoleName;
import org.acme.domain.entity.User;
import org.acme.port.AuthCheckerPort;
import org.acme.port.UserServicePort;

import java.util.List;
import java.util.Optional;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserServicePort userService;

    @Inject
    AuthCheckerPort authChecker;

    @POST
    public Response addUser(@QueryParam("token") @DefaultValue("") String token,
                            UserRequest request) {

        boolean dbVuoto = userService.getAll().isEmpty();

        if (!dbVuoto) {
            Response error = authChecker.check(token, RoleName.ADMIN);
            if (error != null) return error;
        }

        Optional<String> validationError = userService.validateUser(request);
        if (validationError.isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(validationError.get()).build();
        }

        return Response.status(201).entity(userService.createUser(request)).build();
    }

    @PUT
    @Path("/updateProfile")
    public Response update(@QueryParam("token") @DefaultValue("") String token,
                           @QueryParam("cf") String cf,
                           @QueryParam("mail") String mail,
                           @QueryParam("phone") Long number) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        return userService.updateUser(cf, mail, number)
                .map(u -> Response.ok(u).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/roles/add")
    public Response addRole(@QueryParam("token") @DefaultValue("") String token,
                            @QueryParam("cf") String cf,
                            @QueryParam("role") String role) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        Optional<String> err = userService.addRole(cf, role);
        return err.isPresent()
                ? Response.status(Response.Status.BAD_REQUEST).entity(err.get()).build()
                : Response.ok("Ruolo aggiunto con successo").build();
    }

    @DELETE
    @Path("/roles/remove")
    public Response removeRole(@QueryParam("token") @DefaultValue("") String token,
                               @QueryParam("cf") String cf,
                               @QueryParam("role") String role) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        Optional<String> err = userService.removeRole(cf, role);
        return err.isPresent()
                ? Response.status(Response.Status.BAD_REQUEST).entity(err.get()).build()
                : Response.ok("Rule removed").build();
    }

    @DELETE
    @Path("/deleteProfile")
    public Response delete(@QueryParam("token") @DefaultValue("") String token,
                           @QueryParam("cf") String cf) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        Boolean deleted = userService.deleteUser(cf);
        return deleted
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response getAll(@QueryParam("token") @DefaultValue("") String token) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        return Response.ok(userService.getAll()).build();
    }

    @GET
    @Path("/search/fiscalcode")
    public Response findByCF(@QueryParam("token") @DefaultValue("") String token,
                             @QueryParam("cf") String cf) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        return userService.findUserByCF(cf)
                .map(u -> Response.ok(u).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/search/email")
    public Response findByMail(@QueryParam("token") @DefaultValue("") String token,
                               @QueryParam("mail") String mail) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        return userService.findUserByEmail(mail)
                .map(u -> Response.ok(u).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/search/phone")
    public Response findByPhone(@QueryParam("token") @DefaultValue("") String token,
                                @QueryParam("phone") Long number) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        return userService.findByPhone(number)
                .map(u -> Response.ok(u).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/search/name")
    public Response findByName(@QueryParam("token") @DefaultValue("") String token,
                               @QueryParam("name") String name) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        return Response.ok(userService.findByName(name)).build();
    }

    @GET
    @Path("/search/surname")
    public Response findBySurname(@QueryParam("token") @DefaultValue("") String token,
                                  @QueryParam("surname") String surname) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        return Response.ok(userService.findBySurname(surname)).build();
    }

    @GET
    @Path("/search/role")
    public Response findByRole(@QueryParam("token") @DefaultValue("") String token,
                               @QueryParam("role") String role) {

        Response error = authChecker.check(token, RoleName.ADMIN);
        if (error != null) return error;

        try {
            RoleName roleName = RoleName.valueOf(role.toUpperCase());
            return Response.ok(userService.findByRole(roleName)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Ruolo non valido: " + role).build();
        }
    }


}
