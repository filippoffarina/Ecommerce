package org.acme.port;

import org.acme.domain.entity.Product;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductServicePort {

    public List<Product> getAll();

    public Optional<Product> findProductById(Long id);

    public Optional<Product> findProductByName(String name);

    public List<Product> findProductBySector(String sector);

    public Optional<Product> updateProduct(Long id,String name,String sector,int price);

    public boolean deleteProduct(Long id);

    public Product addProduct(Product p);

}
