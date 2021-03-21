package com.rr.blog.controller.admin;

import cn.hutool.http.HtmlUtil;
import com.github.pagehelper.PageInfo;
import com.rr.blog.dto.ArticleParam;
import com.rr.blog.entity.Article;
import com.rr.blog.entity.Category;
import com.rr.blog.entity.Tag;
import com.rr.blog.entity.User;
import com.rr.blog.service.ArticleService;
import com.rr.blog.service.CategoryService;
import com.rr.blog.service.TagService;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin/article")
public class BackArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    @RequestMapping(value="")
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                         @RequestParam(required = false) String status, Model model){
        HashMap<String,Object> criteria=new HashMap<>(1);
        if(status==null){
            model.addAttribute("pageUrlPrefix","/admin/article?pageIndex="+pageIndex);
        }
        else{
            criteria.put("status",status);
            model.addAttribute("pageUrlPrefix","/admin/article?pageIndex="+pageIndex+"&status="+status);
        }

        PageInfo<Article> articlePageInfo =articleService.pageArticle(pageIndex,pageSize,criteria);
        model.addAttribute("pageInfo",articlePageInfo);
        return "Admin/Article/index";
    }
    @RequestMapping(value = "/insert")
    public String insertArticleView(Model model){
        List<Category> categoryList=categoryService.listCategory();
        model.addAttribute("categoryList",categoryList);
        List<Tag> tagList =tagService.listTag();
        model.addAttribute("tagList",tagList);
        return "Admin/Article/insert";
    }
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8" )
    public String insertArticleSubmit(HttpSession session, ArticleParam articleParam){
        User user =(User) session.getAttribute("user");
        if(user==null)
            return "redirect:/404";
        Article article =new Article();
        article.setArticleUserId(user.getUserId());
        article.setArticleTitle(articleParam.getArticleTitle());
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(articleParam.getArticleContent());
        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            article.setArticleSummary(summary);
        } else {
            article.setArticleSummary(summaryText);
        }
        List<Category> categoryList = new ArrayList<>();
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);
        //填充标签
        List<Tag> tagList = new ArrayList<>();
        if (articleParam.getArticleTagIds() != null) {
            for (int i = 0; i < articleParam.getArticleTagIds().size(); i++) {
                Tag tag = new Tag(articleParam.getArticleTagIds().get(i));
                tagList.add(tag);
            }
        }
        article.setTagList(tagList);

        articleService.insertArticle(article);
        return "redirect:/admin/article";
    }
    @RequestMapping("/delete/{id}")
    public void deleteArticle(@PathVariable("id")Integer id){
        articleService.deleteArticle(id);
    }
    @RequestMapping("/edit/{id}")
    public ModelAndView editArticleView(@PathVariable("id")Integer id ){
        Article article =articleService.getAfterArticle(id);
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.addObject("article",article);

        List<Category> categoryList = categoryService.listCategory();
        modelAndView.addObject("categoryList", categoryList);

        List<Tag> tagList = tagService.listTag();
        modelAndView.addObject("tagList", tagList);


        modelAndView.setViewName("Admin/Article/edit");
        return modelAndView;
    }
    /**
     * 编辑文章提交
     *
     * @param articleParam
     * @return
     */
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editArticleSubmit(ArticleParam articleParam) {
        Article article = new Article();
        article.setArticleId(articleParam.getArticleId());
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleStatus(articleParam.getArticleStatus());
        //文章摘要
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(article.getArticleContent());
        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            article.setArticleSummary(summary);
        } else {
            article.setArticleSummary(summaryText);
        }
        //填充分类
        List<Category> categoryList = new ArrayList<>();
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);
        //填充标签
        List<Tag> tagList = new ArrayList<>();
        if (articleParam.getArticleTagIds() != null) {
            for (int i = 0; i < articleParam.getArticleTagIds().size(); i++) {
                Tag tag = new Tag(articleParam.getArticleTagIds().get(i));
                tagList.add(tag);
            }
        }
        article.setTagList(tagList);
        articleService.updateArticleDetail(article);
        return "redirect:/admin/article";
    }



}
