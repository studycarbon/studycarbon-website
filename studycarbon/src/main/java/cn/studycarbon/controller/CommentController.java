package cn.studycarbon.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import cn.studycarbon.Application;
import cn.studycarbon.domain.Authority;
import cn.studycarbon.domain.Blog;
import cn.studycarbon.domain.Comment;
import cn.studycarbon.domain.User;
import cn.studycarbon.service.BlogService;
import cn.studycarbon.service.CommentService;
import cn.studycarbon.util.ConstraintViolationExceptionHandler;
import cn.studycarbon.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


// 评论控制器
@Controller
@RequestMapping("/comments")
public class CommentController {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;


    /**
     * 获取评论列表
     */
    @GetMapping
    public String listComments(@RequestParam(value = "blogId", required = true) Long blogId, Model model) {
        Blog blog = blogService.getBlogById(blogId);
        List<Comment> comments = blog.getComments();

        // 判断操作用户是否是评论的所有者
        String commentOwner = "";
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                commentOwner = principal.getUsername();
            }
        }

        model.addAttribute("commentOwner", commentOwner);
        model.addAttribute("comments", comments);
        return "userspace/blog :: #mainContainerRepleace";
    }

    /*
     * 获取评论列表
     *
    */
    @RequestMapping("/all")
    public String listComments(@RequestParam(value = "content", required = false, defaultValue = "") String content,
                               @RequestParam(value = "async", required = false) boolean async,
                               @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                               Model model)
    {
        logger.info("get comments => content:<{}>, async:<{}>, pageIndex:<{}>, pagSize:<{}>", content, async, pageIndex, pageSize);
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Comment> page = commentService.listCommentsByContentContaining(content, pageable);
        List<Comment> commentList = page.getContent();
        model.addAttribute("page", page);
        model.addAttribute("commentList", commentList);
        if (async) {
            return "comments/list::#mainContainerRepleace";
        }
        return "comments/list";
    }

    /**
     * 发表评论
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<Response> createComment(Long blogId, String commentContent) {
        logger.info("create comment blogId:" + blogId + " commentContent:" + commentContent);
        try {
            blogService.createComment(blogId, commentContent);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Long blogId) {

        boolean isOwner = false;
        boolean isAdmin = false;
        Comment comment = commentService.getCommentById(id);
        User user = comment.getUser();
        // 判断操作用户是否是评论的所有者
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {

            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }

            List<Authority> authorities = principal.getAuthorities();
            for (Authority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                }
            }

        }

        if (!isOwner && !isAdmin) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }

        try {
            blogService.removeComment(comment.getBlog().getId(), id);
            commentService.removeComment(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }
}
