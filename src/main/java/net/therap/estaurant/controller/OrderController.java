package net.therap.estaurant.controller;

import net.therap.estaurant.entity.*;
import net.therap.estaurant.propertyEditor.ItemEditor;
import net.therap.estaurant.propertyEditor.RestaurantTableEditor;
import net.therap.estaurant.propertyEditor.StringToIntEditor;
import net.therap.estaurant.service.ItemService;
import net.therap.estaurant.service.OrderLineItemService;
import net.therap.estaurant.service.OrderService;
import net.therap.estaurant.service.RestaurantTableService;
import net.therap.estaurant.validator.OrderLineItemValidator;
import net.therap.estaurant.validator.OrderValidator;
import net.therap.estaurant.validator.QuantityGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static net.therap.estaurant.constant.Constants.*;


/**
 * @author nadimmahmud
 * @since 1/24/23
 */
@Controller
@RequestMapping("/waiter/*")
@SessionAttributes(ORDER)
public class OrderController {

    private static final String ORDER_REDIRECT_URL = "waiter/orders";
    private static final String ORDERS_URL = "orders";
    private static final String ORDER_VIEW = "order-list";
    private static final String ORDER_FORM_URL = "new-order";
    private static final String ORDER_FORM_VIEW = "order-form";
    private static final String ORDER_FORM_SAVE_URL = "new-order/list/save";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String ORDER_CANCEL_URL = "order/cancel";

    private static final String NEXT_PAGE = "new-order/next-page";
    private static final String ORDER_ITEMS_URL = "new-order/items";
    private static final String ORDER_ITEMS_VIEW = "order-item-form";
    private static final String ADD_ORDER_ITEM_URL = "new-order/items/add";
    private static final String REMOVE_ORDER_ITEM_URL = "new-order/items/remove";
    private static final String REDIRECT_ORDER_ITEMS_URL = "waiter/new-order/items";
    private static final String ORDER_LINE_ITEM_ID = "orderLineItemId";

    private static final String REDIRECT_WAITER_NOTIFICATION_URL = "waiter/notification";
    private static final String WAITER_NOTIFICATION_URL = "waiter/notification";
    private static final String WAITER_NOTIFICATION_VIEW = "waiter-notification";
    private static final String WAITER_NOTIFICATION_SERVED = "notification/mark-served";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLineItemService orderLineItemService;

    @Autowired
    private RestaurantTableService restaurantTableService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemEditor itemEditor;

    @Autowired
    private RestaurantTableEditor restaurantTableEditor;

