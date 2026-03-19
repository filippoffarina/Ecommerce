package org.acme.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Optional<User> findByCF(String cf){

        return find("codiceFiscale",cf).firstResultOptional();
    }

    public Optional<User> findByEmail(String email){

        return find("mail",email).firstResultOptional();
    }

    public Optional<User> findByPhone(Long number){

        return find("phone",number).firstResultOptional();
    }

    public List<User> findByName(String name){

        return list("name",name);
    }

    public List<User> findBySurname(String surname){

        return list("surname",surname);
    }

    public List<User> findByRole(RoleName roleName){
        return find("SELECT u FROM User u JOIN u.roles r WHERE r.name = ?1",roleName).list();
    }

    public boolean deleteByCF(String cf){

        Optional<User> userT = findByCF(cf);

        if( userT.isPresent()){

            delete(userT.get());
            return true;
        }

        return false;
    }
}
