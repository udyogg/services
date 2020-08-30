package com.udyogg.services.product;

import com.udyogg.services.common.executer.TraceableScheduledExecuterService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        Schedulers.setFactory(
                new Schedulers.Factory() {
                    @Override
                    public ScheduledExecutorService decorateExecutorService(
                            String schedulerType, Supplier<? extends ScheduledExecutorService> actual) {
                        return new TraceableScheduledExecuterService(actual.get());
                    }
                });
        SpringApplication.run(ProductApplication.class, args);
    }
}
