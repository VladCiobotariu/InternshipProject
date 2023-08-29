package com.ozius.internship.project.security.user;

import com.ozius.internship.project.entity.UserAccount;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class ClientDetailsMapper {

    public UserDetails toUserDetails(UserAccount userAccount) {
        return User.withUsername(userAccount.getEmail())
                .password(userAccount.getPasswordHash())
                .roles("CLIENT") // this is how you add multiple roles --> roles("CLIENT", "ADMIN")
                .build();
    }
}
