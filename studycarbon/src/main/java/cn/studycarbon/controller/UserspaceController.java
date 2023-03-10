package cn.studycarbon.controller;

import cn.studycarbon.domain.*;
import cn.studycarbon.service.BlogService;
import cn.studycarbon.service.CatalogService;
import cn.studycarbon.service.UserService;
import cn.studycarbon.util.ConstraintViolationExceptionHandler;
import cn.studycarbon.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Controller
@RequestMapping("/u")
public class UserspaceController {

    private static Logger logger = LoggerFactory.getLogger(UserspaceController.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private BlogService blogService;

    // 访问个人主页
    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username, Model model) {
        logger.debug("get person blog page => ");
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "redirect:/u/" + username + "/blogs";
    }

    // 访问个人设置
    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        logger.info("get userspace page");
        User user = (User) userDetailsService.loadUserByUsername(username);
        logger.info("[profile] user:" + user);
        model.addAttribute("user", user);
        return new ModelAndView("userspace/profile", "userModel", model);
    }

    // 保存个人设置, 保存完毕后，重新定向到个人设置页面
    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) {
        logger.info("save user profile:username = " + username);
        User originalUser = userService.getUserById(user.getId());
        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());

        // 判断密码是否变更
        String rawPassword = originalUser.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(user.getPassword());
        boolean isMatch = encoder.matches(rawPassword, encodePasswd);
        if (!isMatch) {
            originalUser.setEncodePassword(user.getPassword());
        }
        userService.saveOrUpdateUser(originalUser);
        return "redirect:/u/" + username + "/profile";
    }

    // 访问用户头像
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("userspace/avatar", "userModel", model);
    }

    // 修改用户头像
    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();
        User originalUser = userService.getUserById(user.getId());
        originalUser.setAvatar(avatarUrl);
        userService.saveOrUpdateUser(originalUser);
        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }


    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "catalog", required = false) Long catalogId,
                                   @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                   @RequestParam(value = "async", required = false) boolean async,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   Model model) {

        logger.info("get personal blogs => pesonal name:{}, order:{}, catalogId:{}, keyword:{}, pageIndex:{}, pageSize{}",
                username, order, catalogId, keyword, pageIndex, pageSize);

        User user = (User) userDetailsService.loadUserByUsername(username);
        Page<Blog> page = null;
        if (catalogId != null && catalogId > 0) { // 分类查询
            Catalog catalog = catalogService.getCatalogById(catalogId);
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = blogService.listBlogsByCatalog(catalog, pageable);
            order = "";
        } else if (order.equals("hot")) { // 最热查询
            Sort sort = Sort.by(Sort.Direction.DESC, "readSize", "commentSize", "voteSize");
            Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
            page = blogService.listBlogsByTitleVoteAndSort(user, keyword, pageable);
        } else if (order.equals("new")) { // 最新查询
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = blogService.listBlogsByTitleVote(user, keyword, pageable);
        }

        //logger.info("get personal blogs list:" + page.getContent());
        List<Blog> logBlogLists = page.getContent();
        for (Blog logBlog : logBlogLists) {
            logger.info("get personal blog:" + logBlog);
        }


        // 当前所在页面数据列表
        List<Blog> list = page.getContent();
        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("catalogId", catalogId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);
        return (async == true ? "userspace/u::#mainContainerRepleace" : "userspace/u");
    }

    /**
     * 获取博客展示界面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs/{id}")
    public String getBlogById(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        // 获取博客内容
        logger.info("get blog username:" + username + " blog id:" + id);
        User principal = null;
        boolean isBlogOwner = false;
        Blog blog = blogService.getBlogById(id);
        List<Vote> votes = null;
        List<Comment> comments = null;
        Vote currentVote = null; // 当前用户的点赞情况

        // 博客不为空的话
        if (blog != null) {
            // 每次读取，简单的可以认为阅读量增加1次
            blogService.readingIncrease(id);
            // 判断操作用户是否是博客的所有者
            if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                    && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
                principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal != null && username.equals(principal.getUsername())) {
                    isBlogOwner = true;
                }
            }

            // 判断操作用户的点赞情况
            votes = blog.getVotes();
            if (principal != null) {
                for (Vote vote : votes) {
                    if (vote.getUser().getUsername().equals(principal.getUsername())) {
                        currentVote = vote;
                        break;
                    }
                }
            }

            // 拦截不能被展示的评论
            comments = blog.getComments();
            logger.info("blog get comments:"+comments);

            for (int i = 0; i < comments.size(); i++) {
                if (comments.get(i).getDisplay() == false) {
                    comments.remove(i--);
                }
            }
            blog.setComments(comments);
        }

        // 获取博客页面时候，如果是不允许显示的博客，需要审核才能显示
        if (blog.getDisplay() == false) {
            blog.setHtmlContent("<p>此博客暂未经过管理员审核，暂不能显示，请等待审核...</p>\n");
        }

        model.addAttribute("isBlogOwner", isBlogOwner);
        model.addAttribute("blogModel", blog);
        model.addAttribute("currentVote", currentVote);

        return "userspace/blog";
    }


    // 删除博客
    @DeleteMapping("/{username}/blogs/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
        logger.info("delete /{}/blogs/{} =>", username, id);
        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        String redirectUrl = "/u/" + username + "/blogs";
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }


    // 获取新增博客的界面
    @GetMapping("/{username}/blogs/edit")
    public ModelAndView createBlog(@PathVariable("username") String username, Model model) {
        logger.info("get new blog edit page.");
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);
        model.addAttribute("blog", new Blog(null, null, null));
        model.addAttribute("catalogs", catalogs);
        return new ModelAndView("userspace/blogedit", "blogModel", model);
    }

    // 获取编辑博客的界面
    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        logger.info("get update blog edit page.");
        // 获取用户分类列表
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);
        model.addAttribute("blog", blogService.getBlogById(id));
        model.addAttribute("catalogs", catalogs);
        return new ModelAndView("userspace/blogedit", "blogModel", model);
    }

    // 保存博客
    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        logger.info("save blog => ");

        // 对 Catalog 进行空处理
        if (blog.getCatalog().getId() == null) {
            logger.info("catalog id is null");
            return ResponseEntity.ok().body(new Response(false, "未选择分类"));
        }

        try {

            // 判断是修改还是新增
            if (blog.getId() != null) {
                logger.info("blog id is not null, update blog.");
                Blog orignalBlog = blogService.getBlogById(blog.getId());
                orignalBlog.setTitle(blog.getTitle());
                orignalBlog.setContent(blog.getContent());
                orignalBlog.setHtmlContent(blog.getHtmlContent());
                orignalBlog.setSummary(blog.getSummary());
                orignalBlog.setCatalog(blog.getCatalog());
                orignalBlog.setTags(blog.getTags());
                orignalBlog.setDisplay(false);
                blogService.saveBlog(orignalBlog);
            } else {
                logger.info("blog id is null, create blog.");
                User user = (User) userDetailsService.loadUserByUsername(username);
                blog.setUser(user);
                blog.setDisplay(false);
                blogService.saveBlog(blog);
            }

        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }

    @GetMapping("/{username}/blogs/display/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String display(@PathVariable("username") String username, @PathVariable("id") Long id) {
        logger.info("get /{}/blogs/display/{} =>" , username, id);
        Blog blog = blogService.getBlogById(id);
        blog.setDisplay(true);
        blogService.saveBlog(blog);
        return "redirect:/admins";
    }

    @GetMapping("/{username}/blogs/notDisplay/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String notDisplay(@PathVariable("username") String username, @PathVariable("id") Long id) {
        Blog blog = blogService.getBlogById(id);
        blog.setDisplay(false);
        blogService.saveBlog(blog);
        return "redirect:/admins";
    }
}
