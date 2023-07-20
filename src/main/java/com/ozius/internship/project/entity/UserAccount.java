package com.ozius.internship.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = UserAccount.TABLE_NAME)
public class UserAccount {

    public static final String TABLE_NAME = "USER_ACCOUNT";

    interface Columns{
        String EMAIL = "EMAIL";
        String PASSWORD_HASH = "PASSWORD_HASH";
        String IMAGE_NAME = "IMAGE_NAME";
        String TELEPHONE = "TELEPHONE";
    }
}
