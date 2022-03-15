package com.nujabness.basicapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nujabness.basicapi.bean.user.UserBean;
import com.nujabness.basicapi.service.exception.BusinessException;
import com.nujabness.basicapi.service.user.impl.UserService;
import org.hamcrest.Matchers;
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
    public void getUserById() throws Exception {
        Integer id = 1;
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/1998");
        UserBean userBean = new UserBean();
        userBean.setName("Momo");
        userBean.setPhoneNumber("0655887744");
        userBean.setCountry("France");
        userBean.setGender("MALE");
        userBean.setBirthDate(birthDate);

        when(userService.getUserById(id)).thenReturn(userBean);

        mvc.perform(get(APP + id)
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(userBean.getName()))
            .andExpect(jsonPath("country").value(userBean.getCountry()))
            .andExpect(jsonPath("phoneNumber").value(userBean.getPhoneNumber()));
    }

    @Test
    public void getUserById_NotFound() throws Exception {
        Integer id = 1;
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/1998");
        UserBean userBean = new UserBean();
        userBean.setName("Momo");
        userBean.setPhoneNumber("0655887744");
        userBean.setCountry("France");
        userBean.setGender("MALE");
        userBean.setBirthDate(birthDate);

        when(userService.getUserById(id)).thenThrow(new BusinessException("Record not Found"));

        MvcResult result = mvc.perform(get(APP + id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Record not Found", result.getResponse().getContentAsString());
    }

    @Test
    public void createUser() throws Exception {
        Integer id = 1;
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/1998");
        UserBean userBean = new UserBean();
        userBean.setName("Momo");
        userBean.setPhoneNumber("0655887744");
        userBean.setCountry("France");
        userBean.setGender("MALE");
        userBean.setBirthDate(birthDate);

        when(userService.createUser(any(UserBean.class))).thenReturn(id);

        mvc.perform(post(APP)
                .content(objectMapper.writeValueAsString(userBean))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/users/1"));
    }

    @Test
    public void createUser_InvalidUser() throws Exception {
        Integer id = 1;
        UserBean userBean = new UserBean();
        userBean.setName(null);
        userBean.setPhoneNumber(null);
        userBean.setCountry(null);
        userBean.setGender(null);
        userBean.setBirthDate(null);

        when(userService.createUser(any(UserBean.class))).thenReturn(id);

        mvc.perform(post(APP)
                .content(objectMapper.writeValueAsString(userBean))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("details").value(Matchers.containsInAnyOrder(
                "BirthDate must not be null",
                "Country must not be null or empty",
                "Gender must not be null",
                "PhoneNumber must not be null",
                "Name must not be null or empty")));
    }
}
