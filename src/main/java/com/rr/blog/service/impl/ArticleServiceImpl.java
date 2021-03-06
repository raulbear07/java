package com.rr.blog.service.impl;

import com.github.pagehelper.PageInfo;
import com.rr.blog.entity.*;
import com.rr.blog.mapper.ArticleCategoryRefMapper;
import com.rr.blog.mapper.ArticleMapper;
import com.rr.blog.mapper.ArticleTagRefMapper;
import com.rr.blog.mapper.TagMapper;
import com.rr.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleCategoryRefMapper articleCategoryRefMapper;
    @Autowired
    private ArticleTagRefMapper articleTagRefMapper;
    @Autowired
    private ArticleCategoryRef articleCategoryRef;

    @Override
    public Integer countArticle(Integer status) {
        Integer count =0;
        try{
            articleMapper.countArticle(status);
        }
        catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());

        }
        return count;
    }

    @Override
    public Integer countArticleComment() {
        Integer count =0;
        try{
            articleMapper.countArticleComment();
        }
        catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());

        }
        return count;
    }

    @Override
    public Integer countArticleView() {
        Integer count =0;
        try{
            articleMapper.countArticleView();
        }
        catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());

        }
        return count;
    }

    @Override
    public Integer countArticleByCategoryId(Integer categoryId) {
        Integer count = 0;
        try {
            count = articleCategoryRefMapper.countArticleByCategoryId(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据分类统计文章数量失败, categoryId:{}, cause:{}", categoryId, e);
        }
        return count;
    }

    @Override
    public Integer countArticleByTagId(Integer tagId) {
        return articleTagRefMapper.countArticleByTagId(tagId);
    }

    @Override
    public List<Article> listArticle(HashMap<String, Object> criteria) {
        return articleMapper.findAll(criteria);
    }

    @Override
    public List<Article> listRecentArticle(Integer limit) {
        return articleMapper.listArticleByLimit(limit);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateArticleDetail(Article article) {
        if(article==null || article.getArticleId()==null ||article.getArticleId().intValue()<=0)
            return ;
        article.setArticleUpdateTime(new Date());
        articleMapper.update(article);
        if(article.getTagList()!=null && article.getTagList().size()>0){
            List<Tag> list=articleTagRefMapper.listTagByArticleId(article.getArticleId());
            for (Tag tag:article.getTagList()) {
                articleTagRefMapper.insert(new ArticleTagRef(article.getArticleId(),tag.getTagId()));
            }
        }
        if(article.getCategoryList()!=null && article.getCategoryList().size()>0){
            articleCategoryRefMapper.deleteByArticleId(article.getArticleId());
            for (Category category:article.getCategoryList()) {
                articleCategoryRefMapper.insert(new ArticleCategoryRef(article.getArticleId(),category.getCategoryId()));
            }
        }
    }

    @Override
    public void deleteArticleBatch(List<Integer> ids) {

    }

    @Override
    public void deleteArticle(Integer id) {

    }

    @Override
    public PageInfo pageArticle(Integer pageIndex, Integer pageSize, HashMap<String, Object> criteria) {
        return null;
    }

    @Override
    public Article getArticleByStatusAndId(Integer status, Integer id) {
        return null;
    }

    @Override
    public List<Article> listArticleByViewCount(Integer limit) {
        return null;
    }

    @Override
    public Article getAfterArticle(Integer id) {
        return null;
    }

    @Override
    public Article getPreArticle(Integer id) {
        return null;
    }

    @Override
    public List<Article> listRandomArticle(Integer limit) {
        return null;
    }

    @Override
    public List<Article> listArticleByCommentCount(Integer limit) {
        return null;
    }

    @Override
    public Integer insertArticle(Article article) {
        return null;
    }

    @Override
    public void updateCommentCount(Article articleId) {

    }

    @Override
    public Article getLastUpdateArticle() {
        return null;
    }

    @Override
    public List<Article> listArticleByCategoryId(Integer cateId, Integer limit) {
        return null;
    }

    @Override
    public List<Article> listArticleByCategoryIds(List<Integer> cateIds, Integer limit) {
        return null;
    }

    @Override
    public List<Integer> listCategoryIdByArticleId(Integer articleId) {
        return null;
    }

    @Override
    public List<Article> listAllNotWithContent() {
        return null;
    }
}
