package com.nujabness.basicapi.service.user.impl;

import com.nujabness.basicapi.service.exception.BusinessException;
import com.nujabness.basicapi.bean.user.UserDTO;
import com.nujabness.basicapi.data.dao.IUserRepository;
import com.nujabness.basicapi.data.entity.User;
import com.nujabness.basicapi.service.mapper.user.UserMapper;
import com.nujabness.basicapi.service.user.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Optional;


@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new BusinessException("Record not found");
        }
        return UserMapper.userToUserDTO(user.get());
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
         applyRules(userDTO);
         return UserMapper.userToUserDTO(userRepository.save(UserMapper.userDTOToUser(userDTO)));
    }

    private void applyRules(UserDTO userDTO){
        LocalDate birthDate = LocalDate.from(userDTO.getBirthDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        int age = Period.between(birthDate, LocalDate.now()).getYears();

        if(age < 18 || !"FRANCE".equalsIgnoreCase(userDTO.getCountry()))
            throw new BusinessException("Only french adults can create account");
    }
}
