package com.ozius.internship.project.infra;

import com.ozius.internship.project.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class JpaHelper {

    private final EntityManagerFactory emf;

    public JpaHelper(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public <T> T doTransaction(JpaCallback<T> callback) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            T result = callback.execute(em);
            em.getTransaction().commit();
            return result;
        }
    }

    public void doTransaction(JpaCallbackVoid callback) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            callback.execute(em);
            em.getTransaction().commit();
        }
    }

    public <T> T doManaged(JpaCallback<T> callback) {
        try (EntityManager em = emf.createEntityManager()) {
            return callback.execute(em);
        }
    }

    public void doManaged(JpaCallbackVoid callback) {
        try (EntityManager em = emf.createEntityManager()) {
            callback.execute(em);
        }
    }
}
