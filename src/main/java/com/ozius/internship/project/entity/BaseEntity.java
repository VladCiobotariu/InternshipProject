package com.ozius.internship.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public class BaseEntity {

    public static final String ID = "id";

    @Id
    @GeneratedValue
    @Column(name = ID)
    private long id;

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getId() == 0) return false;
        if(o == null) return false;
        if(!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        if(that.getId() == 0) return false;
        if (!getClass().equals(that.getClass())) return false;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
