package dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleDao {

    //给用户添加一个ROLE_MEMBER角色
    @Insert("insert into member_role values(#{memberId},'486f8ebc-57cc-4ad3-8e51-300515da5d10')")
    void addRoleToMember(String memberId);

    //用户注册为骑手 给用户添加一个ROLE_RIDER角色
    @Insert("insert into member_role values(#{memberId},'7e99f33f-06bf-41c2-85d3-576f474a1b4b')")
    void rigisterRider(String memberId);

    //用户注销骑手身份 把用户的ROLE_RIDER角色删除
    @Delete("delete from member_role where memberId=#{memberId} and roleId='7e99f33f-06bf-41c2-85d3-576f474a1b4b'")
    void del(String memberId);
}
