package net.therap.estaurant.dao;

import net.therap.estaurant.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class UserDao extends Base {

    public User findByEmail(String email) {

        try {
            return entityManager.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean isDuplicateEmail(User user) {
        return !entityManager.createNamedQuery("User.isDuplicateEmail", User.class)
                .setParameter("email", user.getEmail())
                .setParameter("id", user.getId())
                .getResultList()
                .isEmpty();
    }

    public List<User> findChef() {
        return entityManager.createNamedQuery("User.findChef", User.class).getResultList();
    }

    public List<User> findWaiter() {
        return entityManager.createNamedQuery("User.findWaiter", User.class).getResultList();
    }

    public List<User> findAll() {
        return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    public User saveOrUpdate(User user) throws Exception {

        if (user.isNew()) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }

        return user;
    }
}
