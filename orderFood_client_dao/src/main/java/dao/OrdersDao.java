package dao;

import domain.Orders;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersDao {
    //根据会员id查找对应的订单 再根据订单id查找订单里的产品
    @Select("select * from orders where memberId=#{memberId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "ordersInfos",column = "id",many = @Many(select = "dao.OrdersProductDao.findByOrdersId"))
    })
    List<Orders> findByMemberId(String memberId);

    //添加订单
    @Insert("insert into orders values(#{id},#{orderTime},#{orderStatus},#{orderDesc},#{memberId})")
    void add(Orders orders);


    //根据订单id 修改订单状态 1：支付 /0：未支付
    @Update("update orders set ordersStatus='1' where id=#{id}")
    void updateOrdersStatus(String id);


}
