package com.rr.blog.controller.home;

import com.rr.blog.entity.Article;
import com.rr.blog.entity.Category;
import com.rr.blog.entity.Page;
import com.rr.blog.entity.Tag;
import com.rr.blog.enums.LimitCount;
import com.rr.blog.enums.PageStatus;
import com.rr.blog.service.ArticleService;
import com.rr.blog.service.CategoryService;
import com.rr.blog.service.PageService;
import com.rr.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Key;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private PageService pageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @RequestMapping("/{key}")
    public String pageDetail(@PathVariable("key")String key, Model model) {
        Page page = pageService.getPageByKey(PageStatus.NORMAL.getValue(), key);
        if (page == null) {
            return "redirect:/404";
        }
        model.addAttribute("page", page);
        //侧边栏显示
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(8);

        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/page";
    }
    /**
     * 文章归档页面显示
     *
     * @return
     */
    @RequestMapping(value = "/articleFile")
    public String articleFile(Model model) {
        List<Article> articleList = articleService.listAllNotWithContent();
        model.addAttribute("articleList", articleList);
        //侧边栏显示
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(10);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/articleFile";
    }
    @RequestMapping("/map")
    public String  siteMap(Model model){
        List<Article> articleList =articleService.listAllNotWithContent();
        model.addAttribute("articleList", articleList);
        List<Category> categoryList =categoryService.listCategory();
        model.addAttribute("categoryList", categoryList);
        List<Tag> tagList =tagService.listTag();
        model.addAttribute("tagList", tagList);
        List<Article> mostCommnetArticleList= articleService.listArticleByCommentCount(LimitCount.COUNT_HIGH.getValue());
        model.addAttribute("mostCommnetArticleList", mostCommnetArticleList);
        return "/Home/Page/SiteMap";
    }


}
