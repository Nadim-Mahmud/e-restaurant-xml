package net.therap.estaurant.controller;

import net.therap.estaurant.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static net.therap.estaurant.constant.Constants.ITEM_LIST;

/**
 * @author nadimmahmud
 * @since 2/9/23
 */
@Controller
@RequestMapping("/waiter/*")
public class WaiterController {

    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";

    @Autowired
    private ItemService itemService;

    @GetMapping(HOME_URL)
    public String waiterHome(ModelMap modelMap) {
        modelMap.put(ITEM_LIST, itemService.findAll());

        return HOME_VIEW;
    }
}
