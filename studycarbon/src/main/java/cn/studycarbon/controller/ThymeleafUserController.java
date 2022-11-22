package cn.studycarbon.controller;

// ThymeleafController：展示thymeleaf的用法

import cn.studycarbon.dao.ThymeleafUserDao;
import cn.studycarbon.domain.ThymeleafUser;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/thymeleafUsers")
public class ThymeleafUserController {

    @Autowired
    private ThymeleafUserDao thymeleafUserDao;

    // 直接映射 /thymeleafUsers
    @GetMapping
    public ModelAndView list(Model model) {
        //System.out.println(thymeleafUserDao.listUser());
        model.addAttribute("userList", thymeleafUserDao.listUser());
        model.addAttribute("title","用户管理");
        return new ModelAndView("thymeleafUser/list","userModel", model);
    }

    // /thymeleafUsers/{id}
    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", thymeleafUserDao.getUserById(id));
        model.addAttribute("title","查看用户");
        return new ModelAndView("thymeleafUser/view","userModel", model);
    }

    // 获取创建表单页面
    // /thymeleafUsers/form
    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        ThymeleafUser user = new ThymeleafUser();
        model.addAttribute("user", user);
        model.addAttribute("title","创建用户");
        return new ModelAndView("thymeleafUser/form","userModel", model);
    }

    // 保存或者更新用户
    @PostMapping
    public ModelAndView saveOrUpdateUser(ThymeleafUser user) {
        thymeleafUserDao.saveOrUpdateUser(user);
        return new ModelAndView("redirect:/thymeleafUsers");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        thymeleafUserDao.deleteUser(id);
        return new ModelAndView("redirect:/thymeleafUsers");
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        ThymeleafUser user = thymeleafUserDao.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("title","修改用户");
        return new ModelAndView("thymeleafUser/form","userModel", model);
    }
}
