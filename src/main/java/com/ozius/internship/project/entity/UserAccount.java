package com.ozius.internship.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = UserAccount.TABLE_NAME)
public class UserAccount extends BaseEntity{

    public static final String TABLE_NAME = "USER_ACCOUNT";

    interface Columns{
        String FIRST_NAME = "FIRST_NAME";
        String LAST_NAME = "LAST_NAME";
        String EMAIL = "EMAIL";
        String PASSWORD_HASH = "PASSWORD_HASH";
        String IMAGE_NAME = "IMAGE_NAME";
        String TELEPHONE = "TELEPHONE";
    }

    @Column(name = Columns.FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = Columns.LAST_NAME, nullable = false)
    private String lastName;

    @Column(name = Columns.EMAIL, nullable = false)
    private String email;

    @Column(name = Columns.PASSWORD_HASH, nullable = false)
    private String passwordHash;

    @Column(name = Columns.IMAGE_NAME, nullable = false)
    private String imageName;

    @Column(name = Columns.TELEPHONE, length = 10, nullable = false)
    private String telephone;

    protected UserAccount() {
    }

    public UserAccount(String firstName, String lastName, String email, String passwordHash, String imageName, String telephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.imageName = imageName;
        this.telephone = telephone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getImageName() {
        return imageName;
    }

    public String getTelephone() {
        return telephone;
    }

    //TODO Given that we will most probably use PUT for updates, a single update method is preferred.
    // it can either can all fields as parameter or a single value object. This is to be decided later which approach we'll use.
    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    void setImageName(String imageName) {
        this.imageName = imageName;
    }

    void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
