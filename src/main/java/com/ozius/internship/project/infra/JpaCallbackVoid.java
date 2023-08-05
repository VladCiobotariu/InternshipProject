package com.ozius.internship.project.infra;

import jakarta.persistence.EntityManager;

public interface JpaCallbackVoid {
    void execute(EntityManager em);
}
