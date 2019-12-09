package controller;


import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.AddressService;
import service.MemberService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private AddressService addressService;

    //注册
    @RequestMapping("/register")
    public String register(Member member, ModelMap map) throws MyException {
        Result result = memberService.register(member);
        map.addAttribute("result", result);
        return "result";
    }

    //激活
    @RequestMapping("/active")
    public String active(String email, String activeCode, ModelMap map) {
        Result result = memberService.active(email, activeCode);
        map.addAttribute("result", result);
        return "result";
    }

    //重发激活码
    @RequestMapping("/reActive")
    public String reActive(String email, ModelMap map) {
        memberService.sendActiveCode(email);
        Result result = new Result(true, "已重新发送激活码，请去邮箱激活！");
        map.addAttribute("result", result);
        return "result";
    }

    //上传头像
    @RequestMapping("/upload")
    public @ResponseBody
    Result upload(MultipartFile file) throws IOException {
        Result result = memberService.upload(file);
        return result;
    }

    //支付信息页面
    @RequestMapping("/pay_list")
    public ModelAndView pay_list(double totalPrice) {
        SecurityContext context = SecurityContextHolder.getContext();// 获取到Security容器
        User user = (User) context.getAuthentication().getPrincipal();// 获取Security存的User对象
        String username = user.getUsername();// 获取到访问人
        Member member = memberService.findByUsername(username);
        List<Address> addresses = addressService.findByMemberId(member.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("addresses", addresses);
        modelAndView.getModelMap().addAttribute("totalPrice", totalPrice);
        modelAndView.setViewName("pay");
        return modelAndView;
    }

    //支付
    @RequestMapping("/pay")
    public ModelAndView pay(Orders orders, String payCode) {
        Result result = memberService.pay(orders, payCode);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("result", result);
        modelAndView.setViewName("after_login_result");
        return modelAndView;
    }

    //用户注册成为骑手
    @RequestMapping("/rigisterRider")
    public ModelAndView rigisterRider(String payCode) {
        Result result = memberService.rigisterRider(payCode);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("result", result);
        modelAndView.setViewName("after_login_result");
        return modelAndView;
    }

    //注销用户的骑手身份
    @RequestMapping("/cancelRider")
    public ModelAndView cancelRider(String payCode) {
        Result result = memberService.cancelRider(payCode);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("result", result);
        modelAndView.setViewName("after_login_result");
        return modelAndView;
    }

    //充钱
    @RequestMapping("/chongqian")
    public ModelAndView chongqian(double balance) {
        Result result = memberService.chongqian(balance);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("result", result);
        modelAndView.setViewName("after_login_result");
        return modelAndView;
    }

    //查询账户余额
    @RequestMapping("/balance")
    public ModelAndView balance() {
        double balance = memberService.balance();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("balance", balance);
        modelAndView.setViewName("chongqian");
        return modelAndView;
    }

//    @RequestMapping("/login")
//    public String login(ModelMap map){
//        String session_checkCode = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
//        Result result = userService.login(user, checkCode, session_checkCode);
//        if(!result.isResult()){
//            map.addAttribute("result",result);
//            return "result";
//        }else{
//            map.addAttribute("user",result.getMessage());
//            request.getSession().setAttribute("user",result.getMessage());
//            return "main";
//        }
//    }


//

//
//    @RequestMapping("/checkUsername")
//    public @ResponseBody
//    Result checkUsername(HttpServletRequest request, String username){
//        Result result = userService.checkUserName(username);
//        return result;
//    }
//

}
