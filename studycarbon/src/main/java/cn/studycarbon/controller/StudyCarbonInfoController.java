package cn.studycarbon.controller;

import cn.studycarbon.domain.StudyCarbonInfo;
import cn.studycarbon.service.StudyCarbonInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @brief: 从数据库中查询studycarbon网站的信息，发送给请求方
 * @details:
 *      执行流程：request -> controller -> service -> dao
*/

@RestController
@RequestMapping("/studycarboninfo")
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

}
