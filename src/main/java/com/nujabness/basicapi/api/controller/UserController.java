package com.nujabness.basicapi.api.controller;

import com.nujabness.basicapi.bean.user.UserDTO;
import com.nujabness.basicapi.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
@Tag(name = "User")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user", responses = {
            @ApiResponse(description = "Get user success", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(description = "User not found",responseCode = "400",content = @Content)
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create user", responses = {
            @ApiResponse(description = "Create user success", responseCode = "201",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(description = "Validation Failed",responseCode = "400",content = @Content)
    })
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userRequest) {
        UserDTO userDto = userService.createUser(userRequest);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
}
