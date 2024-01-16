package com.dpensky.desafioanotaai.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dpensky.desafioanotaai.domain.products.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    
}
