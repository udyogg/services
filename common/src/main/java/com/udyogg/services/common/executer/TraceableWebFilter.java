package com.udyogg.services.common.executer;

import com.udyogg.services.common.ReactiveContext.ReactiveContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * A web filter that will be used by webClient on receiving api calls response in a
 * another thread. This will help in keeping our thread local data otherwise it will get lost
 *
 * @author Ravi Singh
 * @created 19/04/2020 - 20:11
 * @project contra
 */
public final class TraceableWebFilter implements ExchangeFilterFunction {

    @Autowired
    ReactiveContext reactiveContext;


    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        final ClientRequest.Builder builder = ClientRequest.from(request);
        Mono<ClientResponse> exchange =
                Mono.defer(() -> next.exchange(builder.build()))
                        .transform(reactiveContext::contextualize)
                        .publishOn(Schedulers.parallel());
        return exchange;
    }

}
