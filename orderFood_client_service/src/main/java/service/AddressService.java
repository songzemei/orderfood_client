package service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dao.AddressDao;
import dao.MemberDao;
import domain.Address;
import domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private MemberDao memberDao;

    //查找所有地址
    public PageInfo<Address> all(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);

        List<Address> addresses = addressDao.all(member.getId());
        return new PageInfo<>(addresses);
    }

    //修改地址
    public void update(Address address) {
        addressDao.update(address);
    }

    //添加地址
    public void add(Address address) {
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);

        address.setId(UUID.randomUUID().toString());
        address.setMemberId(member.getId());
        addressDao.add(address);
    }

    //通过地址id 查找地址
    public Address findById(String id) {
        return addressDao.findById(id);
    }

    //通过会员id 查找地址
    public List<Address> findByMemberId(String memberId) {
        return addressDao.all(memberId);
    }
}
