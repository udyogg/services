package com.udyogg.services.database.entities;

import java.util.UUID;
import lombok.Data;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Data
@Table("products")
public class Product {

  @Column(value = "id")
  private UUID id;
  @PrimaryKeyColumn(name = "product_category", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private String productCategory;
  @PrimaryKeyColumn(name = "product_name", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
  private String productName;
  @Column(value = "product_description")
  private String proudctDescription;
  @Column(value = "price")
  private double price;
}
