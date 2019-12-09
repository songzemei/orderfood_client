package service;

import dao.MemberDao;
import dao.OrdersDao;
import dao.RiderDao;
import domain.Member;
import domain.Result;
import domain.Rider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class RiderService {
    @Autowired
    private RiderDao riderDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrdersDao ordersDao;

    //接单
    public Result receiveOrders(String ordersId) {
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);
        Rider rider = riderDao.findById(member.getId());
        if (rider.getWorkStatus()==1){
            return new Result(false,"你正在派送中，无法接单...");
        }else {
            // 把订单的骑手id修改,将订单状态改为1：已配送
            ordersDao.updateOrdersStatus(ordersId,member.getId());
            //骑手余额+5
            member.setBalance(member.getBalance()+5);
            memberDao.updateBalance(member);
            //把骑手的状态修改 将workStatus改为1:工作中，新增接单时间,接单数量+1
            rider.setWorkStatus(1);
            rider.setOrderTime(new Date());
            rider.setOrderCount(rider.getOrderCount()+1);
            riderDao.receiveOrders(rider);
            return new Result(true,"接单成功，送餐路上注意安全...");
        }
    }

    //根据id查找骑手
    public Rider findById(String memberId) {
        return riderDao.findById(memberId);
    }

    //配送一单的时间为60s 超过60s就把配送员状态改为0：空闲
    public void judge() {
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);
        Rider rider = riderDao.findById(member.getId());
        if (rider!=null){
            if (rider.getOrderTime()!=null){
                if (rider.getWorkStatus() == 1 && (new Date().getTime() - rider.getOrderTime().getTime()) > 60 * 1000) {
                    riderDao.ordersFinish(rider.getMemberId());
                }
            }
        }

    }
}
