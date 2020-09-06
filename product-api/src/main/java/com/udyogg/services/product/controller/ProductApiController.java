package com.udyogg.services.product.controller;

import com.udyogg.services.product.handler.ProductHandler;
import com.udyogg.services.product.models.streams.ProductStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(
        path = {"/api/v1/products"},
        consumes = {MediaType.APPLICATION_JSON_VALUE}
)
public final class ProductApiController {

    @Autowired
    ProductHandler productHandler;


    @GetMapping
    public DeferredResult<ResponseEntity<?>> getProducts(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestHeader(name = "TraceId", required = true) String traceId,
            @RequestHeader(name = "ClientId", required = true ) String clientId,
            @RequestHeader(name = "ClientName", required = false) String clientName
    ) {

        long start = System.currentTimeMillis();
        ProductStream productStream = ProductStream.builder().build();
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
//        Mono.just(productStream)
//                .flatMap(getProductHandler::handle)
//                .subscriberContext(reactiveContext.initReactiveLocalContext(LoggerConstants.APIS.CREATE_PASS_API.value()))
//                .subscribeOn(Schedulers.parallel())
//                .subscribe(
//                        stream -> createPassHandler.respond(stream, deferredResult, CommonUtils.getTimeInString(System.currentTimeMillis() - start)),
//                        error -> createPassHandler.respond(devcieType,
//                                CustomException.wrap(error),
//                                deferredResult,
//                                CommonUtils.getTimeInString(System.currentTimeMillis() - start))
//                );

        return deferredResult;
    }

}
