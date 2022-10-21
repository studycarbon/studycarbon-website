package cn.studycarbon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class HomePageController {
    public static Logger logger = LoggerFactory.getLogger(HomePageController.class);
    // 获取网站主页
    @GetMapping
    public ModelAndView homePage() {
        logger.debug("called.");
        return new ModelAndView("front/pages/index");
    }
}
