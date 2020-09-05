package com.udyogg.services.database.config;

import java.util.List;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
@EnableCassandraRepositories(
    basePackages = "com.udyogg.services.database.repository")
public class CassandraConfig {


//  private List<String> contactPoints;

  private int port;
}
