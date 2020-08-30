package com.udyogg.services.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.udyogg.services.common.executer.TraceableWebFilter;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

import java.util.concurrent.TimeUnit;

import static reactor.netty.http.client.HttpClient.create;

@Configuration
public class CommonConfiguration {

//    @Autowired
//    CommonServiceProperties commonServiceProperties;

    @Bean
    public TraceableWebFilter traceableWebFilter() {
        return new TraceableWebFilter();
    }

//    @Bean
//    public ProfileServiceProperties profileServiceProperties() { return commonServiceProperties.getProfileservices(); }
//
//    @Bean
//    public IdentityServicesProperties identityServicesProperties() {return commonServiceProperties.getIdentityservices();}
//
//    @Bean
//    public LoyaltyServiceProperties loyaltyServiceProperties() {return  commonServiceProperties.getLoyaltyservices();}


    @Bean
    public WebClient webclient(
            ObjectMapper objectMapper,
            TraceableWebFilter traceableWebFilter
            ) {
        ExchangeStrategies strategies =
                ExchangeStrategies.builder().codecs(c -> configureCodecs(objectMapper, c)).build();

        HttpClient httpClient =
                create()
                        .tcpConfiguration(
                                tcpClient ->
                                        tcpClient
                                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                                                .runOn(LoopResources.create("reactor-webclient"))
                                                .doOnConnected(
                                                        connection ->
                                                                connection
                                                                        .addHandlerLast(
                                                                                new ReadTimeoutHandler(
                                                                                        60000,
                                                                                        TimeUnit.MILLISECONDS))
                                                                        .addHandlerLast(
                                                                                new WriteTimeoutHandler(
                                                                                        60000, TimeUnit.MILLISECONDS))));

        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        WebClient.Builder builder =
                WebClient.builder()
                        .exchangeStrategies(strategies)
                        .clientConnector(connector)
                        .filter(traceableWebFilter);

        return builder.build();
    }



    private void configureCodecs(
            ObjectMapper objectMapper, ClientCodecConfigurer clientCodecConfigurer) {
        clientCodecConfigurer.customCodecs().decoder(new Jackson2JsonDecoder(objectMapper));
        clientCodecConfigurer.customCodecs().encoder(new Jackson2JsonEncoder(objectMapper));
    }

}
