package service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dao.AddressDao;
import domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressDao addressDao;

    //查找所有地址
    public PageInfo<Address> all(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Address> addresses = addressDao.all();
        return new PageInfo<>(addresses);
    }
}
