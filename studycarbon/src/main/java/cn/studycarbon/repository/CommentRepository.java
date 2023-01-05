package cn.studycarbon.repository;

import cn.studycarbon.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findCommentByContentContaining(String content, Pageable pageable);
}
