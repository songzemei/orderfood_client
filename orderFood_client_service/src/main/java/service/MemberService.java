package service;


import dao.MemberDao;
import dao.MemberRoleDao;
import dao.RiderDao;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import util.MailUtils;
import util.Md5Util;
import util.UploadUtil;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService implements UserDetailsService {
    private String contextPath = "";
    //    private String uploadPath = "http://localhost:81/upload/";
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private MemberRoleDao memberRoleDao;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private RiderDao riderDao;

    //spring-security登录
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * 这个方法是给spring security调用的
         * spring security调用该方法，返回一个User对象
         * spring security通过操作该User对象完成认证。
         * 也就是说，不管我们查出来是什么对象，都要转成spring security的User对象
         */
        Member member = memberDao.findByUsername(username);
        // 把自己的UserInfo对象转成SpringSecurity的User对象
        List<SimpleGrantedAuthority> list = new ArrayList();
        for (Role role : member.getRoles()) {
            list.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        User user = new User(member.getUsername(),
//                "{noop}" + member.getPassword(),
                member.getPassword(),
                member.getStatus() == 1 ? true : false,
                true,
                true,
                true,
                list);
        return user;
    }

    //注册
    public Result register(Member member) throws MyException {
        member.setId(UUID.randomUUID().toString());
        member.setPassword(encoder.encode(member.getPassword()));
        member.setActive(0);
        member.setBalance(0.0);
        member.setPaycode("123");
        member.setStatus(1);
        memberDao.add(member);
        memberRoleDao.addRoleToMember(member.getId());//用户注册时 添加一个ROLE_MEMBER角色
        sendActiveCode(member.getEmail());
        return new Result(true, "用户注册成功！请在15分钟内去邮箱激活用户");
    }

    //生成激活码，将激活码存入redis，发送激活邮件
    public void sendActiveCode(String email) {
        String activeCode = UUID.randomUUID().toString();
        Jedis jedis = jedisPool.getResource();
        jedis.setex(email, 15 * 2, activeCode);
        String text = "这是激活邮件，请点击<a href='http://localhost/member/active?email=" + email +
                "&activeCode=" + activeCode + "'>" + "激活</a>,如果无法点击链接，请复制以下代码至浏览器访问：" +
                "'http://localhost/member/active?email=" + email + "&activeCode=" + activeCode;
        Thread thread = new Thread(new MailUtils(email, text, "激活邮件"));
        thread.start();
    }

    //激活
    public Result active(String email, String activeCode) {
        Result result = new Result();
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get(email);
        if (s == null || !activeCode.equals(s)) {
            // 激活失败
            result.setResult(false);
            result.setMessage("激活失败！<a href='" + contextPath + "/member/reActive?email=" + email + "'>重新发送激活码</a>");
        } else {
            result.setResult(true);
            result.setMessage("激活成功！请<a href='" + contextPath + "/index.jsp'>登录</a>");
            memberDao.active(email);
        }
        return result;
    }

    //上传头像
    public Result upload(MultipartFile file) throws IOException {
        System.out.println("service upload");
        String name = file.getOriginalFilename();
        name = UUID.randomUUID() + "_" + name;
        UploadUtil.uploadToImgServer(file, "http://localhost:81/upload/", name);
        Result result = new Result();
        result.setResult(true);
        result.setMessage(name);
        return result;
    }

    //通过username查询member
    public Member findByUsername(String username) {
        return memberDao.findByUsername(username);
    }

    //支付
    public Result pay(Orders orders, String payCode) {
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);
        if (!(member.getPaycode().equals(payCode))) {
            return new Result(false, "购物车付款失败,支付密码错误...");
        } else {
            //交易 事务 用户扣钱 商家加钱

            //生成订单
            ordersService.add(orders, member.getId());
            return new Result(true, "购物车付款成功，订单已生成，等待配送员接单...");
        }
    }

    //用户注册成为骑手
    public Result rigisterRider(String payCode) {
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);
        if (!(member.getPaycode().equals(payCode))) {
            return new Result(false, "支付密码错误...");
        } else {
            //需要交200押金和100服装费
            if (member.getBalance()<300){
                return new Result(false,"您的账户余额不足交纳押金和服装费，请充值后再试...");
            }else {
                //扣钱300

                //给用户添加ROLE_RIDER角色
                memberRoleDao.rigisterRider(member.getId());
                //rider表添加一个数据
                Rider rider = new Rider();
                rider.setMemberId(member.getId());
                rider.setWorkStatus(0);
                rider.setOrderCount(0);
                riderDao.add(rider);
                return new Result(true, "注册骑手成功，快去接单吧...");
            }
        }
    }

    //注销用户的骑手身份
    public Result cancelRider(String payCode) {
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberDao.findByUsername(username);
        if (!(member.getPaycode().equals(payCode))) {
            return new Result(false, "支付密码错误...");
        }else {
            //将200押金退给用户 100服装不退

            //把用户的ROLE_RIDER角色删除
            memberRoleDao.del(member.getId());
            //把rider表中的用户相关数据也删除
            riderDao.del(member.getId());
            return new Result(true,"注销骑手身份成功，200押金已退回到您的账户");
        }
    }

    //根据id查询会员
    public Member findById(String id){
        return memberDao.findById(id);
    }

//    public Result login(User user, String checkCode, String session_checkCode){
//        Result loginResult = new Result();
//        if(!session_checkCode.equalsIgnoreCase(checkCode)){
//            loginResult.setResult(false);
//            loginResult.setMessage("<h1>登录失败！验证码错误。请重新<a href='"+contextPath+"/index.jsp'>登录</a></h1>");
//        }else{
//            String password = user.getPassword();
//            try {
//                password = Md5Util.encodeByMd5(password);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            user.setPassword(password);
//            User queryUser = userDao.findUserByNameAndPassword(user);
//
//            if(queryUser != null){
//                if("N".equals(queryUser.getActive())){
//                    loginResult.setResult(false);
//                    loginResult.setMessage("用户未激活，请去邮箱激活用户或<a href='"+contextPath+"/user/reActive?username="+user.getUsername()+"'>重新发送激活码</a>");
//                }else{
//                    // 登录成功！
//                    loginResult.setResult(true);
//                    loginResult.setMessage(queryUser);
//                }
//            }else{
//                loginResult.setResult(false);
//                loginResult.setMessage("<h1>登录失败！用户名或密码错误。请重新<a href='"+contextPath+"/index.jsp'>登录</a></a></h1>");
//            }
//        }
//        return loginResult;
//    }


//    public Result checkUserName(String username){
//        Result result = new Result();
//        User user = userDao.findUserByUsername(username);
//        if(user == null){
//            result.setResult(true);
//        }else{
//            result.setResult(false);
//            result.setMessage("用户名已存在");
//        }
//        return result;
//    }


}
