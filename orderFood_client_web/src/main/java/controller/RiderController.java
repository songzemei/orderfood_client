package controller;

import domain.Result;
import domain.Rider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.RiderService;

@Controller
@RequestMapping("/rider")
public class RiderController {
    @Autowired
    private RiderService riderService;

    //接单
    @RequestMapping("/receiveOrders")
    public ModelAndView receiveOrders(String ordersId) {
        Result result = riderService.receiveOrders(ordersId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("result",result);
        modelAndView.setViewName("after_login_result");
        return modelAndView;
    }
}
