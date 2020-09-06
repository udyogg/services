package com.udyogg.services.product.handler;

import com.udyogg.services.product.models.streams.ProductStream;
import reactor.core.publisher.Mono;

/**
 * @author Ravi Singh
 * @date 04/09/20-09-2020
 * @project udyogg
 */
public class GenericProductHandler implements IProductHandler {

    @Override
    public Mono<ProductStream> getProducts() {
        return null;
    }

    @Override
    public Mono<ProductStream> updateProducts() {
        return null;
    }

    @Override
    public Mono<ProductStream> createProducts() {
        return null;
    }

    @Override
    public Mono<ProductStream> deleteProducts() {
        return null;
    }
}
