package net.therap.estaurant.dao;

import net.therap.estaurant.entity.Item;
import net.therap.estaurant.entity.OrderLineItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class OrderLineItemDao extends AbstractDao<OrderLineItem> {

    public List<OrderLineItem> findActiveOrderByOrderId(int orderId) {
        return entityManager.createNamedQuery("OrderLineItem.findActiveOrderByOrderId", OrderLineItem.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<OrderLineItem> findNotServedOrderLineItemsByItemId(int itemId) {
        return entityManager.createNamedQuery("OrderLineItem.findNotServedByItemId", OrderLineItem.class)
                .setParameter("itemId", itemId)
                .getResultList();
    }

    public List<OrderLineItem> findOrderedItemsByChef(List<Item> itemList) {
        return entityManager.createNamedQuery("OrderLineItem.findOrdersOnProcess", OrderLineItem.class)
                .setParameter("itemList", itemList)
                .getResultList();
    }

    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(OrderLineItem.class, id));
    }

    @Override
    public List<OrderLineItem> findAll() {
        return entityManager.createNamedQuery("OrderLineItem.findAll", OrderLineItem.class).getResultList();
    }

    @Override
    public OrderLineItem findById(int id) {
        return entityManager.find(OrderLineItem.class, id);
    }

    @Override
    public OrderLineItem saveOrUpdate(OrderLineItem orderLineItem) throws Exception {

        if (orderLineItem.isNew()) {
            entityManager.persist(orderLineItem);
        } else {
            orderLineItem = entityManager.merge(orderLineItem);
        }

        return orderLineItem;
    }
}
