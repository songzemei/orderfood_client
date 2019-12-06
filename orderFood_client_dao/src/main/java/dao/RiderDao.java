package dao;

import domain.Rider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiderDao {

    //查询所有骑手
    @Select("select * from rider")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "member",column = "memberId",one = @One(select = "dao.MemberDao.findById"))
    })
    List<Rider> all();

    //添加骑手
    @Insert("insert into rider values(#{memberId},#{workStatus},#{orderTime},#{orderCount})")
    void add(Rider rider);

    //删除骑手
    @Delete("delete from rider where memberId=#{memberId}")
    void del(String memberId);

    //接单
    @Update("update rider set workStatus=#{workStatus},orderTime=#{orderTime},orderCount=#{orderCount} where memberId=#{memberId}")
    void receiveOrders(Rider rider);

    //根据id查找骑手
    @Select("select * from rider where memberId=#{memberId}")
    Rider findById(String memberId);

    //配送一单的时间为60s 超过60s就把配送员状态改为0：空闲
    @Update("update rider set workStatus=0 ,orderTime=null where memberId=#{id}")
    void ordersFinish(String id);
}
