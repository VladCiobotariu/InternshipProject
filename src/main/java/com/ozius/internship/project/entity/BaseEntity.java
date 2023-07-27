package com.ozius.internship.project.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;

        if (id == 0 || that.id == 0) {
            return false;
        }

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
