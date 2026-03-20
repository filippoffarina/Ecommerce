package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.application.dto.UserRequest;
import org.acme.domain.entity.*;
import org.acme.port.ProductServicePort;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService implements ProductServicePort {

    @Inject
    ProductRepository productRepository;

    public List<Product> getAll(){

        return productRepository.listAll();
    }

    public Optional<Product> findProductById(Long id){

        return productRepository.findByKey(id);
    }

    public Optional<Product> findProductByName(String name){

        return productRepository.findByName(name);
    }

    public List<Product> findProductBySector(String sector){

        return productRepository.findBySector(sector);
    }

    @Transactional
    public Optional<Product> updateProduct(Long id, String name, String sector,int price){

        return productRepository.findByKey(id)
                .map(p ->{
                    if (name != null) {
                        p.setName(name);
                    }
                    if (sector != null) {
                        p.setSector(sector);
                    }
                    if (price != productRepository.findByKey(id).get().getPrice()){
                        p.setPrice(price);
                    }
                    return p;
                });
    }


    @Transactional
    public boolean deleteProduct(Long id){

        return productRepository.deleteById(id);
    }

    @Transactional
    public Product addProduct(Product p){

        productRepository.persist(p);
        return p;
    }
}
