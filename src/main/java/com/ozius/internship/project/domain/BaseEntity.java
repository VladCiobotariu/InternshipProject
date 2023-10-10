package com.ozius.internship.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity {

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
        if (this == o) return true; //same instance
        if (this.getId() == 0) return false;   // not a persisted entity
        if(o == null) return false; //that is null
        if(!(o instanceof BaseEntity that)) return false; //not an entity
        if(that.getId() == 0) return false;  // not a persisted entity
        /*
         * Lazy relationships are loaded as hibernate proxy. The Hibernate proxy is an extension of the entity class.
         * Example a seller entity will be either loaded with class Seller when EAGER or class Seller$HibernateProxy$NJOOXJ3J when LAZY, where  Seller$HibernateProxy$NJOOXJ3J extends Seller.
         * Now, any instance of proxy class is assignable to entity class reference but not vice versa, i.e. Seller$HibernateProxy$NJOOXJ3J is assignable to Seller
         * Therefore we have to check if at least one class is assignable to another.
         * We'll have the following possible combination:  (entity, entity), (proxy, entity), (entity, proxy), (proxy, proxy). All of them return true when proxy is a extension of entity.
         */
        if (!(that.getClass().isAssignableFrom(getClass()) || getClass().isAssignableFrom(that.getClass()))) return false;
        return getId() == that.getId(); //we consider two entities of same class equal if they share the same id.
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
