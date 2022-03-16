package com.nujabness.basicapi.bean.user;

import org.junit.Test;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;


public class UserDTOTest {

    @Test
    public void UserDTOMethods_True_MethodsAreWellImplemented() {
        final Class<?> classUnderTest = UserDTO.class;
        assertPojoMethodsFor(classUnderTest).areWellImplemented();
    }
}
