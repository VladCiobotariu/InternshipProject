package com.ozius.internship.project.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }
}
