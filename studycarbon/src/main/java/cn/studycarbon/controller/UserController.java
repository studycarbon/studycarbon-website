package cn.studycarbon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
    public static Logger logger = LoggerFactory.getLogger(UserController.class);
    /*
     * @brief:返回用户登录页面
    */
    @GetMapping("/loginview")
    public ModelAndView userLoginView() {
        logger.debug("get login view");
        return new ModelAndView("front/pages/login");
    }

    /*
     * @brief: 返回用户注册页面
    */
    @GetMapping("/regview")
    public ModelAndView userRegView() {
        logger.debug("get reg view");
        return new ModelAndView("front/pages/register");
    }

    /*
     * @brief: 用户登录
    */
    @PostMapping("/login")
    public ModelAndView userLogin() {
        logger.debug("do login...");
        return null;
    }

    /*
     * @brief: 用户注册
    */
    @PostMapping("/reg")
    public ModelAndView userReg() {
        return null;
    }

    /*
     * @brief: 用户退出登录
    */
    @GetMapping("/logout")
    public ModelAndView userLogout() {
        return  null;
    }

}
