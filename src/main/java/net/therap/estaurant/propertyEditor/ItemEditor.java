package net.therap.estaurant.propertyEditor;

import net.therap.estaurant.entity.Item;
import net.therap.estaurant.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/23/23
 */
@Component
public class ItemEditor extends PropertyEditorSupport {

    @Autowired
    private ItemService itemService;

    @Override
    public String getAsText() {
        Item item = (Item) getValue();

        return item == null ? "" : item.getName();
    }

    @Override
    public void setAsText(String itemId) {

        if (Objects.isNull(itemId) || itemId.isEmpty()) {
            setValue(null);
            return;
        }

        setValue(itemService.findById(Integer.parseInt(itemId)));
    }
}
