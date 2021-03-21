package com.rr.blog.controller.home;

import com.alibaba.fastjson.JSON;
import com.rr.blog.entity.*;
import com.rr.blog.enums.ArticleStatus;
import com.rr.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArticleController {
    private static  Integer LIMIT_LOW=5;
    private static  Integer LIMIT_MiD=5;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;
    @RequestMapping("article/{articleId}")
    public String getArticleDetailPage(@PathVariable("articleId") Integer articleId, Model model){

        Article article =articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(),articleId);
        if(article==null)
            return "error/404";
        User user = userService.getUserById(article.getArticleUserId());
        article.setUser(user);
        model.addAttribute("article",article);
        List<Comment> commentList =commentService.listCommentByArticleId(articleId);
        model.addAttribute("comment",commentList);

        List<Integer> categoryIds= articleService.listCategoryIdByArticleId(articleId);
        List<Article> similarArticleList =articleService.listArticleByCategoryIds(categoryIds,5);
        model.addAttribute("similarArticleList",similarArticleList);

        List<Article> mostViewArticleList=articleService.listArticleByViewCount(LIMIT_LOW);
        model.addAttribute("mostViewArticleList",mostViewArticleList);

        Article preArticle=articleService.getPreArticle(articleId);
        model.addAttribute("preArticle",preArticle);

        Article afterArticle=articleService.getAfterArticle(articleId);
        model.addAttribute("afterArticle",afterArticle);

        List<Tag> allTagList=tagService.listTag();
        model.addAttribute("allTagList",allTagList);

        List<Article> randomArticleList= articleService.listRandomArticle(LIMIT_MiD);
        model.addAttribute("randomArticleList",randomArticleList);

        List<Article> mostCommentArticleList =articleService.listArticleByViewCount(LIMIT_MiD);
        model.addAttribute("mostCommentArticleList",mostCommentArticleList);

        return "home/page/articleDetail";
    }
    @ResponseBody
    @RequestMapping("article/like/{id}")
    public String increaseLikeCount(@PathVariable("id") Integer id){
        Article article =articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(),id);
        if(article==null)
            return JSON.toJSONString(0);
        Integer articleCount =article.getArticleLikeCount()+1;
        article.setArticleLikeCount(articleCount);
        articleService.updateArticleDetail(article);
        return JSON.toJSONString(articleCount);
    }

    @RequestMapping("article/view/{id}")
    @ResponseBody
    public String increaseViewCount(@PathVariable("id")Integer id){
        Article article =articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(),id);
        Integer articleCount = article.getArticleViewCount() + 1;
        article.setArticleViewCount(articleCount);
        articleService.updateArticleDetail(article);
        return JSON.toJSONString(articleCount);
    }

}
