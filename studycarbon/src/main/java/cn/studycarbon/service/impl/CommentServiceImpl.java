package cn.studycarbon.service.impl;

import javax.transaction.Transactional;

import cn.studycarbon.domain.Comment;
import cn.studycarbon.domain.User;
import cn.studycarbon.repository.CommentRepository;
import cn.studycarbon.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Comment 服务.
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 1.0.0 2017年4月9日
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public void removeComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateComment(Comment comment) {
        Comment orginComment = getCommentById(comment.getId());
        orginComment.setContent(comment.getContent());
        commentRepository.save(orginComment);
    }

    @Override
    public Page<Comment> listCommentsByContentContaining(String content, Pageable pageable) {
        return commentRepository.findCommentByContentContaining(content, pageable);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).get();
    }

}