    @Autowired
    private OrderValidator orderValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(Item.class, itemEditor);
        webDataBinder.registerCustomEditor(RestaurantTable.class, restaurantTableEditor);
        webDataBinder.registerCustomEditor(Integer.class, "quantity", new StringToIntEditor());
    }

    @InitBinder(ORDER)
    public void orderBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(orderValidator);
    }

    @GetMapping(ORDERS_URL)
    public String showOrders(
            @SessionAttribute(ACTIVE_USER) User user,
            ModelMap modelMap) {

        modelMap.put(ORDER_LIST, orderService.findActiveOrderListByWaiterId(user.getId()));
        modelMap.put(NAV_ITEM, ORDERS);

        return ORDER_VIEW;
    }

    @GetMapping(ORDER_FORM_URL)
    public String showOrderForm(@RequestParam(value = ORDER_ID_PARAM, required = false) String orderId,
                                @SessionAttribute(ACTIVE_USER) User user,
                                ModelMap modelMap) {
        Order order = Objects.nonNull(orderId) ? orderService.findById(Integer.parseInt(orderId)) : new Order();

        modelMap.put(ORDER, order);
        modelMap.put(RESTAURANT_TABLE_LIST, restaurantTableService.findByWaiterId(user.getId()));
        modelMap.put(NAV_ITEM, ORDER_FORM);

        return ORDER_FORM_VIEW;
    }

    @PostMapping(NEXT_PAGE)
    public String orderTableValidationPage(@SessionAttribute(ACTIVE_USER) User user,
                                           @Valid @ModelAttribute(ORDER) Order order,
                                           BindingResult bindingResult,
                                           ModelMap modelMap) {

        if (bindingResult.hasErrors()) {
            modelMap.put(RESTAURANT_TABLE_LIST, restaurantTableService.findByWaiterId(user.getId()));
            modelMap.put(NAV_ITEM, ORDER_FORM);

            return ORDER_FORM_VIEW;
        }

        return REDIRECT + REDIRECT_ORDER_ITEMS_URL;
    }


    @GetMapping(ORDER_ITEMS_URL)
    public String showOrderItems(@SessionAttribute(ORDER) Order order, ModelMap modelMap) {
        modelMap.put(ORDER_LINE_ITEM, new OrderLineItem());
        modelMap.put(ORDER_LINE_ITEM_LIST, order.getOrderLineItemList());
        modelMap.put(ITEM_LIST, itemService.findAvailable());
        modelMap.put(NAV_ITEM, ORDER_FORM);

        return ORDER_ITEMS_VIEW;
    }

    @PostMapping(ADD_ORDER_ITEM_URL)
    public String addOrderItem(@SessionAttribute(ORDER) Order order,
                               @Validated(QuantityGroup.class) @ModelAttribute(ORDER_LINE_ITEM) OrderLineItem orderLineItem,
                               BindingResult bindingResult,
                               ModelMap modelMap) {
        orderLineItem.setOrderStatus(OrderStatus.ORDERED);

        modelMap.put(ITEM_LIST, itemService.findAvailable());
        modelMap.put(ORDER_LINE_ITEM, orderLineItem);
        modelMap.put(ORDER_LINE_ITEM_LIST, order.getOrderLineItemList());
        modelMap.put(NAV_ITEM, ORDER_FORM);

        OrderLineItemValidator.validate(order.getOrderLineItemList(), orderLineItem, bindingResult);

        if (!bindingResult.hasErrors()) {
            order.addOrderLineItem(orderLineItem);
        }

        return ORDER_ITEMS_VIEW;
    }

    @PostMapping(REMOVE_ORDER_ITEM_URL)
    public String removeOrderItem(@SessionAttribute(ORDER) Order order,
                                  @RequestParam(ORDER_LINE_ITEM_ID) int orderLineItemId) {
        order.removeOrderLineItem(new OrderLineItem(orderLineItemId));

        return REDIRECT + REDIRECT_ORDER_ITEMS_URL;
    }

    @PostMapping(ORDER_FORM_SAVE_URL)
    public String saveOrUpdateResOrder(@SessionAttribute(ORDER) Order order,
                                       SessionStatus sessionStatus,
                                       RedirectAttributes redirectAttributes) throws Exception {

        if (order.getOrderLineItemList().isEmpty()) {
            redirectAttributes.addFlashAttribute(EMPTY_LIST, EMPTY_LIST);

            return REDIRECT + REDIRECT_ORDER_ITEMS_URL;
        }

        order.setStatus(OrderStatus.ORDERED);
        orderService.saveOrUpdate(order);
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.update", null, Locale.getDefault()));
        sessionStatus.setComplete();

        return REDIRECT + ORDER_REDIRECT_URL;
    }

    @PostMapping(ORDER_CANCEL_URL)
    public String cancelOrder(@RequestParam(ORDER_ID_PARAM) int orderId, RedirectAttributes redirectAttributes) throws Exception {

        if (orderLineItemService.isOrderOnProcess(orderId)) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.cancel.inUse", null, Locale.getDefault()));
        } else {
            orderService.cancel(orderId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.cancel", null, Locale.getDefault()));
        }

        return REDIRECT + ORDER_REDIRECT_URL;
    }

    @GetMapping(WAITER_NOTIFICATION_URL)
    public String showWaiterNotification(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.getOrderListWithStatus(user.getId()));
        modelMap.put(NAV_ITEM, NOTIFICATION);

        return WAITER_NOTIFICATION_VIEW;
    }

    @PostMapping(WAITER_NOTIFICATION_SERVED)
    public String markServed(@RequestParam(ORDER_ID_PARAM) int orderId) throws Exception {
        Order order = orderService.findById(orderId);

        orderService.saveAsServed(order);

        return REDIRECT + REDIRECT_WAITER_NOTIFICATION_URL;
    }
}
