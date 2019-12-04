package dao;

import domain.Product;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {
    //查找菜单里的所有产品
    @Select("select * from product")
    List<Product> all();

    //根据id查询
    @Select("select * from product where id=#{id}")
    Product findById(String id);
}
