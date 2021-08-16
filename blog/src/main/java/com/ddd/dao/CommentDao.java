package com.ddd.dao;

import com.ddd.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Long> {
    List<Comment> findByBlogIdAndAndParentCommentNull(Long blogId, Sort sort);
}
