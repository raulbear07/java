package com.rr.blog.controller.home;

import com.github.pagehelper.PageInfo;
import com.rr.blog.entity.Article;
import com.rr.blog.entity.Category;
import com.rr.blog.entity.Tag;
import com.rr.blog.enums.ArticleStatus;
import com.rr.blog.enums.LimitCount;
import com.rr.blog.service.ArticleService;
import com.rr.blog.service.CategoryService;
import com.rr.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @RequestMapping("/category/{cateId}")
    public String  getArticleListByCategoryId(@PathVariable("cateId")Integer cateId,
                                              @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                              @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                              Model model){
        Category category=categoryService.getCategoryById(cateId);
        if(category ==null)
            return "redirect:/404";
        model.addAttribute("category",category);
        HashMap<String,Object> criteria =new HashMap<>(2);
        criteria.put("categoryId",cateId);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        PageInfo<Article> articlePageInfo=articleService.pageArticle(pageIndex,pageSize,criteria);
        model.addAttribute("articlePageInfo",articlePageInfo);

        List<Tag> tagList=tagService.listTag();
        model.addAttribute("tagList",tagList);
        List<Article> randomArticleList= articleService.listRandomArticle(LimitCount.COUNT_MID.getValue());
        model.addAttribute("randomArticleList",randomArticleList);

        List<Article> mostCommentArticleList=articleService.listArticleByCommentCount(LimitCount.COUNT_MID.getValue());
        model.addAttribute("mostCommentArticleList",mostCommentArticleList);
        List<Article> mostViewArticleList=articleService.listArticleByViewCount(LimitCount.COUNT_MID.getValue());
        model.addAttribute("mostViewArticleList",mostViewArticleList);
        return "/Home/Page/articleListByCategory";
    }





}
