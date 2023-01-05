package cn.studycarbon.service;

import cn.studycarbon.domain.Comment;
import cn.studycarbon.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Comment 服务接口.
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 1.0.0 2017年4月9日
 */

public interface CommentService {
    // 根据id获取 Comment
    Comment getCommentById(Long id);

    // 删除评论
    void removeComment(Long id);

    Page<Comment> listCommentsByContentContaining(String content, Pageable pageable);
}
