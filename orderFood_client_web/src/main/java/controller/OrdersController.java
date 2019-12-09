package controller;

import com.github.pagehelper.PageInfo;
import dao.RiderDao;
import domain.Member;
import domain.Orders;
import domain.Rider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.MemberService;
import service.OrdersService;
import service.RiderService;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private RiderService riderService;

    //根据会员id查找对应的所有订单 分页
    @RequestMapping("/all")
    public ModelAndView findByMemberId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize) {
        PageInfo<Orders> allOrders = ordersService.findByMemberId(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("allOrders", allOrders);
        modelAndView.setViewName("orders_list");
        return modelAndView;
    }

    //根据会员id和订单id查找对应的订单
    @RequestMapping("/info")
    public ModelAndView info(String ordersId) {
        Orders orders = ordersService.info(ordersId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("orders", orders);
        modelAndView.setViewName("orders_info");
        return modelAndView;
    }

    //分页查询所有未配送的订单  即 orderStatus=0
    @RequestMapping("/allUnFinish")
    public ModelAndView allUnFinish(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize) {
        riderService.judge();
        PageInfo<Orders> allOrders = ordersService.allUnFinish(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("allOrders", allOrders);
        modelAndView.setViewName("orders_list_unfinish");
        return modelAndView;
    }

    //根据订单id查找对应的订单
    @RequestMapping("/infoById")
    public ModelAndView infoById(String ordersId) {
        Orders orders = ordersService.infoById(ordersId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("orders", orders);
        modelAndView.setViewName("orders_info");
        return modelAndView;
    }

    //根据骑手id查询所有订单 分页
    @RequestMapping("/allByRiderId")
    public ModelAndView allByRiderId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize) {
        PageInfo<Orders> allOrders = ordersService.allByRiderId(pageNum, pageSize);
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberService.findByUsername(username);
        Rider rider = riderService.findById(member.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("allOrders", allOrders);
        modelAndView.getModelMap().addAttribute("orderCount", rider==null?0:rider.getOrderCount());
        modelAndView.setViewName("orders_list_finish");
        return modelAndView;
    }
}
