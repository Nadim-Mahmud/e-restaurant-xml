package net.therap.estaurant.controller;

import net.therap.estaurant.command.Password;
import net.therap.estaurant.command.Profile;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.service.UserService;
import net.therap.estaurant.validator.PasswordValidator;
import net.therap.estaurant.validator.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static net.therap.estaurant.constant.Constants.*;


/**
 * @author nadimmahmud
 * @since 1/25/23
 */
@Controller
@SessionAttributes({PASSWORD, PROFILE})
public class ProfileController {

    private static final String UPDATE_PASSWORD_URL = "update-password";
    private static final String UPDATE_PASSWORD_VIEW = "password-form";
    private static final String SAVE_PASSWORD_URL = "/update-password/update";

    private static final String UPDATE_PROFILE_URL = "update-profile";
    private static final String UPDATE_PROFILE_VIEW = "profile-form";
    private static final String SAVE_PROFILE_URL = "/update-profile/update";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileValidator profileValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @InitBinder(PASSWORD)
    public void passwordBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordValidator());
    }

    @InitBinder(PROFILE)
    public void profileBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(profileValidator);
    }

    @GetMapping(UPDATE_PASSWORD_URL)
    public String showPasswordUpdatePage(ModelMap modelMap, @SessionAttribute(ACTIVE_USER) User user) {
        Password password = new Password();
        password.setStoredUserPassword(user.getPassword());
        modelMap.put(PASSWORD, password);

        return UPDATE_PASSWORD_VIEW;
    }

    @PostMapping(SAVE_PASSWORD_URL)
    String savePassword(@SessionAttribute(ACTIVE_USER) User user,
                        @Valid @ModelAttribute(PASSWORD) Password password,
                        BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {

            return UPDATE_PASSWORD_VIEW;
        }

        user.setPassword(password.getNewPassword());
        userService.saveOrUpdate(user);

        return REDIRECT;
    }

    @GetMapping(UPDATE_PROFILE_URL)
    String updateProfile(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(PROFILE, userService.getProfileObject(user));

        return UPDATE_PROFILE_VIEW;
    }

    @PostMapping(SAVE_PROFILE_URL)
    String saveOrUpdateProfile(@SessionAttribute(ACTIVE_USER) User user,
                               @Valid @ModelAttribute(PROFILE) Profile profile,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws Exception {

        if (user.getId() != profile.getId()) {
            return REDIRECT;
        }

        if (bindingResult.hasErrors()) {
            return UPDATE_PROFILE_VIEW;
        }

        user = userService.updateUserByProfile(user, profile);
        userService.saveOrUpdate(user);
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.profile.updated", null, Locale.getDefault()));

        return REDIRECT;
    }
}
