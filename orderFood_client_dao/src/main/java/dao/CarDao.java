package dao;

import domain.Car;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDao {
    //查询
    @Select("select * from car where memberId=#{memberId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "product",column = "productId",one = @One(select = "dao.ProductDao.findById"))
    })
    List<Car> all(String memberId);

    //add
    @Insert("insert into car values(#{id},#{productId},#{productCount},#{memberId}，#{ordersId})")
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

    //清空购物车
    @Delete("delete from car")
    void empty();

    //根据ordersId查询
    @Select("select * from car where ordersId=#{ordersId}")
    List<Car> findByOrdersId(String ordersId);

}
