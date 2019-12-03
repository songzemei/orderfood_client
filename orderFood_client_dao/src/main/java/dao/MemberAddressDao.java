package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAddressDao {
    @Insert("insert into member_address values(#{memberId},#{addressId})")
    void add(@Param("memberId") String memberId, @Param("addressId") String addressId);
}
