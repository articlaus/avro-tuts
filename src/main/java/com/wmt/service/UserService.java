package com.wmt.service;

import com.google.gson.Gson;
import com.wmt.entity.Person;
import com.wmt.model.PersonModel;
import com.wmt.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Ganbat Bayar
 * On: 6/7/2019
 * Project: avrotest
 */
@Service
@Log4j2
public class UserService {

    @Autowired
    KafkaService kafkaService;
    @Autowired
    PersonRepository personRepository;

    public ResponseEntity getSerialized(PersonModel model) {
        try {
            kafkaService.produceRaw("model", new Gson().toJson(model));
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity getDeserialized() {
        try {
            List<Person> persons = new ArrayList<>();
            persons = personRepository.findAll();
            return ResponseEntity.ok(persons);
        } catch (Exception ex) {
            log.error(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
