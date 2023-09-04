package com.example.vivek.springbootredistutorialapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    // if we want to cache the result obtain by the api 
    // @Cacheable(key = "#id", value = "Product")
    // if you want to cache the result based on the condition then, apply as below
    //in this, if price is less than or equal to 1000, then only it will store the value in cache
    @Cacheable(key = "#id", value = "Product", unless = "#result.price > 1000")
    public Product findProductById(int id) {
        System.out.println("inside findProductById() ::: ");
        return (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    @Override
    @CacheEvict(key = "#id", value = "Product")
    public String deleteProduct(int id) {
        redisTemplate.opsForHash().delete(HASH_KEY,id);
        return "product deleted";
    }
}
