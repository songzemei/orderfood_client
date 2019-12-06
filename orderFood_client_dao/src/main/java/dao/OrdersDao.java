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
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "cars", column = "id", many = @Many(select = "dao.CarDao.findByOrdersId")),
            @Result(property = "address", column = "addressId", one = @One(select = "dao.AddressDao.findById"))
    })
    List<Orders> findByMemberId(String memberId);

    //添加订单
    @Insert("insert into orders values(#{id},#{orderTime},#{orderStatus},#{orderDesc},#{memberId},#{payType},#{totalPrice},#{addressId},#{riderId})")
    void add(Orders orders);

    //根据订单id 修改订单状态 1：已完成/0：未完成，修改骑手id
    @Update("update orders set orderStatus=1,riderId=#{riderId} where id=#{ordersId}")
    void updateOrdersStatus(@Param("ordersId") String ordersId, @Param("riderId") String riderId);

    //根据会员id和订单id查找对应的订单
    @Select("select * from orders where memberId=#{memberId} and id=#{ordersId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "cars", column = "id", many = @Many(select = "dao.CarDao.findByOrdersId")),
            @Result(property = "address", column = "addressId", one = @One(select = "dao.AddressDao.findById"))
    })
    Orders info(@Param("memberId") String memberId, @Param("ordersId") String ordersId);

    //分页查询所有未配送的订单  即 orderStatus=0
    @Select("select * from orders where orderStatus=0")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "cars", column = "id", many = @Many(select = "dao.CarDao.findByOrdersId")),
            @Result(property = "address", column = "addressId", one = @One(select = "dao.AddressDao.findById")),
            @Result(property = "member", column = "memberId", one = @One(select = "dao.MemberDao.findById"))
    })
    List<Orders> allUnFinish();

    //根据订单id查找对应的订单
    @Select("select * from orders where  id=#{ordersId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "cars", column = "id", many = @Many(select = "dao.CarDao.findByOrdersId")),
            @Result(property = "address", column = "addressId", one = @One(select = "dao.AddressDao.findById"))
    })
    Orders infoById(String ordersId);

    //根据骑手id查询所有订单 分页
    @Select("select * from orders where riderId=#{riderId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "cars", column = "id", many = @Many(select = "dao.CarDao.findByOrdersId")),
            @Result(property = "address", column = "addressId", one = @One(select = "dao.AddressDao.findById")),
            @Result(property = "member", column = "memberId", one = @One(select = "dao.MemberDao.findById"))
    })
    List<Orders> allByRiderId(String riderId);
}
