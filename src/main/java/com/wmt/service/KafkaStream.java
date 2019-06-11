package com.wmt.service;

import com.google.gson.Gson;
import com.wmt.entity.Person;
import com.wmt.model.PersonModel;
import com.wmt.repository.PersonRepository;
import com.wmt.util.KafkaConstants;
import lombok.extern.log4j.Log4j2;
import org.apache.avro.Schema;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Created by: Ganbat Bayar
 * On: 6/10/2019
 * Project: avrotest
 */
@Component
@Log4j2
public class KafkaStream {
    @Autowired
    PersonRepository repository;

    @PostConstruct
    private void init() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "avro-converter-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> rawStream = builder.stream("rawtopic");
        KStream<String, byte[]> serStream = rawStream.flatMap((k, v) -> {
            return Collections.singletonList(new KeyValue<String, byte[]>(k, convert(v)));
        });

        serStream.to("serde", Produced.with(Serdes.String(), Serdes.ByteArray()));
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

    private byte[] convert(String v) {
        try {
            PersonModel model = new Gson().fromJson(v, PersonModel.class);
            Optional<Person> person = repository.findById(model.getName());
            if (!person.isPresent()) {
                log.info("inserting to cassandra - " + model.toString());
                repository.insert(new Person(model.getName(), model.getFavorite_number(), model.getFavorite_color()));
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Schema sc = ReflectData.get().getSchema(PersonModel.class);
            DatumWriter<PersonModel> userWriter = new ReflectDatumWriter<>(PersonModel.class);
            Encoder encoder = EncoderFactory.get().binaryEncoder(baos, null);
            userWriter.write(model, encoder);
            encoder.flush();
            baos.close();

            return baos.toByteArray();
        } catch (Exception ex) {
            log.error(ex);
            return null;
        }

    }
}
