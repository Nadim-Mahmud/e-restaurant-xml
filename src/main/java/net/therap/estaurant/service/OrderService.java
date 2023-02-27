package net.therap.estaurant.service;

import net.therap.estaurant.dao.OrderDao;
import net.therap.estaurant.entity.Order;
import net.therap.estaurant.entity.OrderLineItem;
import net.therap.estaurant.entity.OrderStatus;
import net.therap.estaurant.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderLineItemService orderLineItemService;

    public List<Order> findAll() {
        return orderDao.findAll();
    }

    public List<Order> findActiveOrderListByWaiterId(int waiterId) {
        return orderDao.findActiveOrderListByWaiterId(waiterId);
    }

    public Order findById(int id) {
        return Optional.ofNullable(orderDao.findById(id)).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void cancel(int id) throws Exception {
        Order order = orderDao.findById(id);

        order.setStatus(OrderStatus.CANCELED);
        orderDao.saveOrUpdate(order);

        for (OrderLineItem orderLineItem : order.getOrderLineItemList()) {
            orderLineItemService.cancel(orderLineItem.getId());
        }
    }

    @Transactional
    public Order saveOrUpdate(Order order) throws Exception {
        return orderDao.saveOrUpdate(order);
    }

    @Transactional
    public Order saveAsServed(Order order) throws Exception {
        order.setStatus(OrderStatus.SERVED);

        for (OrderLineItem orderLineItem : order.getOrderLineItemList()) {
            orderLineItem.setOrderStatus(OrderStatus.SERVED);
        }

        return orderDao.saveOrUpdate(order);
    }

    public boolean isTableInUse(int tableId) {
        return orderDao.isTableInUse(tableId);
    }

    public boolean isTableBooked(Order order) {
        return orderDao.isTableBooked(order);
    }

    public List<Order> getOrderListWithStatus(int waiterId) {
        List<Order> orderList = orderDao.findActiveOrderListByWaiterId(waiterId);

        for (int i = 0; i < orderList.size(); i++) {

            int prepared = 0;
            int preparing = 0;
            int estTime = 0;

            for (int j = 0; j < orderList.get(i).getOrderLineItemList().size(); j++) {

                if (orderList.get(i).getOrderLineItemList().get(j).getOrderStatus().equals(OrderStatus.PREPARED)) {
                    prepared++;
                }

                if (orderList.get(i).getOrderLineItemList().get(j).getOrderStatus().equals(OrderStatus.PREPARING)) {
                    preparing++;
                }

                if (orderList.get(i).getOrderLineItemList().get(j).getOrderStatus().equals(OrderStatus.PREPARING)) {
                    Date acceptedAt = orderList.get(i).getOrderLineItemList().get(j).getAcceptedAt();
                    Long elapsed = new Date().getTime() - acceptedAt.getTime();
                    elapsed = elapsed / 60000;
                    int time = (int) Math.max(0, orderList.get(i).getOrderLineItemList().get(j).getEstCookingTime() - elapsed);
                    estTime = Math.max(estTime, time);
                }

            }

            orderList.get(i).setEstTime(estTime);

            if (prepared == orderList.get(i).getOrderLineItemList().size()) {
                orderList.get(i).setStatus(OrderStatus.PREPARED);
                orderList.get(i).setEstTime(0);
            } else if (preparing > 0) {
                orderList.get(i).setStatus(OrderStatus.PREPARING);
            } else {
                orderList.get(i).setStatus(OrderStatus.ORDERED);
            }
        }

        return orderList;
    }
}
