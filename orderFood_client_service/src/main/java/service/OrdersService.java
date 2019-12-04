package service;

import dao.OrdersDao;
import domain.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    @Autowired
    private OrdersDao ordersDao;

    public void add(Orders orders){
        ordersDao.add(orders);
    }
}
