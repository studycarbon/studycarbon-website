package cn.studycarbon.controller;

import cn.studycarbon.domain.User;
import cn.studycarbon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController {

    // 日志对象
    public static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

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
    public User userLogin(HttpServletRequest request, User user) {
        logger.debug("userLogin...");
        logger.debug("request:"+request+" user:"+user);

        // 判断用户名是否存在
        User loginUser = userService.userLogin(user);

        // 判断登录用户是否为空
        if (loginUser == null) {
            return null;
        }

        // 通过session记录用户已经登录
        request.getSession().setAttribute("user",user.getId());

        return loginUser;
    }

    /*
     * @brief: 用户注册
    */
    @PostMapping("/reg")
    public User userReg(User user) {
        logger.debug("user login...");
        logger.debug("user:"+user);

        // 随机生成10位数字组成字符串
        String rolename = "";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int randNum = random.nextInt(10);
            rolename += randNum;
        }

        // 设置用户名
        user.setRolename(rolename);
        user.setSex("nan");
        user.setLoginstate(0);
        user.setLogintime(0);
        user.setCreatetime(System.currentTimeMillis());

        userService.userReg(user);
        return user;
    }

    /*
     * @brief: 用户退出登录
    */
    @GetMapping("/logout")
    public ModelAndView userLogout() {
        return  null;
    }

}
