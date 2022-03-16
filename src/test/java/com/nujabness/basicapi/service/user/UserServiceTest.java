package com.nujabness.basicapi.service.user;

import com.nujabness.basicapi.bean.common.Gender;
import com.nujabness.basicapi.bean.user.UserDTO;
import com.nujabness.basicapi.data.dao.IUserRepository;
import com.nujabness.basicapi.data.entity.User;
import com.nujabness.basicapi.service.exception.BusinessException;
import com.nujabness.basicapi.service.user.impl.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired private UserService userService;

    @MockBean private IUserRepository userRepository;

    private static User user;

    @Before
    public void init() throws ParseException {
        user = new User();
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/1998");
        user.setId(1);
        user.setName("Mohammed");
        user.setPhoneNumber("0695844566");
        user.setCountry("France");
        user.setGender(Gender.MALE);
        user.setBirthDate(birthDate);
    }

    @Test
    public void createUser_True_UserValid() throws ParseException {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/1998");
        userDTO.setName("Mohammed");
        userDTO.setPhoneNumber("0695844566");
        userDTO.setCountry("France");
        user.setGender(Gender.MALE);
        userDTO.setBirthDate(birthDate);
        UserDTO res = userService.createUser(userDTO);

        assertThat(res)
                .returns(1,UserDTO::getId)
                .returns("Mohammed",UserDTO::getName)
                .returns("France",UserDTO::getCountry);
    }

    @Test
    public void isFrench_False_CountryNotFrance() throws ParseException {
        UserDTO userDTO = new UserDTO();
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/1998");
        userDTO.setName("Mohammed");
        userDTO.setPhoneNumber("0695844566");
        userDTO.setCountry("Spain");
        user.setGender(Gender.MALE);
        userDTO.setBirthDate(birthDate);
        assertThrows(BusinessException.class, () -> userService.createUser(userDTO));
    }

    @Test
    public void isAdult_False_AgeLessThan18() throws ParseException {
        UserDTO userDTO = new UserDTO();
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/2010");
        userDTO.setName("Mohammed");
        userDTO.setPhoneNumber("0695844566");
        userDTO.setCountry("France");
        user.setGender(Gender.MALE);
        userDTO.setBirthDate(birthDate);
        assertThrows(BusinessException.class, () -> userService.createUser(userDTO));
    }

}
