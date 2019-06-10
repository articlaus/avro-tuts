package com.wmt;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * Created by: Ganbat Bayar
 * On: 6/10/2019
 * Project: avrotest
 */

@Configuration
@EnableCassandraRepositories(basePackages = "com.wmt.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "avro";
    }

    @Override
    protected String getContactPoints() {
        return "localhost";
    }

    @Override
    protected int getPort() {
        return 9042;
    }
}
