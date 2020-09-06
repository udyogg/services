package com.udyogg.services.product.handler;

import com.udyogg.services.product.models.streams.ProductStream;
import reactor.core.publisher.Mono;

/**
 * @author Ravi Singh
 * @date 04/09/20-09-2020
 * @project eduwars
 */
public interface IProductHandler {

    Mono<ProductStream> getProducts();

    Mono<ProductStream> updateProducts();

    Mono<ProductStream> createProducts();

    Mono<ProductStream> deleteProducts();
}
