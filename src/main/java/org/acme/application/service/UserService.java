package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.application.dto.UserRequest;
import org.acme.domain.entity.Role;
import org.acme.domain.entity.RoleName;
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

    public List<User> findByRole(RoleName roleName){

        return userRepository.findByRole(roleName);
    }

    @Transactional
    public Optional<String> validateUser(UserRequest request) {

        if (request.roles == null || request.roles.isEmpty()) {
            return Optional.of("Almeno un ruolo è obbligatorio");
        }
        for (String roleName : request.roles) {
            try {
                RoleName.valueOf(roleName.toUpperCase());
            } catch (IllegalArgumentException e) {
                return Optional.of("Ruolo non valido: " + roleName);
            }
        }
        if (userRepository.findByCF(request.codiceFiscale).isPresent()) {
            return Optional.of("Codice fiscale già registrato");
        }
        if (userRepository.findByEmail(request.mail).isPresent()) {
            return Optional.of("Mail già registrata");
        }
        if (request.phone != null && userRepository.findByPhone(request.phone).isPresent()) {
            return Optional.of("Numero di telefono già registrato");
        }

        return Optional.empty();
    }

    @Transactional
    public User createUser(UserRequest request){

        User u = new User();
        u.setCodiceFiscale(request.codiceFiscale);
        u.setName(request.name);
        u.setSurname(request.surname);
        u.setMail(request.mail);
        u.setPhone(request.phone);

        for (String roleName : request.roles) {
            Role role = new Role();
            role.setName(RoleName.valueOf(roleName.toUpperCase()));
            u.getRoles().add(role);
        }

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
