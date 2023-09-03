package com.example.vivek.springbootredistutorialapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.vivek.springbootredistutorialapp.model.Product;
import com.example.vivek.springbootredistutorialapp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    
    private static final String HASH_KEY = "Product";
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Product save(Product product) {
        redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        List<Object> objectList = redisTemplate.opsForHash().values(HASH_KEY);
        List<Product> list = objectList.stream()
    .map(obj -> modelMapper.map(obj, Product.class))
    .collect(Collectors.toList());
    return list;

    }

    @Override
    public Product findProductById(int id) {
        return (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    @Override
    public String deleteProduct(int id) {
        redisTemplate.opsForHash().delete(HASH_KEY,id);
        return "product deleted";
    }
}
