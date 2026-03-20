package org.acme.adapter.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.UserRequest;
import org.acme.domain.entity.RoleName;
import org.acme.domain.entity.User;
import org.acme.port.UserServicePort;

import java.util.List;
import java.util.Optional;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserServicePort userService;

    @POST
    public Response addUser(UserRequest request) {

        Optional<String> error = userService.validateUser(request);

        if (error.isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(error.get())
                    .build();
        }

        User newUser = userService.createUser(request);
        return Response.status(201).entity(newUser).build();
    }

    @PUT
    @Path("/updateProfile")
    public Response update(@QueryParam("cf") String cf,@QueryParam("mail") String mail,@QueryParam("phone") Long number){

        return userService.updateUser(cf, mail, number)
                .map(u -> Response.ok(u).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }

    @PUT
    @Path("/cf/roles/add")
    public Response addRole(@QueryParam("cf") String cf, @QueryParam("role") String role) {

        Optional<String> error = userService.addRole(cf, role);

        return error.isPresent()
                ? Response.status(Response.Status.BAD_REQUEST).entity(error.get()).build()
                : Response.ok("Ruolo aggiunto con successo").build();
    }

    @DELETE
    @Path("/cf/roles/remove")
    public Response removeRole(@QueryParam("cf") String cf, @QueryParam("role") String role) {

        Optional<String> error = userService.removeRole(cf, role);

        return error.isPresent()
                ? Response.status(Response.Status.BAD_REQUEST).entity(error.get()).build()
                : Response.ok("Rule removed").build();
    }

    @DELETE
    @Path("/deleteProfile")
    public Response delete(@QueryParam("cf") String cf){

        Boolean deleted = userService.deleteUser(cf);

        return deleted
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    public List<User> getAll(){

        return userService.getAll();
    }

    @GET
    @Path("/search/fiscalcode")
    public Optional<User> findByCF(@QueryParam("cf") String cf){

        return userService.findUserByCF(cf);
    }

    @GET
    @Path("/search/email")
    public Optional<User> findByMail(@QueryParam("mail") String mail){

        return userService.findUserByEmail(mail);
    }

    @GET
    @Path("/search/phone")
    public Optional<User> findByPhone(@QueryParam("phone") Long number){

        return userService.findByPhone(number);
    }

    @GET
    @Path("/search/name")
    public List<User> findByName(@QueryParam("name") String name){

        return userService.findByName(name);
    }

    @GET
    @Path("/search/surname")
    public List<User> findBySurname(@QueryParam("surname") String surname){

        return userService.findBySurname(surname);
    }

    @GET
    @Path("/search/role")
    public List<User> findByRole(@QueryParam("role") String role)
    {

        RoleName roleName = RoleName.valueOf(role.toUpperCase());

        return  userService.findByRole(roleName);
    }


}
