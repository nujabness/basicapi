package com.nujabness.basicapi.service.user;

import com.nujabness.basicapi.bean.user.UserBean;

public interface IUserService {

    UserBean getUserById(Integer id);

    Integer createUser(UserBean id);

}
