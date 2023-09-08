package com.example.vivek.springbootredistutorialapp.service;

import java.util.List;
import com.example.vivek.springbootredistutorialapp.model.Product;

public interface ProductService {
    public Product save(Product product);
    public List<Product> findAll();
    public Product findProductById(int id);
    public String deleteProduct(int id);
    public void clearAllData();
}
