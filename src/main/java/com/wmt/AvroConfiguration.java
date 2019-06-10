package com.wmt;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by: Ganbat Bayar
 * On: 6/10/2019
 * Project: avrotest
 */
@Configuration
@Import({CassandraConfig.class, KafkaConfig.class})
public class AvroConfiguration {
}
