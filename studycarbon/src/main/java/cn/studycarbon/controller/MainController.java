package cn.studycarbon.controller;

import cn.studycarbon.domain.Authority;
import cn.studycarbon.domain.User;
import cn.studycarbon.service.AuthorityService;
import cn.studycarbon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

// IntelliJ IDEA小技巧 —— 代码太乱？一键快速格式化代码
// https://blog.csdn.net/xue_xiaofei/article/details/106445967
// 使用“Control + A”全选代码，再使用“Control + Alt + L”，将代码格式规范化

// 主页控制器
@Controller
public class MainController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    // 主页
    @GetMapping("/")
    public String root() {
        logger.info("get / =>");
        return "redirect:/index";
    }

    // 返回首页
    @GetMapping("/index")
    public String index() {
        logger.info("get index =>");
        return "forward:/blogs";
    }

    // 登录
    @GetMapping("/login")
    public String login() {
        // 返回登录页面
        logger.info("get /login =>");
        return "login";
    }

    // 登录错误
    @GetMapping("/login-error")
    public String loginError(Model model) {
        logger.info("login /login-error =>");
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登录失败，用户名或者密码错误");
        return "login";
    }

    // 注册页面
    @GetMapping("/register")
    public String register(User user) {
        logger.info("get /register =>");
        return "register";
    }

    // 注册用户
    @PostMapping("/register")
    public String registerUser(User user) {
        logger.info("post /register => user<{}>", user);

        // 2L 代码用户角色
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(2L));
        user.setAuthorities(authorities);

        // 用户密码加密，用户发送上来的密码不是加密的，需要加密存储
        user.setEncodePassword(user.getPassword());

        // 保存用户信息
        userService.saveOrUpdateUser(user);
        // 跳转到登录页面
        return "redirect:/login";
    }

    // 跳转到搜索页面
    @GetMapping("/search")
    public String search() {
        logger.info("get /search =>");
        return "search";
    }

    @GetMapping("/about")
    public String about() {
        logger.info("get /about =>");
        return "about";
    }

    @GetMapping("/contribute")
    public String contribute() {
        logger.info("get /contribute =>");
        return "contribute";
    }

    @GetMapping("/disclaimers")
    public  String disclaimers() {
        logger.info("get /disclaimers");
        return "disclaimers";
    }
}
