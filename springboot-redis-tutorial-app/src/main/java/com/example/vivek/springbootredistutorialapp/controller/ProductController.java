package com.example.vivek.springbootredistutorialapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vivek.springbootredistutorialapp.model.Product;
import com.example.vivek.springbootredistutorialapp.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product){
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> listOfProducts = productService.findAll();
        return ResponseEntity.ok(listOfProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable int id){
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        String deleteProduct = productService.deleteProduct(id);
        return ResponseEntity.ok(deleteProduct);
    }

}
