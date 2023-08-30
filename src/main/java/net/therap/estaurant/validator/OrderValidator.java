package net.therap.estaurant.validator;

import net.therap.estaurant.entity.Order;
import net.therap.estaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/25/23
 */
@Component
public class OrderValidator implements Validator {

    @Autowired
    private OrderService orderService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Order.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Order order = (Order) target;

        if (Objects.isNull(order) || Objects.isNull(order.getRestaurantTable())) {
            errors.rejectValue("restaurantTable", "select.empty");
        }

        if (orderService.isTableBooked(order)) {
            errors.rejectValue("restaurantTable", "table.booked");
        }
    }
}
