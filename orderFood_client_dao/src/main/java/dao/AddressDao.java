package dao;

import domain.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao {
    //查找所有地址
    @Select("SELECT * FROM address WHERE id IN(SELECT addressId FROM member_address WHERE memberId=#{memberId})")
    List<Address> all(String memberId);

    //修改地址
    @Update("update address set username=#{username},phoneNum=#{phoneNum},addressName=#{addressName} where id=#{id}")
    void update(Address address);

    //添加地址
    @Insert("insert into address values(#{id},#{username},#{phoneNum},#{addressName})")
    void add(Address address);

    //通过id查找地址
    @Select("select * from address where id=#{id}")
    Address findById(String id);
}
