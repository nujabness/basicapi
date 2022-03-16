package com.nujabness.basicapi.data.repository;

import com.nujabness.basicapi.bean.common.Gender;
import com.nujabness.basicapi.data.dao.IUserRepository;
import com.nujabness.basicapi.data.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;


@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired private IUserRepository userRepository;
    @Autowired private TestEntityManager entityManager;

    private static User user;

    @Before
    public void init() throws ParseException {
        user = new User();
        Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("05/11/1998");
        user.setName("Mohammed");
        user.setPhoneNumber("0695844566");
        user.setCountry("France");
        user.setGender(Gender.MALE);
        user.setBirthDate(birthDate);
    }

    @Test
    public void findById_true_found() {
        entityManager.persist(user);
        User userLocal = userRepository.findById(3).get();
        assertThat(userLocal).isEqualTo(user);
    }

    @Test
    public void saveUser_False_EmptyUserName() {
        user.setName("");
        assertThrows(ConstraintViolationException.class, () -> entityManager.persistAndFlush(user));
    }

    @Test
    public void saveUser_False_EmptyCountry() {
        user.setCountry("");
        assertThrows(ConstraintViolationException.class, () -> entityManager.persistAndFlush(user));
    }

    @Test
    public void saveUser_False_EmptyPhoneNumber() {
        user.setPhoneNumber("");
        assertThrows(ConstraintViolationException.class, () -> entityManager.persistAndFlush(user));
    }

    @Test
    public void saveUser_False_NullBirthdate() {
        user.setBirthDate(null);
        assertThrows(ConstraintViolationException.class, () -> entityManager.persistAndFlush(user));
    }

    @Test
    public void saveUser_False_NullGender() {
        user.setBirthDate(null);
        assertThrows(ConstraintViolationException.class, () -> entityManager.persistAndFlush(user));
    }
}

