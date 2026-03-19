package org.acme.port;

import org.acme.application.dto.UserRequest;
import org.acme.domain.entity.RoleName;
import org.acme.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServicePort {

    public List<User> getAll();

    public Optional<User> findUserByCF(String cf);

    public Optional<User> findUserByEmail(String mail);

    public Optional<User> findByPhone(Long number);

    public List<User> findByName(String name);

    public List<User> findBySurname(String surname);

    public User createUser(UserRequest request);

    public Optional<User> updateUser(String cf,String mail,Long number);

    public boolean deleteUser(String cf);

    public Optional<String> validateUser(UserRequest request);

    public List<User> findByRole(RoleName roleName);

}
