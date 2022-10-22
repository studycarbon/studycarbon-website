package cn.studycarbon.controller;

import cn.studycarbon.domain.StudyCarbonInfo;
import cn.studycarbon.service.StudyCarbonInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/*
 * @brief: 从数据库中查询studycarbon网站的信息，发送给请求方
 * @details:
 *      执行流程：request -> controller -> service -> dao
 *      包括信息：免责声明，贡献者信息
*/

@RestController
@RequestMapping("/info")
public class StudyCarbonInfoController {

    public static Logger logger = LoggerFactory.getLogger(StudyCarbonInfoController.class);

    @Autowired
    private StudyCarbonInfoService studyCarbonInfoService;

    @GetMapping
    public StudyCarbonInfo studyCarbonInfo() {
        logger.debug("studyCarbonInfo called.");
        StudyCarbonInfo info =  studyCarbonInfoService.getInfo();
        logger.info("studycarbon info:"+info);
        return info;
    }

    @GetMapping("/aboutview")
    public ModelAndView aboutView() {
        logger.debug("info about...");
        return new ModelAndView("front/pages/about");
    }

    @GetMapping("/disclaimerveiw")
    public ModelAndView disclaimerView() {
        logger.debug("info disclaimer...");
        return new ModelAndView("front/pages/disclaimer");
    }

    @GetMapping("/contributionview")
    public ModelAndView contributionView() {
        logger.debug("info contribution...");
        return new ModelAndView("front/pages/contribution");
    }

}
