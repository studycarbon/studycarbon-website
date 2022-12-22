package cn.studycarbon.service;


import java.util.List;

import cn.studycarbon.domain.User;
import cn.studycarbon.domain.es.EsBlog;
import cn.studycarbon.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Blog 服务接口.
public interface EsBlogService {

    // 删除Blog
    void removeEsBlog(String id);

    // 更新 EsBlog
    EsBlog updateEsBlog(EsBlog esBlog);

    // 根据id获取Blog
    EsBlog getEsBlogByBlogId(Long blogId);

    // 最新博客列表，分页
    Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable);

    // 最热博客列表，分页
    Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable);

    // 博客列表，分页
    Page<EsBlog> listEsBlogs(Pageable pageable);

    // 最新前5
    List<EsBlog> listTop5NewestEsBlogs();

    // 最热前5
    List<EsBlog> listTop5HotestEsBlogs();

    // 最热前 30 标签
    List<TagVO> listTop30Tags();

    // 最热前12用户
    List<User> listTop12Users();
}
