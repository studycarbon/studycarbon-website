package cn.studycarbon.controller;

import java.util.List;

import cn.studycarbon.domain.User;
import cn.studycarbon.domain.es.EsBlog;
import cn.studycarbon.service.EsBlogService;
import cn.studycarbon.service.UserService;
import cn.studycarbon.vo.TagVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 主页控制器.
@Controller
@RequestMapping("/blogs")
public class BlogController {

    // 日志
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private EsBlogService esBlogService;

    @Autowired
    UserService userService;

    // 列出所有博客数据
    @GetMapping
    public String listEsBlogs(
            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "async", required = false) boolean async,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model) {

        logger.debug("order:{},keyword:{},async:{},pageIndex{},pageSize{}", order, keyword, async, pageIndex, pageSize);

        Page<EsBlog> page = null;
        List<EsBlog> list = null;
        boolean isEmpty = true; // 系统初始化时，没有博客数据
        try {
            if (order.equals("hot")) { // 最热查询
                // Sort sort = new Sort(Sort.Direction.DESC,“id“)报错的解决
                // https://blog.csdn.net/weixin_44154163/article/details/109657052#:~:text=%E9%94%99%E8%AF%AF%E4%BB%A3%E7%A0%81%EF%BC%9A%20Sort%20sort%20%3D%20new%20Sort%20%28Sort.%20Direction.ASC%2C%22,%E6%9D%A5%E5%88%9B%E5%BB%BA%E4%BA%86%20%E8%A7%A3%E5%86%B3%20%E6%96%B9%E6%A1%88%EF%BC%9A%20Sort%20sort%20%3D%20Sort.by%20%28Sort.
                Sort sort = Sort.by(Direction.DESC, "readSize", "commentSize", "voteSize", "createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listHotestEsBlogs(keyword, pageable);
            } else if (order.equals("new")) { // 最新查询
                Sort sort = Sort.by(Direction.DESC, "createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listNewestEsBlogs(keyword, pageable);
            }
            isEmpty = false;
        } catch (Exception e) {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = esBlogService.listEsBlogs(pageable);
        }

        list = page.getContent();    // 当前所在页面数据列表

        // 更新用户头像等
        for (EsBlog esBlog : list) {
            String avatar = userService.getUserByUsername(esBlog.getUsername()).getAvatar();
            esBlog.setAvatar(avatar);
        }

        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        // 首次访问页面才加载 最新发布的文章 热门发布文章 热门用户 热门标签
        if (!async && !isEmpty) {
            List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
            model.addAttribute("newest", newest);
            List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
            model.addAttribute("hotest", hotest);
            List<TagVO> tags = esBlogService.listTop30Tags();
            model.addAttribute("tags", tags);
            List<User> users = esBlogService.listTop12Users();
            model.addAttribute("users", users);
        }

        return (async == true ? "/index :: #mainContainerRepleace" : "/index");
    }

    // 最新的5篇文章
    @GetMapping("/newest")
    public String listNewestEsBlogs(Model model) {
        List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
        model.addAttribute("newest", newest);
        return "newest";
    }

    // 最热的5篇文章
    @GetMapping("/hotest")
    public String listHotestEsBlogs(Model model) {
        List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
        model.addAttribute("hotest", hotest);
        return "hotest";
    }

}
