package com.ozius.internship.project.security.user;

import com.ozius.internship.project.security.password.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserAccountDto {
    private String email;
    @ValidPassword
    @NotBlank
    @NotNull
    private String password;
    private String firstName;
    private String lastName;
    private String telephone;
    private String image;
}
