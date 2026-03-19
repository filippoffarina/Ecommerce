package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.entity.User;
import org.acme.domain.entity.UserRepository;
import org.acme.port.UserServicePort;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService implements UserServicePort {

    @Inject
    UserRepository userRepository;


    public List<User> getAll(){

        return userRepository.listAll();
    }

    public Optional<User> findUserByCF(String cf){

        return userRepository.findByCF(cf);
    }

    public Optional<User> findUserByEmail(String mail){

        return userRepository.findByEmail(mail);
    }

    public Optional<User> findByPhone(Long number){

        return userRepository.findByPhone(number);
    }

    public List<User> findByName(String name){

        return userRepository.findByName(name);
    }

    public List<User> findBySurname(String surname){

        return userRepository.findBySurname(surname);
    }

    @Transactional
    public Optional<String> validateUser(User u) {

        if (userRepository.findByCF(u.getCodiceFiscale()).isPresent()) {
            return Optional.of("Codice fiscale già registrato");
        }
        if (userRepository.findByEmail(u.getMail()).isPresent()) {
            return Optional.of("Mail già registrata");
        }
        if (u.getPhone() != null && userRepository.findByPhone(u.getPhone()).isPresent()) {
            return Optional.of("Numero di telefono già registrato");
        }

        return Optional.empty();
    }

    @Transactional
    public User createUser(User u){

        userRepository.persist(u);
        return u;
    }

    @Transactional
    public Optional<User> updateUser(String cf, String mail, Long number){

        return userRepository.findByCF(cf)
                .map(u ->{
                    if (mail != null) {
                        u.setMail(mail);
                    }
                    if (number != null) {
                        u.setPhone(number);
                    }
                    return u;
                });
    }

    @Transactional
    public boolean deleteUser(String cf){

        return userRepository.deleteByCF(cf);
    }


}
