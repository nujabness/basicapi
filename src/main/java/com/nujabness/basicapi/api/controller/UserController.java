package com.nujabness.basicapi.api.controller;


import com.nujabness.basicapi.service.exception.BusinessException;
import com.nujabness.basicapi.bean.user.UserBean;
import com.nujabness.basicapi.service.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        try {
            UserBean userBean = userService.getUserById(id);
            return ResponseEntity.ok(userBean);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserBean userRequest) {
        try {
            Integer id = userService.createUser(userRequest);
            return ResponseEntity.created(new URI( String.format("/users/%d", id))).build();
        } catch (BusinessException|URISyntaxException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
