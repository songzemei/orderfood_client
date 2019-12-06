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
}
