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
 
    /**
    if we want to cache the result obtain by the api 
    @Cacheable(key = "#id", value = "Product")
    if you want to cache the result based on the condition then, apply as below
    in this, if price is less than or equal to 1000, then only it will store the value in cache
    @Cacheable is an annotation typically used in Java Spring applications to configure caching behavior for methods.
    key: The key attribute specifies the key that will be used to identify the cached data. 
         In your example, key = "#id" means that the value of the id parameter (or method argument) will be used as the cache key.
         The # symbol is used to indicate that the id parameter is a placeholder, and its actual value will be used as the cache key.
    value: The value attribute specifies the name of the cache where the result of the annotated method will be stored.
    In your example, value = "Product" means that the cached data will be stored in a cache named "Product." 
    This allows you to have multiple caches with different names in your application, and you can configure each cache independently.
    Additionally, there is an unless attribute:
    unless: The unless attribute is a conditional expression that determines whether the result should be cached or not. 
    In below example, unless = "#result.price > 1000" means that the result of the method will not be cached if the price of the result object is greater than 1000. 
    If this condition is met, the method will be executed every time without using the cache.
    In summary, key specifies the cache key based on a method parameter (id in this case), and value specifies the name of the cache where the result will be stored. The unless attribute provides a condition to control when caching should be skipped based on the result of the method.
    **/
    @Override
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
