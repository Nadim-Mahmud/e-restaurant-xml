package net.therap.estaurant.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class Base {

    @PersistenceContext
    protected EntityManager entityManager;
}
