package net.therap.estaurant.propertyEditor;

import net.therap.estaurant.entity.RestaurantTable;
import net.therap.estaurant.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

@Component
public class RestaurantTableEditor extends PropertyEditorSupport {

    @Autowired
    private RestaurantTableService restaurantTableService;

    @Override
    public String getAsText() {
        RestaurantTable restaurantTable = (RestaurantTable) getValue();

        return restaurantTable == null ? "" : restaurantTable.getName();
    }

    @Override
    public void setAsText(String restaurantTableId) {
        if (Objects.isNull(restaurantTableId) || restaurantTableId.isEmpty()) {
            setValue(null);

            return;
        }

        setValue(restaurantTableService.findById(Integer.parseInt(restaurantTableId)));
    }
}
