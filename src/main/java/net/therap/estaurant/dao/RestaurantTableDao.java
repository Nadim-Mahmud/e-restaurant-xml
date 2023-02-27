package net.therap.estaurant.dao;

import net.therap.estaurant.entity.RestaurantTable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class RestaurantTableDao extends AbstractDao<RestaurantTable> {

    public boolean isDuplicateTable(RestaurantTable restaurantTable) {
        return !entityManager.createNamedQuery("RestaurantTable.isDuplicateTable", RestaurantTable.class)
                .setParameter("name", restaurantTable.getName())
                .setParameter("id", restaurantTable.getId())
                .getResultList()
                .isEmpty();
    }

    public List<RestaurantTable> findByWaiterId(int waiterId) {
        return entityManager.createNamedQuery("RestaurantTable.findByWaiterId", RestaurantTable.class)
                .setParameter("waiterId", waiterId)
                .getResultList();
    }

    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(RestaurantTable.class, id));
    }

    @Override
    public List<RestaurantTable> findAll() {
        return entityManager.createNamedQuery("RestaurantTable.findAll", RestaurantTable.class).getResultList();
    }

    @Override
    public RestaurantTable findById(int id) {
        return entityManager.find(RestaurantTable.class, id);
    }

    @Override
    public RestaurantTable saveOrUpdate(RestaurantTable restaurantTable) throws Exception {

        if (restaurantTable.isNew()) {
            entityManager.persist(restaurantTable);
        } else {
            restaurantTable = entityManager.merge(restaurantTable);
        }

        return restaurantTable;
    }
}
