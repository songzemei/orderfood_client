package controller;

import com.github.pagehelper.PageInfo;
import domain.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.OrdersService;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    //根据会员id查找对应的订单 再根据订单id查找订单里的产品
    @RequestMapping("/all")
    public ModelAndView findByMemberId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize) {
        PageInfo<Orders> allOrders = ordersService.findByMemberId(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("allOrders", allOrders);
        modelAndView.setViewName("orders_list");
        return modelAndView;
    }

    //add订单
    @RequestMapping("/add")
    public void add(Orders orders) {
        ordersService.add(orders);
    }
}