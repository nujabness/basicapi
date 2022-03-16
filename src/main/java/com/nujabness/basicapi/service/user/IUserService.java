package com.nujabness.basicapi.service.user;

import com.nujabness.basicapi.bean.user.UserDTO;

public interface IUserService {

    UserDTO getUserById(Integer id);

    UserDTO createUser(UserDTO id);

}
