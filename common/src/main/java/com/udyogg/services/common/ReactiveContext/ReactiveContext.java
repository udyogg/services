package com.udyogg.services.common.ReactiveContext;


import com.udyogg.services.common.logging.LoggerConstants;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import reactor.util.function.Tuple2;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * class for initialising context object(threadlocal) and
 * updating those during thread switching and reactive programming
 *
 * @author Ravi Singh
 * @created 16/04/2020 - 14:39
 * @project contra
 */
@Component
public final class ReactiveContext {

    private ReactiveContext() {}

    @PostConstruct
    void init() {
        Creator.INSTANCE = this;
    }

    public static ReactiveContext instance() {
        return Creator.INSTANCE;
    }

    private static final class Creator {

        /** The instance. */
        private static ReactiveContext INSTANCE = new ReactiveContext();
    }


    /**
     * initialise local context object (threadlocal) for each incoming request
     * @param traceId
     * @param clientId
     * @param clientName
     */
    public void initLocalContext(String traceId,  String clientId, String clientName) {

        Contexts.clear();
        MDC.clear();

        MDC.put(LoggerConstants.KEYS.TRACEID.value(), traceId);
        MDC.put(LoggerConstants.KEYS.CLIENT_NAME.value(), clientName);
        MDC.put(LoggerConstants.KEYS.CLIENT_ID.value(), clientId);

        Contexts.LocalContext current = Contexts.current();
        current.put(LoggerConstants.KEYS.TRACEID.value(), traceId);
        current.put(LoggerConstants.KEYS.CLIENT_NAME.value() ,clientName);
        current.put(LoggerConstants.KEYS.CLIENT_ID.value(), clientId);
    }
    private <T extends Object> Mono<T> continueStream(Tuple2<Object, Context> anyAndContext) {
        return continueStream(anyAndContext.getT1(), anyAndContext.getT2());
    }

    private <T extends Object> Mono<T> continueStream(Object any, Context context) {
        updateLocalContext(context);

        Mono<T> continuation;
        Throwable throwable = null;
        T event = null;


        if (any instanceof Throwable) {
            throwable = (Throwable) any;
            continuation = Mono.error(throwable);
        }
        else {
            event = (T) any;
            continuation = Mono.just(event);
        }
        return continuation;
    }


    private <T extends Object> Mono<T> continueStream(Context context) {
        updateLocalContext(context);
        return Mono.empty();
    }


    private void updateLocalContext(Context context) {
        updateLocalContext(LoggerConstants.KEYS.TRACEID.value(), context.getOrEmpty(LoggerConstants.KEYS.TRACEID.value()));
        updateLocalContext(LoggerConstants.KEYS.CLIENT_ID.value(), context.getOrEmpty(LoggerConstants.KEYS.CLIENT_ID.value()));
        updateLocalContext(LoggerConstants.KEYS.CLIENT_NAME.value(), context.getOrEmpty(LoggerConstants.KEYS.CLIENT_NAME.value()));
        updateLocalContext(LoggerConstants.KEYS.API.value(), context.getOrEmpty(LoggerConstants.KEYS.API.value()));
    }


    private void updateLocalContext(String key, Optional<String> value) {
        if(value.isPresent()) {
            String v = value.get();
            MDC.put(key,  v);

            // Add to thread local
            Contexts.LocalContext current = Contexts.current();
            current.put(key, v);
        }
    }

    /**
     * updating reactive context object, that will be used in copying context object
     * from one thread to another thread during asynchronous reactive execution of Mono
     * Flux
     *
     * @param api
     * @return
     */
    public Context initReactiveLocalContext(String api) {
        String traceId = Contexts.current().get(LoggerConstants.KEYS.TRACEID.value());
        updateLocalContext(LoggerConstants.KEYS.API.value(), Optional.of(api));
        return Context.empty()
                .put(LoggerConstants.KEYS.TRACEID.value(), traceId)
                .put(LoggerConstants.KEYS.API.value(), api)
                .put(LoggerConstants.KEYS.CLIENT_NAME.value(), MDC.get(LoggerConstants.KEYS.CLIENT_NAME.value()))
                .put(LoggerConstants.KEYS.CLIENT_ID.value(), MDC.get(LoggerConstants.KEYS.CLIENT_ID.value()));

    }


    /**
     * this method will get called to copy context object when webClient
     * switch threads during executing api calls.
     *
     * @param source
     * @param <T>
     * @return
     */
    public <T extends Object> Mono<T> contextualize(Mono<T> source) {
        return (Mono<T>)
                source
                        .cast(Object.class)
                        .onErrorResume(Mono::just)
                        .zipWith(Mono.subscriberContext())
                        .flatMap(this::continueStream)
                        .switchIfEmpty(Mono.subscriberContext().flatMap(this::continueStream));
    }

    public void updateLocalContext(Contexts.LocalContext context) {
        Contexts.init(context);
        updateLocalContext(LoggerConstants.KEYS.TRACEID.value(), Optional.of(context.get(LoggerConstants.KEYS.TRACEID.value())));
        updateLocalContext(LoggerConstants.KEYS.CLIENT_ID.value(), Optional.of(context.get(LoggerConstants.KEYS.CLIENT_ID.value())));
        updateLocalContext(LoggerConstants.KEYS.CLIENT_NAME.value(), Optional.of(context.get(LoggerConstants.KEYS.CLIENT_NAME.value())));
        updateLocalContext(LoggerConstants.KEYS.API.value(), Optional.of(context.get(LoggerConstants.KEYS.API.value())));
    }


}
