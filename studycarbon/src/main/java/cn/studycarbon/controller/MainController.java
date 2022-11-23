package cn.studycarbon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 主页控制器
@Controller
public class MainController {
    // 主页
    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    // 返回首页
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    // 登录
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 登录错误
    @GetMapping("/login-error")
    public String loginError(Model model) {
        System.out.println("loginError==============================================");
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登录失败，用户名或者密码错误");
        return "login";
    }
}
