package com.ozius.internship.project.infra;

import jakarta.persistence.EntityManager;

public interface JpaCallback<T> {

    T execute(EntityManager em);
}
