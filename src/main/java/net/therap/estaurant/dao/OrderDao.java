package net.therap.estaurant.dao;

import net.therap.estaurant.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class OrderDao extends AbstractDao<Order> {

    public boolean isTableInUse(int tableId) {
        return !entityManager.createNamedQuery("Order.findOrderByTable", Order.class)
                .setParameter("tableId", tableId)
                .getResultList()
                .isEmpty();
    }

    public boolean isTableBooked(Order order) {
        return !entityManager.createNamedQuery("Order.findOrderOnTable", Order.class)
                .setParameter("tableId", order.getRestaurantTable().getId())
                .setParameter("orderId", order.getId())
                .getResultList()
                .isEmpty();
    }

    public List<Order> findActiveOrderListByWaiterId(int waiterId) {
        return entityManager.createNamedQuery("Order.findActiveOnly", Order.class)
                .setParameter("waiterId", waiterId)
                .getResultList();
    }

    @Override
    public List<Order> findAll() {
        return entityManager.createNamedQuery("Order.findAll", Order.class).getResultList();
    }

    @Override
    public Order findById(int id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public Order saveOrUpdate(Order order) throws Exception {

        if (order.isNew()) {
            entityManager.persist(order);
        } else {
            order = entityManager.merge(order);
        }

        return order;
    }
}
