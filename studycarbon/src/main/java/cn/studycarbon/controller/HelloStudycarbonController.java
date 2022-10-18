package cn.studycarbon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *  @brief:第一个controller,主要是测试和使用controller
*/

@RestController
@RequestMapping("/hellostudycarbon")
public class HelloStudycarbonController {
    public static Logger logger = LoggerFactory.getLogger(HelloStudycarbonController.class);
    @GetMapping
    public String helloStudycarbon() {
        logger.debug("called.");
        return "hello, studycarbon!";
    }
}
