package com.dpensky.desafioanotaai.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dpensky.desafioanotaai.domain.category.Category;
import com.dpensky.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.dpensky.desafioanotaai.domain.products.Product;
import com.dpensky.desafioanotaai.domain.products.ProductDTO;
import com.dpensky.desafioanotaai.domain.products.exceptions.ProductNotFoundException;
import com.dpensky.desafioanotaai.repositories.ProductRepository;
import com.dpensky.desafioanotaai.services.aws.AwsSnsService;
import com.dpensky.desafioanotaai.services.aws.MessageDTO;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final AwsSnsService snsService;

    public ProductService(ProductRepository repository, CategoryService categoryRepository, AwsSnsService snsService) {
        this.productRepository = repository;
        this.categoryService = categoryRepository;
        this.snsService = snsService;
    }

    public Product insert(ProductDTO productData) {
        Category category = this.categoryService
                .getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);
        newProduct.setCategory(category);
        this.productRepository.save(newProduct);
        this.snsService.publish(new MessageDTO(newProduct.getOwnerId()));
        return newProduct;
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    public Product update(String id, ProductDTO productData) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        if (productData.categoryId() != null) {
            this.categoryService.getById(productData.categoryId())
                    .ifPresent(product::setCategory);
        }
        if (!productData.title().isEmpty())
            product.setTitle(productData.title());
        if (!productData.description().isEmpty())
            product.setDescription(productData.description());
        if (productData.price() != null)
            product.setPrice(productData.price());
        this.productRepository.save(product);
        this.snsService.publish(new MessageDTO(product.getOwnerId()));
        return product;
    }

    public void delete(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        this.productRepository.delete(product);
    }
}
