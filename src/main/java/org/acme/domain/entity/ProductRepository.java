package org.acme.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public Optional<Product> findByKey(Long id){

        return find("Id",id).firstResultOptional();
    }

    public Optional<Product> findByName(String name){

        return find("name",name).firstResultOptional();
    }

    public List<Product> findBySector(String sector){

        return list("sector",sector);
    }

    public boolean deleteById(Long id){

        Optional<Product> productT = findByKey(id);

        if(productT.isPresent()){

            delete(productT.get());
            return true;
        }

        return false;

    }

}
