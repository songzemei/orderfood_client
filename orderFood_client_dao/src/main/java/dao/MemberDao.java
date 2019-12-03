package dao;



import domain.Member;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao {
    @Select("select * from member where username=#{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roles", column = "id", many = @Many(select = "dao.RoleDao.findByMemberId"))
    })
    Member findByUsername(String username);//通过username查询member

    @Insert("insert into member values(#{id},#{username},#{password},#{email},#{phoneNum},#{active},#{balance},#{paycode},#{headerImg},#{status})")
    void add(Member member);//注册

    @Update("update member set active = '1' where email = #{email}")
    void active(String email);//邮箱激活


}
