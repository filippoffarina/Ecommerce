package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.entity.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class SessionStore {

    private final Map<String, User> sessions = new ConcurrentHashMap<>();

    public void save(String token, User user){
        sessions.put(token,user);
    }

    public User get(String token){
        return sessions.get(token);
    }

    public void remove(String token) {
        sessions.remove(token);
    }

    public boolean exists(String token) {
        return sessions.containsKey(token);
    }
}
