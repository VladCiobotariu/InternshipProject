package com.ozius.internship.project.security.user;

import com.ozius.internship.project.entity.UserAccount;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

    public UserDetails toUserDetails(UserAccount userAccount) {
        return User.withUsername(userAccount.getEmail())
                .password(userAccount.getPasswordHash())
                .roles("client")
                .build();
    }
}
