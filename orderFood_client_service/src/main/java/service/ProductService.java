package service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dao.ProductDao;
import domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    //分页查找所有产品
    public PageInfo<Product> all(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productDao.all();
        return new PageInfo<>(products);
    }

    //分页查询所有产品 并按照价格排序
    public PageInfo<Product> allOrderBy(int pageNum, int pageSize,String orderBy) {
        PageHelper.startPage(pageNum, pageSize,orderBy);
        List<Product> products = productDao.all();
        return new PageInfo<>(products);
    }
}
