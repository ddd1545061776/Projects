package com.ddd.service;

import com.ddd.domain.Comment;

import java.util.List;

public interface CommentService {
    /**根据blogid查询改博客的评论信息
    * @param: blogId
    */
    List<Comment> listCommentByBlogId(Long blogId);
    /**保存评论
    * @param: comment
    */
    Comment saveComment(Comment comment);
}
