package com.nujabness.basicapi.service.mapper.user;

import com.nujabness.basicapi.bean.user.UserDTO;
import com.nujabness.basicapi.data.entity.User;

public class UserMapper {

    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setCountry(user.getCountry());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setGender(user.getGender());
		return userDTO;
    }

    public static User userDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setBirthDate(userDTO.getBirthDate());
        user.setCountry(userDTO.getCountry());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setGender(userDTO.getGender());
        return user;
    }

}
