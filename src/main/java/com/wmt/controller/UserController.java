package com.wmt.controller;

import com.wmt.model.PersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wmt.service.*;

/**
 * Created by: Ganbat Bayar
 * On: 6/7/2019
 * Project: avrotest
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/create")
    public ResponseEntity insertUser(@RequestBody PersonModel model) {
        return userService.getSerialized(model);
    }

    @RequestMapping("/get")
    public ResponseEntity getPerson() {
        return userService.getDeserialized();
    }
}
