package com.udyogg.services.database.repository;

import com.udyogg.services.database.entities.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CassandraRepository<Product> {

}
