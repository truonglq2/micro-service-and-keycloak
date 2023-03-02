package com.programming.techie.productservice.controller;

import com.programming.techie.productservice.model.Product;
import com.programming.techie.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check")
@RequiredArgsConstructor
public class ProductController {

    @Value("${test.name}")
    private String name;
    private final ProductRepository productRepository;

    @GetMapping
    public String test() {
        return name;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        productRepository.save(product);
    }
}
