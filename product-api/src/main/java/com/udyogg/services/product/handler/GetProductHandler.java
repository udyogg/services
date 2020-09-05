package com.udyogg.services.product.handler;

import com.udyogg.services.database.entities.Product;
import com.udyogg.services.database.repository.ProductRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetProductHandler {

  @Autowired
  ProductRepository productRepository;


  public List<Product> handle() {
    List<Product> products = new ArrayList<>();
    Collections.addAll(productRepository.findAll());
    return products;
  }
}
