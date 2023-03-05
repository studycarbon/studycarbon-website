package cn.studycarbon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksController {
    // 日志
    private static Logger logger = LoggerFactory.getLogger(BlogController.class);

    public BooksController() {
    }

    @GetMapping()
    String index() {
        logger.info("get /books =>");
        return "books/index";
    }
}
