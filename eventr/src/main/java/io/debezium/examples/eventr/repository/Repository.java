package io.debezium.examples.eventr.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Repository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    protected Repository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T getById(int id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> getAll() {
        return entityManager.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
    }

    public T save(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(int id) {
        final T entity = getById(id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
