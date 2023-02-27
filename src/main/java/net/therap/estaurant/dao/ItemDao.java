package net.therap.estaurant.dao;

import net.therap.estaurant.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class ItemDao extends AbstractDao<Item> {


    public List<Item> findAvailable() {
        return entityManager.createNamedQuery("Item.findAvailable", Item.class).getResultList();
    }

    public boolean isExistingItem(Item item) {
        return !entityManager.createNamedQuery("Item.findItemByName", Item.class)
                .setParameter("id", item.getId())
                .setParameter("name", item.getName())
                .getResultList()
                .isEmpty();
    }

    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(Item.class, id));
    }

    @Override
    public List<Item> findAll() {
        return entityManager.createNamedQuery("Item.findAll", Item.class).getResultList();
    }

    @Override
    public Item findById(int id) {
        return entityManager.find(Item.class, id);
    }

    @Override
    public Item saveOrUpdate(Item item) throws Exception {

        if (item.isNew()) {
            entityManager.persist(item);
        } else {
            item = entityManager.merge(item);
        }

        return item;
    }
}
