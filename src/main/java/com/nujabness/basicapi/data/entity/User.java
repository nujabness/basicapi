package com.nujabness.basicapi.data.entity;

import com.nujabness.basicapi.bean.common.Gender;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Entity
public class User {

    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "Name must not be null or empty")
    private String name;

    @NotNull(message = "BirthDate must not be null")
    private Date birthDate;

    @NotBlank(message = "Country must not be null or empty")
    private String country;

    @NotBlank(message = "PhoneNumber must not be null")
    @Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$",
             message = "PhoneNumber is not Valid")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
