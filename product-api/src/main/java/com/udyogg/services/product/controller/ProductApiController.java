package com.udyogg.services.product.controller;

import com.udyogg.services.database.entities.Product;
import com.udyogg.services.product.handler.GetProductHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = {"/api/v1/products"},
    consumes = {MediaType.APPLICATION_JSON_VALUE}
)
public final class ProductApiController {

  @Autowired
  GetProductHandler getProductHandler;


  @GetMapping(path = "/product/show/{id}")
  public List<Product> getProducts(
      @RequestHeader(name = "Authorization", required = true) String token,
      @RequestHeader(name = "TraceId", required = true) String traceId,
      @RequestHeader(name = "ClientId", required = true) String clientId,
      @RequestHeader(name = "ClientName", required = false) String clientName) {
    return getProductHandler.handle();
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

  }

}
