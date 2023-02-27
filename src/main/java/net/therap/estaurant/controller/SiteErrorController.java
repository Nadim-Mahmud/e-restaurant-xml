//package net.therap.estaurant.controller;
//
//import net.therap.estaurant.constant.Constants;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author nadimmahmud
// * @since 1/30/23
// */
//@Controller
//public class SiteErrorController implements ErrorController {
//
//    private static final String ERROR_VIEW = "error";
//
//    @RequestMapping("/error")
//    public String handleError(HttpServletRequest request, ModelMap modelMap) {
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//        if (status != null) {
//            modelMap.put(Constants.ERROR_CODE, Integer.valueOf(status.toString()));
//        }
//
//        return ERROR_VIEW;
//    }
//}
