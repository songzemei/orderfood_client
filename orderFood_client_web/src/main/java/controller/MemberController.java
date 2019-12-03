package controller;


import domain.Member;
import domain.MyException;
import domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

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
    public @ResponseBody Result upload(MultipartFile file) throws IOException {
        System.out.println("controller upload");
        Result result = memberService.upload(file);
        return result;
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
