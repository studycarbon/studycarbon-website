package cn.studycarbon.controller;

import cn.studycarbon.domain.Blog;
import cn.studycarbon.domain.es.EsBlog;
import cn.studycarbon.service.BlogService;
import cn.studycarbon.service.EsBlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/command")
@Controller
public class CommandController {

    // 日志
    private static Logger logger = LoggerFactory.getLogger(CommandController.class);

    @Autowired
    BlogService blogService;

    @Autowired
    EsBlogService esBlogService;

    @RequestMapping("/updateEsBlog") // 设置映射路径
    @ResponseBody // 返回json结构体
    String updateEsBlog() {
        // 获取所有博客
        List<Blog> blogs = blogService.getAllBlogs();
        // 将blogs更新至esBlog
        for (Blog blog : blogs) {
            EsBlog esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
            if (esBlog == null) {
                esBlog = new EsBlog(blog);
                esBlogService.updateEsBlog(esBlog);
            }
        }
        return "update es blog finished!";
    }
}
