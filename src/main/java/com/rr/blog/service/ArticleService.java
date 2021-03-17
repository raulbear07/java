package com.rr.blog.service;

import com.github.pagehelper.PageInfo;
import com.rr.blog.entity.Article;
import com.rr.blog.enums.ArticleStatus;
import com.rr.blog.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface ArticleService {
    Integer countArticle(Integer status);
    Integer countArticleComment();
    Integer countArticleView();
    Integer countArticleByCategoryId(Integer categoryId);
    Integer countArticleByTagId(Integer tagId);
    List<Article> listArticle(HashMap<String,Object> criteria);
    List<Article> listRecentArticle(Integer limit);
    void updateArticleDetail(Article article);
    void deleteArticleBatch(List<Integer> ids);
    void deleteArticle(Integer id);
    PageInfo<Article> pageArticle(Integer pageIndex , Integer pageSize, HashMap<String,Object> criteria);
    Article getArticleByStatusAndId(Integer status, Integer id);
    List<Article> listArticleByViewCount(Integer limit);
    Article getAfterArticle(Integer id);
    Article getPreArticle(Integer id);
    List<Article> listRandomArticle(Integer limit);
    List<Article> listArticleByCommentCount(Integer limit);
    void insertArticle(Article article);
    void updateCommentCount(Integer articleId);
    Article getLastUpdateArticle();
    List<Article> listArticleByCategoryId(Integer cateId, Integer limit);
    List<Article> listArticleByCategoryIds(List<Integer> cateIds, Integer limit);
    List<Integer> listCategoryIdByArticleId(Integer articleId);
    List<Article> listAllNotWithContent();
}
