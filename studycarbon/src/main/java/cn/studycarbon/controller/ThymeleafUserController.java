package cn.studycarbon.controller;

// ThymeleafController：展示thymeleaf的用法

import cn.studycarbon.domain.ThymeleafUser;
import cn.studycarbon.repository.ThymeleafUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/thymeleafUsers")
public class ThymeleafUserController {

    @Autowired
    private ThymeleafUserRepository thymeleafRepo;

    // 直接映射 /thymeleafUsers
    @GetMapping
    public ModelAndView list(Model model) {
        model.addAttribute("userList", thymeleafRepo.findAll());
        model.addAttribute("title", "用户管理");
        return new ModelAndView("thymeleafUser/list", "userModel", model);
    }

    // /thymeleafUsers/{id}
    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", thymeleafRepo.findById(id).get());
        model.addAttribute("title", "查看用户");
        return new ModelAndView("thymeleafUser/view", "userModel", model);
    }

    // 获取创建表单页面
    // /thymeleafUsers/form
    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        ThymeleafUser user = new ThymeleafUser();
        model.addAttribute("user", user);
        model.addAttribute("title", "创建用户");
        return new ModelAndView("thymeleafUser/form", "userModel", model);
    }

    // 保存或者更新用户
    @PostMapping
    public ModelAndView saveOrUpdateUser(ThymeleafUser user) {
        thymeleafRepo.save(user);
        return new ModelAndView("redirect:/thymeleafUsers");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        thymeleafRepo.deleteById(id);
        return new ModelAndView("redirect:/thymeleafUsers");
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        ThymeleafUser user = thymeleafRepo.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("title", "修改用户");
        return new ModelAndView("thymeleafUser/form", "userModel", model);
    }
}
