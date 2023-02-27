package net.therap.estaurant.controller;

import net.therap.estaurant.entity.*;
import net.therap.estaurant.propertyEditor.CategoryEditor;
import net.therap.estaurant.propertyEditor.ItemEditor;
import net.therap.estaurant.propertyEditor.RestaurantTableEditor;
import net.therap.estaurant.service.ItemService;
import net.therap.estaurant.service.OrderService;
import net.therap.estaurant.service.RestaurantTableService;
import net.therap.estaurant.service.UserService;
import net.therap.estaurant.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.util.Objects.nonNull;
import static net.therap.estaurant.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 1/18/23
 */
@Controller
@RequestMapping("/admin/*")
@SessionAttributes({CHEF, WAITER})
public class UserController {

    private static final String CHEF_REDIRECT_URL = "admin/chef";
    private static final String CHEF_URL = "chef";
    private static final String CHEF_VIEW = "chef-list";
    private static final String CHEF_FORM_URL = "chef/form";
    private static final String CHEF_FORM_SAVE_URL = "chef/save";
    private static final String CHEF_FORM_VIEW = "chef-form";
    private static final String CHEF_ID_PARAM = "chefId";
    private static final String CHEF_DELETE_URL = "chef/delete";

    private static final String WAITER_REDIRECT_URL = "admin/waiter";
    private static final String WAITER_URL = "waiter";
    private static final String WAITER_VIEW = "waiter-list";
    private static final String WAITER_FORM_URL = "waiter/form";
    private static final String WAITER_FORM_SAVE_URL = "waiter/save";
    private static final String WAITER_FORM_VIEW = "waiter-form";
    private static final String WAITER_ID_PARAM = "waiterId";
    private static final String WAITER_DELETE_URL = "waiter/delete";

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RestaurantTableService restaurantTableService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestaurantTableEditor restaurantTableEditor;

    @Autowired
    private ItemEditor itemEditor;

    @Autowired
    private CategoryEditor categoryEditor;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder({WAITER, CHEF})
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(Category.class, categoryEditor);
        webDataBinder.registerCustomEditor(Item.class, itemEditor);
        webDataBinder.registerCustomEditor(RestaurantTable.class, restaurantTableEditor);
        webDataBinder.addValidators(emailValidator);
    }

    @GetMapping(CHEF_URL)
    public String showChef(ModelMap modelMap) {
        modelMap.put(CHEF_LIST, userService.findChef());
        modelMap.put(NAV_ITEM, CHEF);

        return CHEF_VIEW;
    }

    @GetMapping(CHEF_FORM_URL)
    public String showChefForm(@RequestParam(value = CHEF_ID_PARAM, required = false) String chefId,
                               ModelMap modelMap
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User chef = nonNull(chefId) ? userService.findById(Integer.parseInt(chefId)) : new User();
        modelMap.put(CHEF, chef);
        setupReferenceDataChefForm(modelMap, chef);

        return CHEF_FORM_VIEW;
    }

    @PostMapping(CHEF_FORM_SAVE_URL)
    public String saveOrUpdateChef(@Valid @ModelAttribute(CHEF) User user,
                                   BindingResult bindingResult,
                                   ModelMap modelMap,
                                   SessionStatus sessionStatus,
                                   RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            setupReferenceDataChefForm(modelMap, user);

            return CHEF_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (user.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        user.setType(UserType.CHEF);
        userService.saveOrUpdate(user);
        sessionStatus.setComplete();

        return REDIRECT + CHEF_REDIRECT_URL;
    }

    @PostMapping(CHEF_DELETE_URL)
    public String deleteChef(@RequestParam(CHEF_ID_PARAM) int chefId,
                             RedirectAttributes redirectAttributes) throws Exception {
        userService.delete(chefId);
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));

        return REDIRECT + CHEF_REDIRECT_URL;
    }

    @GetMapping(WAITER_URL)
    public String showWaiter(ModelMap modelMap) {
        modelMap.put(WAITER_LIST, userService.findWaiter());
        modelMap.put(NAV_ITEM, WAITER);

        return WAITER_VIEW;
    }

    @GetMapping(WAITER_FORM_URL)
    public String showWaiterForm(@RequestParam(value = WAITER_ID_PARAM, required = false) String waiterId,
                                 ModelMap modelMap
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User waiter = nonNull(waiterId) ? userService.findById(Integer.parseInt(waiterId)) : new User();
        modelMap.put(WAITER, waiter);
        setupReferenceDataWaiterForm(modelMap, waiter);

        return WAITER_FORM_VIEW;
    }

    @PostMapping(WAITER_FORM_SAVE_URL)
    public String saveOrUpdateWaiter(@Valid @ModelAttribute(WAITER) User user,
                                     BindingResult bindingResult,
                                     ModelMap modelMap,
                                     SessionStatus sessionStatus,
                                     RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            setupReferenceDataWaiterForm(modelMap, user);

            return WAITER_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (user.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        user.setType(UserType.WAITER);
        userService.saveOrUpdate(user);
        sessionStatus.setComplete();

        return REDIRECT + WAITER_REDIRECT_URL;
    }

    @PostMapping(WAITER_DELETE_URL)
    public String deleteWaiter(@RequestParam(WAITER_ID_PARAM) int waiterId,
                               RedirectAttributes redirectAttributes) throws Exception {

        if (orderService.findActiveOrderListByWaiterId(waiterId).size() > 0) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.delete.waiter", null, Locale.getDefault()));
        } else {
            userService.delete(waiterId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));
        }
        return REDIRECT + WAITER_REDIRECT_URL;
    }

    private void setupReferenceDataChefForm(ModelMap modelMap, User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        modelMap.put(ITEM_OPTION_LIST, itemService.findAll());
        modelMap.put(NAV_ITEM, CHEF);

        if (!user.isNew()) {
            modelMap.put(UPDATE_PAGE, true);
        }

        if (user.isNew()) {
            user.setPassword("");
        }
    }

    private void setupReferenceDataWaiterForm(ModelMap modelMap, User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        modelMap.put(RESTAURANT_TABLE_LIST, restaurantTableService.findAll());
        modelMap.put(NAV_ITEM, WAITER);

        if (!user.isNew()) {
            modelMap.put(UPDATE_PAGE, true);
        }

        if (user.isNew()) {
            user.setPassword("");
        }
    }
}
