package net.therap.estaurant.service;

import net.therap.estaurant.dao.OrderLineItemDao;
import net.therap.estaurant.entity.OrderLineItem;
import net.therap.estaurant.entity.OrderStatus;
import net.therap.estaurant.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author nadimmahmud
 * @since 1/24/23
 */
@Service
public class OrderLineItemService {

    @Autowired
    private OrderLineItemDao orderLineDao;

    @Autowired
    private UserService userService;

    public boolean isOrderOnProcess(int orderId) {
        return orderLineDao.findActiveOrderByOrderId(orderId).size() > 0;
    }

    public boolean isItemInUse(int itemId) {
        return orderLineDao.findNotServedOrderLineItemsByItemId(itemId).size() > 0;
    }

    public List<OrderLineItem> getOrderedNotificationsByUserId(int chefId) {
        return orderLineDao.findOrderedItemsByChef(userService.findById(chefId).getItemList());
    }

    public List<OrderLineItem> findAll() {
        return orderLineDao.findAll();
    }

    public OrderLineItem findById(int id) {
        return Optional.ofNullable(orderLineDao.findById(id)).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void delete(int id) throws Exception {
        orderLineDao.delete(id);
    }

    @Transactional
    public OrderLineItem saveOrUpdate(OrderLineItem orderLineitem) throws Exception {
        return orderLineDao.saveOrUpdate(orderLineitem);
    }

    @Transactional
    public OrderLineItem cancel(int orderLineItemId) throws Exception {
        OrderLineItem orderLineItem = orderLineDao.findById(orderLineItemId);
        orderLineItem.setOrderStatus(OrderStatus.CANCELED);

        return orderLineDao.saveOrUpdate(orderLineItem);
    }
}
