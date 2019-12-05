package service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dao.CarDao;
import dao.MemberDao;
import dao.OrdersDao;
import domain.Car;
import domain.Member;
import domain.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersService {
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private CarDao carDao;

    //根据会员id查找对应的所有订单
    public PageInfo<Orders> findByMemberId(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);
        List<Orders> allOrders = ordersDao.findByMemberId(member.getId());
        return new PageInfo<>(allOrders);
    }

    //add订单
    public void add(Orders orders, String memberId) {
        orders.setId(UUID.randomUUID().toString());
        orders.setOrderTime(new Date());
        orders.setOrderStatus(0);
        orders.setMemberId(memberId);
        ordersDao.add(orders);
        //根据会员id 更改购物车的ordersid
        carDao.updateOrdersId(orders.getId(), memberId);
        //生成订单后 将购物车的状态改为1：已支付，再查询购物车将查不到
        carDao.updateStatus(memberId);
    }


    //根据会员id和订单id查找对应的订单
    public Orders info(String ordersId) {
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);
        Orders orders = ordersDao.info(member.getId(), ordersId);
        return orders;
    }
}
