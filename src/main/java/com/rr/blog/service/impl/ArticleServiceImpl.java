package com.rr.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rr.blog.entity.*;
import com.rr.blog.enums.ArticleCommentStatus;
import com.rr.blog.mapper.*;
import com.rr.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        articleMapper.deleteBatch(ids);    }

    @Override
    public void deleteArticle(Integer id) {
        articleMapper.deleteById(id);
        articleCategoryRefMapper.deleteByArticleId(id);
    }

    @Override
    public PageInfo<Article>  pageArticle(Integer pageIndex, Integer pageSize, HashMap<String, Object> criteria) {
        PageHelper.startPage(pageIndex,pageIndex);
        List<Article> list =articleMapper.findAll(criteria);
        for (Article item:list
             ) {
            List<Category> categoryList = articleCategoryRefMapper.listCategoryByArticleId(item.getArticleId());
            if(categoryList==null || categoryList.size()==0){
                categoryList =new ArrayList<>();
                categoryList.add(Category.Default());
            }
            item.setCategoryList(categoryList);
        }
        return new PageInfo<>(list);
    }

    @Override
    public Article getArticleByStatusAndId(Integer status, Integer id) {
        return articleMapper.getArticleByStatusAndId(status,id);
    }

    @Override
    public List<Article> listArticleByViewCount(Integer limit) {
        return articleMapper.listArticleByCommentCount(limit);
    }

    @Override
    public Article getAfterArticle(Integer id) {
        return articleMapper.getAfterArticle(id);
    }

    @Override
    public Article getPreArticle(Integer id) {
        return articleMapper.getPreArticle(id);
    }

    @Override
    public List<Article> listRandomArticle(Integer limit) {
        return articleMapper.listRandomArticle(limit);
    }

    @Override
    public List<Article> listArticleByCommentCount(Integer limit) {
        return articleMapper.listArticleByCommentCount(limit);
    }

    @Override
    @Transactional( rollbackFor = Exception.class)
    public void insertArticle(Article article) {
        article.setArticleCreateTime(new Date());
        article.setArticleUpdateTime(new Date());
        article.setArticleIsComment(ArticleCommentStatus.ALLOW.getValue());
        article.setArticleViewCount(0);
        article.setArticleCommentCount(0);
        article.setArticleLikeCount(0);
        article.setArticleOrder(1);
        articleMapper.insert(article);
        if (article.getCategoryList() != null && article.getCategoryList().size() > 0) {
            for (Category category : article.getCategoryList()
            ) {
                articleCategoryRefMapper.insert(new ArticleCategoryRef(article.getArticleId(), category.getCategoryId()));
            }
        }
        if(article.getTagList()!=null && article.getTagList().size()>0) {
            for (Tag tag:article.getTagList()
                 ) {
                articleTagRefMapper.insert(new ArticleTagRef(article.getArticleId(),tag.getTagId()));
            }
        }
    }

    @Override
    public void updateCommentCount(Integer articleId) {
        articleMapper.updateCommentCount(articleId);

    }

    @Override
    public Article getLastUpdateArticle() {
        return articleMapper.getLastUpdateArticle();
    }

    @Override
    public List<Article> listArticleByCategoryId(Integer cateId, Integer limit) {
        if(cateId==null)
            return null;
        return articleMapper.findArticleByCategoryId(cateId,limit);
    }

    @Override
    public List<Article> listArticleByCategoryIds(List<Integer> cateIds, Integer limit) {
        if(cateIds==null ||cateIds.size()==0)
            return null;
        return articleMapper.findArticleByCategoryIds(cateIds,limit);
    }

    @Override
    public List<Integer> listCategoryIdByArticleId(Integer articleId) {
        if(articleId==null)
            return null;
        return articleCategoryRefMapper.selectCategoryIdByArticleId(articleId);
    }

    @Override
    public List<Article> listAllNotWithContent() {
        return articleMapper.listAllNoteWithContent();
    }
}
