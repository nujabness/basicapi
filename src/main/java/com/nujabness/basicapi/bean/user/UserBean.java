package com.nujabness.basicapi.bean.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserBean {

    @NotBlank(message = "Name must not be null or empty")
    private String name;

    @JsonFormat(pattern = "dd/mm/yyyy")
    @NotNull(message = "BirthDate must not be null")
    private Date birthDate;

    @NotBlank(message = "Country must not be null or empty")
    private String country;

    @NotBlank(message = "PhoneNumber must not be null")
    @Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$",
            message = "PhoneNumber is not Valid")
    private String phoneNumber;

    @NotBlank(message = "Gender must not be null")
    @Pattern(regexp = "MALE|FEMALE",
            message = "Gender is not Valid")
    private String gender;
}
