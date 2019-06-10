package com.wmt.repository;

import com.wmt.entity.Person;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: Ganbat Bayar
 * On: 6/10/2019
 * Project: avrotest
 */
@Repository
public interface PersonRepository extends CassandraRepository<Person, String> {
}
