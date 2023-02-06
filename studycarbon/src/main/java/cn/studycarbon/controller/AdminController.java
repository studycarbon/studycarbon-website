package cn.studycarbon.controller;

import cn.studycarbon.vo.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {
    // 日志
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    // 判断该路径是否被拦截
    @GetMapping
    public ModelAndView adminView(Model model) {
        logger.info("get admin views =>");
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("用户管理", "/users"));
        list.add(new Menu("角色管理", "/roles"));
        list.add(new Menu("博客管理", "/blogs/all"));
        list.add(new Menu("评论管理", "/comments/all"));
        model.addAttribute("list", list);
        return new ModelAndView("admins/index", "model", model);
    }
}
