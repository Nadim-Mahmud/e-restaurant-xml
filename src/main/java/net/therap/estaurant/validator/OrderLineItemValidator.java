package net.therap.estaurant.validator;

import net.therap.estaurant.entity.OrderLineItem;
import org.springframework.validation.Errors;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/23/23
 */
public class OrderLineItemValidator {

    public static void validate(List<OrderLineItem> orderLineItemList, OrderLineItem orderLineItem, Errors errors) {
        if (orderLineItemList.contains(orderLineItem)) {
            errors.rejectValue("item", "select.duplicate");
        }
    }
}
