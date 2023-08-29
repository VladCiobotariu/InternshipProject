package com.ozius.internship.project.security.user;

import com.ozius.internship.project.entity.UserAccount;
import com.ozius.internship.project.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;
    private final UserDetailsMapper userDetailsMapper;

    public DatabaseUserDetailsService(UserAccountRepository userAccountRepository, UserDetailsMapper userDetailsMapper) {
        this.userAccountRepository = userAccountRepository;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByEmail(email);
        return userDetailsMapper.toUserDetails(userAccount);
    }
}
