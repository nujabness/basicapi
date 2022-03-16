package com.nujabness.basicapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nujabness.basicapi.bean.common.Gender;
import com.nujabness.basicapi.bean.user.UserDTO;
import com.nujabness.basicapi.service.exception.BusinessException;
import com.nujabness.basicapi.service.user.impl.UserService;
import org.hamcrest.Matchers;
import org.hibernate.type.LocalDateType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private final static String APP = "/users/";
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getUserById_True_Found() throws Exception {
        Integer id = 1;
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/1998");
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Momo");
        userDTO.setPhoneNumber("0655887744");
        userDTO.setCountry("France");
        userDTO.setGender(Gender.MALE);
        userDTO.setBirthDate(birthDate);

        when(userService.getUserById(id)).thenReturn(userDTO);

        mvc.perform(get(APP + id)
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(userDTO.getName()))
            .andExpect(jsonPath("country").value(userDTO.getCountry()))
            .andExpect(jsonPath("phoneNumber").value(userDTO.getPhoneNumber()));
    }

    @Test
    public void getUserById_False_NotFound() throws Exception {
        Integer id = 1;
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/1998");
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Momo");
        userDTO.setPhoneNumber("0655887744");
        userDTO.setCountry("France");
        userDTO.setGender(Gender.MALE);
        userDTO.setBirthDate(birthDate);

        when(userService.getUserById(id)).thenThrow(new BusinessException("Record not Found"));

        mvc.perform(get(APP + id)
                    .contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("details").value(Matchers.containsInAnyOrder("Record not Found")));
    }

    @Test
    public void createUser_True_ValidUser() throws Exception {
        Integer id = 1;
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/1998");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setName("Momo");
        userDTO.setPhoneNumber("0655887744");
        userDTO.setCountry("France");
        userDTO.setGender(Gender.MALE);
        userDTO.setBirthDate(birthDate);

        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        mvc.perform(post(APP)
                .content(objectMapper.writeValueAsString(userDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").value("1"))
            .andExpect(jsonPath("name").value("Momo"))
            .andExpect(jsonPath("phoneNumber").value("0655887744"));
    }

    @Test
    public void createUser_False_InvalidUser() throws Exception {
        Integer id = 1;
        UserDTO userDTO = new UserDTO();
        userDTO.setName(null);
        userDTO.setPhoneNumber(null);
        userDTO.setCountry(null);
        userDTO.setGender(null);
        userDTO.setBirthDate(null);

        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        mvc.perform(post(APP)
                .content(objectMapper.writeValueAsString(userDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("details").value(Matchers.containsInAnyOrder(
                    "PhoneNumber must not be null",
                    "Name must not be null or empty",
                    "BirthDate must not be null",
                    "Country must not be null or empty")));
    }
}
