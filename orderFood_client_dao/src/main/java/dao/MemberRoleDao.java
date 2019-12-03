package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleDao {
    @Insert("insert into member_role values(#{memberId},'486f8ebc-57cc-4ad3-8e51-300515da5d10')")
    void addRoleToMember(String memberId);
}
