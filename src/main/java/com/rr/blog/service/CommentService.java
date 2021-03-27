package com.rr.blog.service;

import com.github.pagehelper.PageInfo;
import com.rr.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment getCommentById(Integer id);
    List<Comment> listCommentByArticleId(Integer articleId);
    void insertComment(Comment comment);
    void updateComment(Comment comment);
    void deleteComment(Integer commentId);
    PageInfo<Comment> listCommentPage(Integer pageIndex,Integer pageSize);
    List<Comment> listComment();
    Integer commentCount();
    List<Comment> listRecentComment(Integer limit);
    List<Comment> listChildComment(Integer id);

}
