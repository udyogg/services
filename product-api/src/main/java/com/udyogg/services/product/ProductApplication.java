package com.udyogg.services.product;

import com.udyogg.services.common.executer.TraceableScheduledExecuterService;
import com.udyogg.services.database.config.CassandraConfig;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

@SpringBootApplication
@ComponentScan(basePackages = "com.udyogg.services.database")
public class ProductApplication {

  @Autowired
  CassandraConfig cassandraConfig;

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

  @PostConstruct
  public void get() {
    System.out.println(cassandraConfig.getPort());
  }
}
