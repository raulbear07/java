package com.rr.blog.mapper;

import com.rr.blog.entity.Article;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ArticleMapper {
    Integer deleteById(Integer articleId);
    Integer insert(Article article);
    Integer update(Article article);
    List<Article> findAll(HashMap<String ,Object> criteria);
    List<Article> listAllNoteWithContent();
    Integer countArticle(@Param(value = "status")Integer status);
    Integer countArticleComment();
    Integer countArticleView();
    Article getArticleByStatusAndId(@Param(value = "status")Integer status,@Param(value = "id") Integer id);
    List<Article> pageArticle(@Param(value = "status")Integer status,@Param(value = "pageIndex")Integer pageIndex,
                              @Param(value = "pageSize") Integer pageSize);
    List<Article> limitArticle(@Param(value = "limit") Integer limit);
    Article getAfterArticle(@Param(value = "id") Integer id);
    Article getPreArticle(@Param(value = "id") Integer id);
    List<Article> getRandomArticle(@Param(value = "limit")Integer limit);
    List<Article> getArticleByCommentCount(@Param(value = "limit")Integer limit);
    void updateCommentCount(@Param(value = "articleId")Integer articleId);
    Article getLastUpdateArticle();
    Integer countArticleByUser(@Param(value = "id") Integer id);
    List<Article> findArticleByCategoryId(@Param(value = "categoryId")Integer categoryId,@Param(value = "limit")Integer limit);
    List<Article> findArticleByCategoryIds(@Param(value = "categoryIds")List<Integer> categoryIds,@Param(value = "limit")Integer limit);
    List<Article> listArticleByLimit(@Param(value = "limit")Integer limit);
    Integer deleteBatch(@Param(value = "ids") List<Integer> ids);


}

