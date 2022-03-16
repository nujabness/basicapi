package com.nujabness.basicapi.bean.user;

import org.junit.Test;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;


public class UserBeanTest {

    @Test
    public void UserBeanTestPOJO() {
        final Class<?> classUnderTest = UserBean.class;
        assertPojoMethodsFor(classUnderTest).areWellImplemented();
    }
}
