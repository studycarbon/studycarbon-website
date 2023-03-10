package cn.studycarbon.service.impl;

import javax.transaction.Transactional;

import cn.studycarbon.domain.*;
import cn.studycarbon.domain.es.EsBlog;
import cn.studycarbon.repository.BlogRepository;
import cn.studycarbon.service.BlogService;
import cn.studycarbon.service.EsBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private EsBlogService esBlogService;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        // 判断是否是新创建的博客
        boolean isNew = (blog.getId() == null);

        EsBlog esBlog = null;
        Blog returnBlog = blogRepository.save(blog);

        if (isNew) {
            // 是新创建的博客，根据blog创建esBlog
            esBlog = new EsBlog(returnBlog);
        } else {
            // 不是新创建的博客，获取到esBlog对象 并更新esBlog
            esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
            esBlog.update(returnBlog);
        }

        // 更新esBlog对象
        esBlogService.updateEsBlog(esBlog);
        return returnBlog;
    }

    @Transactional
    @Override
    public void removeBlog(Long id) {
        // EsBlog esblog = esBlogService.getEsBlogByBlogId(id);
        // esBlogService.removeEsBlog(esblog.getId());

        blogRepository.deleteById(id);
    }

    @Override
    public Blog getBlogById(Long id) {
        // 不应该直接操作repo, 应该通过serviece调用repo
        return blogRepository.findById(id).get();
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        String tags = title;
        Page<Blog> blogs = blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title, user, tags, user, pageable);
        return blogs;
    }

    @Override
    public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
        return blogs;
    }

    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        Page<Blog> blogs = blogRepository.findByCatalog(catalog, pageable);
        return blogs;
    }

    @Override
    public void readingIncrease(Long id) {
        Blog blog = blogRepository.findById(id).get();
        blog.setReadSize(blog.getReadSize() + 1);
        System.out.println("readingIncrease:id:"+id+"readsize:"+blog.getReadSize());
        this.saveBlog(blog);
    }

    @Override
    public Blog createComment(Long blogId, String commentContent) {
        Blog originalBlog = blogRepository.findById(blogId).get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment(user, commentContent, originalBlog);
        originalBlog.addComment(comment);
        return this.saveBlog(originalBlog);
    }

    @Override
    public void removeComment(Long blogId, Long commentId) {
        Blog originalBlog = blogRepository.findById(blogId).get();
        originalBlog.removeComment(commentId);
        this.saveBlog(originalBlog);
    }

    @Override
    public Blog createVote(Long blogId) {
        // 获取到原始的blog
        Blog originalBlog = blogRepository.findById(blogId).get();
        if (originalBlog == null) {
            throw new IllegalArgumentException("无法查询到博客,博客id="+blogId);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new IllegalArgumentException("无法点赞, 用户未登录");
        }

        Vote vote = new Vote(user);

        boolean isExist = originalBlog.addVote(vote);
        if (isExist) {
            throw new IllegalArgumentException("该用户已经点过赞了");
        }
        return this.saveBlog(originalBlog);
    }

    @Override
    public void removeVote(Long blogId, Long voteId) {
        Blog originalBlog = blogRepository.findById(blogId).get();
        originalBlog.removeVote(voteId);
        this.saveBlog(originalBlog);
    }
}
