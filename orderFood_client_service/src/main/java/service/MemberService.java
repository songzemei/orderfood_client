package service;


import dao.MemberDao;
import dao.MemberRoleDao;
import domain.Member;
import domain.MyException;
import domain.Result;
import domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        member.setPaycode(null);
        member.setStatus(1);
        memberDao.add(member);
        memberRoleDao.addRoleToMember(member.getId());
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

    public Result upload(MultipartFile file) throws IOException {
        System.out.println("service upload");
        String name = file.getOriginalFilename();
        name = UUID.randomUUID()+"_"+name;
        UploadUtil.uploadToImgServer(file,"http://localhost:81/upload/",name);
        Result result = new Result();
        result.setResult(true);
        result.setMessage(name);
        return result;
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
