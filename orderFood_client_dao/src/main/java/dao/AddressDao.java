package dao;

import domain.Address;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao {
    //查找所有地址
    @Select("select * from address")
    List<Address> all();
}
