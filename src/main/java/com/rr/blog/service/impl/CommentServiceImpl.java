package com.rr.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rr.blog.entity.Article;
import com.rr.blog.entity.Comment;
import com.rr.blog.enums.ArticleStatus;
import com.rr.blog.mapper.ArticleMapper;
import com.rr.blog.mapper.CommentMapper;
import com.rr.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private  ArticleMapper articleMapper;
    @Override
    public Comment getCommentById(Integer id) {
        return  commentMapper.getCommentById(id);
    }

    @Override
    public List<Comment> listCommentByArticleId(Integer articleId) {

        return commentMapper.listCommentByArticleId(articleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertComment(Comment comment) {
        try {
            comment.setCommentCreateTime(new Date());
            commentMapper.insert(comment);
            articleMapper.updateCommentCount(comment.getCommentArticleId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建评论失败：comment:{}, cause:{}", comment, e);
        }
    }

    @Override
    public void updateCommnet(Comment comment) {
        try {
            commentMapper.update(comment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建更新失败：comment:{}, cause:{}", comment, e);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCommnet(Integer commentId) {
        commentMapper.deleteById(commentId);
        articleMapper.updateCommentCount(commentId);
    }

    @Override
    public PageInfo<Comment> listCommentPage(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex,pageSize);
        List<Comment> list =null;
        try{
            list =commentMapper.listComment();
            for (Comment comment:list
                 ) {
                Article article =articleMapper.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(),comment.getCommentArticleId());
                comment.setArticle(article);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("分页获得评论失败,pageIndex:{}, pageSize:{}, cause:{}", pageIndex, pageSize, e);
        }
        return new PageInfo<>(list);
    }

    @Override
    public List<Comment> listComment() {
        List<Comment> commentList = null;
        try {
            commentList = commentMapper.listComment();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得评论列表失败：cause:{}", e);
        }
        return commentList;
    }

    @Override
    public Integer commentCount() {
        return commentMapper.countComment();
    }

    @Override
    public List<Comment> listRecentComment(Integer limit) {
        return commentMapper.listRecentComment(limit);
    }

    @Override
    public List<Comment> listChildComment(Integer id) {
        return commentMapper.listCommentByArticleId(id);
    }
}
