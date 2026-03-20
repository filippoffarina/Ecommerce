package org.acme.port;

import org.acme.domain.entity.User;

import java.util.Optional;

public interface AuthServicePort {

    public Optional<String> login(String mail, String password);

    public void logout(String token);

    public Optional<User> getUserFromToken(String token);
}
