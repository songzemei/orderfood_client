package service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dao.CarDao;
import dao.MemberDao;
import domain.Car;
import domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {
    @Autowired
    private CarDao carDao;
    @Autowired
    private MemberDao memberDao;
    private Car car = new Car();

    //分页查询购物车
    public PageInfo<Car> all(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);
        List<Car> cars = carDao.all(member.getId());
        return new PageInfo<>(cars);
    }


    //add购物车
    public void add(String productId) {
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);
        car.setProductId(productId);
        car.setMemberId(member.getId());
        Integer count = carDao.findCountByPIdAndMId(car);
        if (count == null) {
            car.setId(UUID.randomUUID().toString());
            car.setProductCount(1);
            carDao.add(car);
        } else {
            car.setProductCount(count + 1);
            carDao.updateCount(car);
        }
    }

    //del购物车
    public void del(String productId) {
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);
        car.setProductId(productId);
        car.setMemberId(member.getId());
        Integer count = carDao.findCountByPIdAndMId(car);
        if (count != null){
            if (count == 1) {
                carDao.del(car);
            } else if (count > 1) {
                car.setProductCount(count - 1);
                carDao.updateCount(car);
            }
        }
    }
}
