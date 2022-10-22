package cn.studycarbon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("publisharticleview")
    ModelAndView publishArticleView() {
        return new ModelAndView("front/pages/publish-page");
    }

    @GetMapping("/articlepageview")
    ModelAndView articlePageView() {
        return new ModelAndView("front/pages/articlepage");
    }
}
