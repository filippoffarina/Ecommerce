package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.entity.User;
import org.acme.domain.entity.UserRepository;
import org.acme.port.AuthServicePort;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AuthService implements AuthServicePort {

    @Inject
    UserRepository userRepository;

    @Inject
    SessionStore sessionStore;

    public Optional<String> login(String mail, String password){

        Optional<User> userOpt = userRepository.findByEmail(mail);

        if(userOpt.isEmpty())
            return Optional.empty();

        User u = userOpt.get();

        if(!u.getPassword().equals(password))
            return Optional.empty();

        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        sessionStore.save(token,u);

        return Optional.of(token);
    }

    public void logout(String token){
        sessionStore.remove(token);
    }

    public Optional<User> getUserFromToken(String token){

        return Optional.ofNullable(sessionStore.get(token));
    }
}
