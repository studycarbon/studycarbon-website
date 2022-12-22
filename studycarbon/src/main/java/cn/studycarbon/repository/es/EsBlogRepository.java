package cn.studycarbon.repository.es;

import cn.studycarbon.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

// 定义EsBlogRepository
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
    // 标题中包含关键字，摘要包含关键字，内容包含关键字
    // 分页查询博客带查询功能
    Page<EsBlog> findDistinctByTitleContainingOrSummaryContainingOrContentContaining(String title, String summary, String content, Pageable pageable);

    //
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String Summary, String content, String tags, Pageable pageable);

    //查找博客id
    EsBlog findByBlogId(Long blogId);
}
