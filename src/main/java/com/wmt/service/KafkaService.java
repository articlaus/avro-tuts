package com.wmt.service;

import com.wmt.model.PersonModel;
import com.wmt.repository.PersonRepository;
import com.wmt.util.KafkaConstants;
import lombok.extern.log4j.Log4j2;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * Created by: Ganbat Bayar
 * On: 6/7/2019
 * Project: avrotest
 */
@Component
@Log4j2
public class KafkaService {

    KafkaTemplate<String, String> rawProd;
    @Autowired
    PersonRepository repository;

    @PostConstruct
    private void init() {
        Map<String, Object> rprop = new HashMap<>();
        rprop.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaConstants.CLIENT_ID);
        rprop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
        rprop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        rprop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        rawProd = new KafkaTemplate<String, String>(new DefaultKafkaProducerFactory<>(rprop));
        rawProd.setDefaultTopic("rawtopic");
    }

    public void produceRaw(String key, String val) {
        try {
            rawProd.sendDefault(key, val);
        } catch (Exception ex) {
            log.error(ex);
        } finally {
            rawProd.flush();
        }
    }


    @KafkaListener(topics = KafkaConstants.TOPIC_NAME)
    private void consume(@Payload byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ReflectDatumReader<PersonModel> reader = new ReflectDatumReader(PersonModel.class);
                PersonModel model = reader.read(null, DecoderFactory.get().binaryDecoder(bais, null));
        } catch (Exception ex) {
            log.error(ex);
        }
    }
}
