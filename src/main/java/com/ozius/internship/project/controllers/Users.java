package com.ozius.internship.project.controllers;

import com.ozius.internship.project.entity.UserAccount;
import com.ozius.internship.project.repository.UserAccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Users {

    private final UserAccountRepository userAccountRepository;

    public Users(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @GetMapping("/users/{email}")
//    @PreAuthorize("hasRole('CLIENT') and #email == authentication.name")
    public ResponseEntity<Object> retrieveUserByEmail(@PathVariable String email){

        UserAccount user = userAccountRepository.findByEmail(email);
        if(user!=null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
