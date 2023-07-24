package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<UserAccount, Long> {
}
