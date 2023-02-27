package net.therap.estaurant.validator;

import net.therap.estaurant.entity.RestaurantTable;
import net.therap.estaurant.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author nadimmahmud
 * @since 1/23/23
 */
@Component
public class RestaurantTableValidator implements Validator {

    @Autowired
    private RestaurantTableService restaurantTableService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RestaurantTable.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (restaurantTableService.isDuplicateTable((RestaurantTable) target)) {
            errors.rejectValue("name", "input.tableName.duplicate");
        }
    }
}
