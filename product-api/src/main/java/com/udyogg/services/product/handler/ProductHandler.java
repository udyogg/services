package com.udyogg.services.product.handler;

import com.udyogg.services.product.models.streams.ProductStream;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler implements IProductHandler {

    public Mono<ProductStream> getProducts() {
        return null;
    }

    public Mono<ProductStream> createProducts() {
        return  null;
    }

    public Mono<ProductStream> updateProducts() {
        return null;
    }

    public Mono<ProductStream> deleteProducts() {
        return null;
    }

}
