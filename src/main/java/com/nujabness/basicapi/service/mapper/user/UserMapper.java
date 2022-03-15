package com.nujabness.basicapi.service.mapper.user;

import com.nujabness.basicapi.bean.common.Gender;
import com.nujabness.basicapi.bean.user.UserBean;
import com.nujabness.basicapi.data.entity.User;

public class UserMapper {

    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserBean userToUserBean(User user) {
        UserBean userBean = new UserBean();
        userBean.setName(user.getName());
        userBean.setBirthDate(user.getBirthDate());
        userBean.setCountry(user.getCountry());
        userBean.setPhoneNumber(user.getPhoneNumber());
        userBean.setGender(user.getGender().toString());
		return userBean;
    }

    public static User userBeanToUser(UserBean userBean) {
        User user = new User();
        user.setName(userBean.getName());
        user.setBirthDate(userBean.getBirthDate());
        user.setCountry(userBean.getCountry());
        user.setPhoneNumber(userBean.getPhoneNumber());
        user.setGender(Gender.valueOf(userBean.getGender()));
        return user;
    }

}
