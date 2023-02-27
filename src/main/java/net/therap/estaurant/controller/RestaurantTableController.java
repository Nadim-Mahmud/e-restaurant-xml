package net.therap.estaurant.controller;

import net.therap.estaurant.entity.RestaurantTable;
import net.therap.estaurant.propertyEditor.RestaurantTableEditor;
import net.therap.estaurant.service.OrderService;
import net.therap.estaurant.service.RestaurantTableService;
import net.therap.estaurant.validator.RestaurantTableValidator;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.util.Objects.nonNull;
import static net.therap.estaurant.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 2/9/23
 */
@Controller
@RequestMapping("/admin/*")
@SessionAttributes(RESTAURANT_TABLE)
public class RestaurantTableController {

    private static final String RES_TABLE_REDIRECT_URL = "admin/res-table";
    private static final String RES_TABLE_URL = "res-table";
    private static final String RES_TABLE_VIEW = "res-table-list";
    private static final String RES_TABLE_FORM_URL = "res-table/form";
    private static final String RES_TABLE_FORM_SAVE_URL = "res-table/save";
    private static final String RES_TABLE_FORM_VIEW = "res-table-form";
    private static final String RES_TABLE_ID_PARAM = "resTableId";
    private static final String RES_TABLE_DELETE_URL = "res-table/delete";

    @Autowired
    private RestaurantTableService restaurantTableService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestaurantTableEditor restaurantTableEditor;

    @Autowired
    private RestaurantTableValidator restaurantTableValidator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder(RESTAURANT_TABLE)
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(RestaurantTable.class, restaurantTableEditor);
        webDataBinder.addValidators(restaurantTableValidator);
    }

    @GetMapping(RES_TABLE_URL)
    public String showRestaurantTable(ModelMap modelMap) {
        modelMap.put(RESTAURANT_TABLE_LIST, restaurantTableService.findAll());
        modelMap.put(NAV_ITEM, RESTAURANT_TABLE);

        return RES_TABLE_VIEW;
    }

    @GetMapping(RES_TABLE_FORM_URL)
    public String showTableForm(@RequestParam(value = RES_TABLE_ID_PARAM, required = false) String resTableId,
                                ModelMap modelMap) {
        RestaurantTable resTable = nonNull(resTableId) ? restaurantTableService.findById(Integer.parseInt(resTableId)) : new RestaurantTable();

        modelMap.put(RESTAURANT_TABLE, resTable);
        modelMap.put(NAV_ITEM, RESTAURANT_TABLE);

        return RES_TABLE_FORM_VIEW;
    }

    @PostMapping(RES_TABLE_FORM_SAVE_URL)
    public String saveOrUpdateResTable(@Valid @ModelAttribute(RESTAURANT_TABLE) RestaurantTable restaurantTable,
                                       BindingResult bindingResult,
                                       ModelMap modelMap,
                                       SessionStatus sessionStatus,
                                       RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            modelMap.put(NAV_ITEM, RESTAURANT_TABLE);

            return RES_TABLE_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (restaurantTable.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        restaurantTableService.saveOrUpdate(restaurantTable);
        sessionStatus.setComplete();

        return REDIRECT + RES_TABLE_REDIRECT_URL;
    }

    @PostMapping(RES_TABLE_DELETE_URL)
    public String deleteResTable(@RequestParam(RES_TABLE_ID_PARAM) String resTableId,
                                 RedirectAttributes redirectAttributes) throws Exception {

        if (orderService.isTableInUse(Integer.parseInt(resTableId))) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.delete.inUse", null, Locale.getDefault()));
        } else {
            restaurantTableService.delete(Integer.parseInt(resTableId));
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));
        }

        return REDIRECT + RES_TABLE_REDIRECT_URL;
    }
}
