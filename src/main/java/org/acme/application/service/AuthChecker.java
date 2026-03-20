package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.acme.domain.entity.RoleName;
import org.acme.domain.entity.User;
import org.acme.port.AuthCheckerPort;
import org.acme.port.AuthServicePort;

import java.util.Optional;

@ApplicationScoped
public class AuthChecker implements AuthCheckerPort {

    @Inject
    AuthService authService;

    public Response check(String token, RoleName roleRequired)
    {
        System.out.println("Token ricevuto: '" + token + "'");

        if (token == null || token.isBlank()) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Missed Token").build();
        }

        Optional<User> userOpt = authService.getUserFromToken(token);

        if (userOpt.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Unvalid token or expired session").build();
        }

        User u = userOpt.get();
        boolean hasRole = u.getRoles().stream()
                .anyMatch(r -> r.getName() == roleRequired);

        if (!hasRole) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("You don't have the permission").build();
        }

        return null;
    }
}
