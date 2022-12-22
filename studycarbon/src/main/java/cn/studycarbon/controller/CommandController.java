package cn.studycarbon.controller;

import cn.studycarbon.domain.Blog;
import cn.studycarbon.domain.es.EsBlog;
import cn.studycarbon.service.BlogService;
import cn.studycarbon.service.EsBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/command")
@Controller
public class CommandController {

    @Autowired
    BlogService blogService;

    @Autowired
    EsBlogService esBlogService;

    @RequestMapping("/updateEsBlog")
    @ResponseBody
    String updateEsBlog() {
        List<Blog> blogs = blogService.getAllBlogs();
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
