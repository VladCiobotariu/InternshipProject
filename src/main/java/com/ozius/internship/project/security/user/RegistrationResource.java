package com.ozius.internship.project.security.user;

import com.ozius.internship.project.entity.UserAccount;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.repository.BuyerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationResource {

    private final BuyerRepository buyerRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationResource(BuyerRepository buyerRepository, PasswordEncoder passwordEncoder) {
        this.buyerRepository = buyerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register-client")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@RequestBody UserAccount userAccount){
        UserAccount user = UserAccount.builder()
                .email(userAccount.getEmail())
                .firstName(userAccount.getFirstName())
                .lastName(userAccount.getLastName())
                .imageName(userAccount.getImageName())
                .telephone(userAccount.getTelephone())
                .passwordHash(passwordEncoder.encode(userAccount.getPasswordHash()))
                .build();
        Buyer buyer = new Buyer(user);
        buyerRepository.save(buyer);
    }
}
