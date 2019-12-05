package dao;

import domain.Car;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDao {
    //根据会员id查询对应购物车 要求支付状态为0：未支付的
    @Select("select * from car where memberId=#{memberId} and carStatus=0")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "product", column = "productId", one = @One(select = "dao.ProductDao.findById"))
    })
    List<Car> all(String memberId);

    //add
    @Insert("insert into car values(#{id},#{productId},#{productCount},#{memberId},#{ordersId},#{carStatus})")
    void add(Car car);

    //根据productId和memberId，修改 productCount
    @Update("update car set productCount=#{productCount} where productId=#{productId} and memberId=#{memberId}")
    void updateCount(Car car);

    //根据productId和memberId，删除 productCount
    @Delete("delete from car where productId=#{productId} and memberId=#{memberId}")
    void del(Car car);

    //根据productId和memberId，查询 productCount
    @Select("select productCount from car where productId=#{productId} and memberId=#{memberId}")
    Integer findCountByPIdAndMId(Car car);

    //根据ordersId查询
    @Select("select * from car where ordersId=#{ordersId}")
    List<Car> findByOrdersId(String ordersId);

    //会员和购物车的产品：一对多 ，购物车付款后 根据会员id 更改购物车状态为1：已支付
    @Update("update car set carStatus=1 where memberId=#{memberId}")
    void updateStatus(String memberId);

    //根据会员id 更改购物车的ordersid
    @Update("update car set ordersId=#{ordersId} where memberId=#{memberId}")
    void updateOrdersId(@Param("ordersId") String ordersId, @Param("memberId") String memberId);

    //    //清空购物车
//    @Delete("delete from car")
//    void empty();

}
