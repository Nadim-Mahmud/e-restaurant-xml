package net.therap.estaurant.propertyEditor;

import net.therap.estaurant.entity.Category;
import net.therap.estaurant.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/23/23
 */
@Component
public class CategoryEditor extends PropertyEditorSupport {

    @Autowired
    private CategoryService categoryService;

    @Override
    public String getAsText() {
        Category category = (Category) getValue();

        return category == null ? "" : category.getName();
    }

    @Override
    public void setAsText(String categoryId) {

        if (Objects.isNull(categoryId) || categoryId.isEmpty()) {
            setValue(null);

            return;
        }

        setValue(categoryService.findById(Integer.parseInt(categoryId)));
    }
}
