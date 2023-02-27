package net.therap.estaurant.controller;

import net.therap.estaurant.entity.OrderLineItem;
import net.therap.estaurant.entity.OrderStatus;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.service.ItemService;
import net.therap.estaurant.service.OrderLineItemService;
import net.therap.estaurant.validator.CookingTimeGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;

import static net.therap.estaurant.constant.Constants.*;


/**
 * @author nadimmahmud
 * @since 1/25/23
 */
@Controller
@RequestMapping("/chef/*")
@SessionAttributes(ORDER_LINE_ITEM)
public class ChefController {

    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";

    private static final String REDIRECT_CHEF_NOTIFICATION_URL = "chef/notification";
    private static final String CHEF_NOTIFICATION_URL = "notification";
    private static final String CHEF_NOTIFICATION_VIEW = "chef-notification";
    private static final String CHEF_ACCEPT_FORM_URL = "notification/form";
    private static final String CHEF_ACCEPT_FORM_VIEW = "chef-order-accept-form";
    private static final String CHEF_ACCEPT_FORM_SAVE_URL = "notification/form/save";
    private static final String ORDER_LINE_ITEM_ID_PARAM = "orderLineItemId";
    private static final String MARK_PREPARED_URL = "notification/mark-prepared";

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderLineItemService orderLineItemService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(HOME_URL)
    public String chefHome(ModelMap modelMap) {
        modelMap.put(ITEM_LIST, itemService.findAll());

        return HOME_VIEW;
    }

    @GetMapping(CHEF_NOTIFICATION_URL)
    public String showChefNotification(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(ORDER_LINE_ITEM_LIST, orderLineItemService.getOrderedNotificationsByUserId(user.getId()));
        modelMap.put(NAV_ITEM, NOTIFICATION);

        return CHEF_NOTIFICATION_VIEW;
    }

    @GetMapping(CHEF_ACCEPT_FORM_URL)
    public String showOrderAcceptForm(@RequestParam(ORDER_LINE_ITEM_ID_PARAM) String orderLineItemId, ModelMap modelMap) {
        modelMap.put(ORDER_LINE_ITEM, orderLineItemService.findById(Integer.parseInt(orderLineItemId)));
        modelMap.put(NAV_ITEM, NOTIFICATION);

        return CHEF_ACCEPT_FORM_VIEW;
    }

    @PostMapping(CHEF_ACCEPT_FORM_SAVE_URL)
    public String saveAcceptForm(@Validated(CookingTimeGroup.class) @ModelAttribute(ORDER_LINE_ITEM) OrderLineItem orderLineItem,
                                 BindingResult bindingResult,
                                 ModelMap modelMap,
                                 SessionStatus sessionStatus) throws Exception {

        if (bindingResult.hasErrors()) {
            modelMap.put(NAV_ITEM, NOTIFICATION);

            return CHEF_ACCEPT_FORM_VIEW;
        }

        orderLineItem.setAcceptedAt(new Date());
        orderLineItem.setOrderStatus(OrderStatus.PREPARING);
        orderLineItemService.saveOrUpdate(orderLineItem);
        sessionStatus.setComplete();

        return REDIRECT + REDIRECT_CHEF_NOTIFICATION_URL;
    }

    @PostMapping(MARK_PREPARED_URL)
    public String markOrderLineItemPrepared(@RequestParam(ORDER_LINE_ITEM_ID_PARAM) int orderLineItemId) throws Exception {
        OrderLineItem orderLineItem = orderLineItemService.findById(orderLineItemId);
        orderLineItem.setOrderStatus(OrderStatus.PREPARED);
        orderLineItemService.saveOrUpdate(orderLineItem);

        return REDIRECT + REDIRECT_CHEF_NOTIFICATION_URL;
    }
}
