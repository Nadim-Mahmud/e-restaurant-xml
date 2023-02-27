package net.therap.estaurant.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public abstract class AbstractDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    public abstract List<T> findAll();

    public abstract T findById(int id);

    public abstract T saveOrUpdate(T entry) throws Exception;
}
