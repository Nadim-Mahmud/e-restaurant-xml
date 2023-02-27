package net.therap.estaurant.validator;

import net.therap.estaurant.command.Profile;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author nadimmahmud
 * @since 1/23/23
 */
@Component
public class ProfileValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Profile.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "email", "input.email");
        User user = userService.updateUserByProfile(new User(), (Profile) target);

        if (userService.isDuplicateEmail(user)) {
            errors.rejectValue("email", "input.email.duplicate");
        }
    }
}
