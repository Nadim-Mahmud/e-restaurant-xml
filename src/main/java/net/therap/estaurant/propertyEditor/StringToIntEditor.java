package net.therap.estaurant.propertyEditor;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/24/23
 */
@Component
public class StringToIntEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Integer number = (Integer) getValue();

        return number.toString();
    }

    @Override
    public void setAsText(String number) {

        if (Objects.isNull(number) || number.isEmpty()) {
            setValue(0);

            return;
        }

        setValue(Integer.parseInt(number));
    }
}